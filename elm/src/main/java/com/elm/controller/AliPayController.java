package com.elm.controller;

import cn.hutool.json.JSONObject;
import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.elm.common.R;
import com.elm.config.AliPayConfig;
import com.elm.dao.OrdersDao;
import com.elm.domain.AliPay;
import com.elm.domain.Orders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// kqonmb1605@sandbox.com
// 111111
@Slf4j
@RestController
@RequestMapping("/api/alipay")
public class AliPayController {

    private static final String GATEWAY_URL = "https://openapi-sandbox.dl.alipaydev.com/gateway.do";
    private static final String FORMAT = "JSON";
    private static final String CHARSET = "UTF-8";
    //签名方式
    private static final String SIGN_TYPE = "RSA2";

    @Resource
    private AliPayConfig aliPayConfig;

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Autowired
    private OrdersDao ordersDao;

    @PostMapping("/preDeal")
    public R preDeal(@RequestBody Orders orders)
    {
        //处理订单逻辑
        System.out.println(orders);
        orders.setStatus(0);
        orders.setOrderInfo(JSON.toJSONString(orders.getItemsLists()));
        orders.setTime(new Date(System.currentTimeMillis()));

        //先插入到数据库里面
        int insert = ordersDao.insert(orders);
        if(insert<=0) return R.error("出错了");

        AliPay aliPay=new AliPay();
        aliPay.setSubject(orders.getStoreId());
        aliPay.setTotalAmount(orders.getPrice());
        aliPay.setTraceNo(orders.getId().toString());
        aliPay.setAlipayTraceNo("");

        // 将订单信息发送到消息队列
       sendMessage(orders.getId());

        System.out.println("消息发送成功");
        return R.success(orders).add("alipay",aliPay);
    }


    @GetMapping("/pay") // &subject=xxx&traceNo=xxx&totalAmount=xxx
    public void pay(AliPay aliPay, HttpServletResponse httpResponse) throws Exception {
        // 1. 创建Client，通用SDK提供的Client，负责调用支付宝的API
        AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY_URL, aliPayConfig.getAppId(),
                aliPayConfig.getAppPrivateKey(), FORMAT, CHARSET, aliPayConfig.getAlipayPublicKey(), SIGN_TYPE);

        // 2. 创建 Request并设置Request参数
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();  // 发送请求的 Request类
        request.setNotifyUrl(aliPayConfig.getNotifyUrl());
        JSONObject bizContent = new JSONObject();
        bizContent.set("out_trade_no", aliPay.getTraceNo());  // 我们自己生成的订单编号
        bizContent.set("total_amount", aliPay.getTotalAmount()); // 订单的总金额
        bizContent.set("subject", aliPay.getSubject());   // 支付的名称
        bizContent.set("product_code", "FAST_INSTANT_TRADE_PAY");  // 固定配置
        request.setBizContent(bizContent.toString());

        // 执行请求，拿到响应的结果，返回给浏览器
        String form = "";
        try {
            form = alipayClient.pageExecute(request).getBody(); // 调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        httpResponse.setContentType("text/html;charset=" + CHARSET);
        httpResponse.getWriter().write(form);// 直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();
    }

    @PostMapping("/notify")  // 注意这里必须是POST接口
    public R payNotify(HttpServletRequest request) throws Exception {
        Orders orders=null;
        System.out.println("看看我进来了吗");
        if (request.getParameter("trade_status").equals("TRADE_SUCCESS")) {
            System.out.println("=========支付宝异步回调========");

            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (String name : requestParams.keySet()) {
                params.put(name, request.getParameter(name));
                // System.out.println(name + " = " + request.getParameter(name));
            }

            String outTradeNo = params.get("out_trade_no");
            String gmtPayment = params.get("gmt_payment");
            String alipayTradeNo = params.get("trade_no");

            String sign = params.get("sign");
            String content = AlipaySignature.getSignCheckContentV1(params);
            boolean checkSignature = AlipaySignature.rsa256CheckContent(content, sign, aliPayConfig.getAlipayPublicKey(), "UTF-8"); // 验证签名
            // 支付宝验签
            if (checkSignature) {
                // 验签通过
                System.out.println("交易名称: " + params.get("subject"));
                System.out.println("交易状态: " + params.get("trade_status"));
                System.out.println("支付宝交易凭证号: " + params.get("trade_no"));
                System.out.println("商户订单号: " + params.get("out_trade_no"));
                System.out.println("交易金额: " + params.get("total_amount"));
                System.out.println("买家在支付宝唯一id: " + params.get("buyer_id"));
                System.out.println("买家付款时间: " + params.get("gmt_payment"));
                System.out.println("买家付款金额: " + params.get("buyer_pay_amount"));

                // 查询订单
                QueryWrapper<Orders> queryWrapper=new QueryWrapper<>();
                queryWrapper.eq("id",outTradeNo)
                        .eq("status",0);
                orders=ordersDao.selectOne(queryWrapper);
                if(orders!=null)
                {
                    orders.setStatus(1);
                    //设置状态
                    ordersDao.update(orders,queryWrapper);
                }
                else return R.error("无订单");
            }
        }
        return R.success(orders);
    }


    public void sendMessage(Integer orderId) {
        rabbitTemplate.convertAndSend("delay.test", "lzy", orderId,new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                //设置消息持久化
                message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                message.getMessageProperties().setHeader("x-delay",1000*60*15);//设置延时时间 15分钟
                return message;
            }
        });

        System.out.println("消息发送成功");
        log.info("{}",new Date(System.currentTimeMillis()));
        log.info("发送消息成功");
    }
}
