package com.elm.controller;

import cn.hutool.db.sql.Order;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.elm.common.R;
import com.elm.dao.OrdersDao;
import com.elm.dao.StoreDao;
import com.elm.dao.requestData.OrderItemsData;
import com.elm.dao.requestData.OrderSearchData;
import com.elm.dao.requestData.OrderUserSearchData;
import com.elm.domain.Orders;
import com.elm.domain.Store;
import com.elm.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@ResponseBody
@RequestMapping("/api/orders")
public class OrdersController {

    @Autowired
    private OrdersDao ordersDao;

    @Autowired
    private StoreDao storeDao;

    @PostMapping("/store")
    public R getOrders(@RequestBody OrderSearchData orderSearchData)
    {
        System.out.println(orderSearchData);
        IPage<Orders> page=new Page<>();
        page.setCurrent(orderSearchData.getCurrent());
        page.setSize(7);

        QueryWrapper<Orders> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(orderSearchData.getOrderId()!=null,"id",orderSearchData.getOrderId())
                .eq(orderSearchData.getStatus()!=-1,"status",orderSearchData.getStatus())
                .eq(orderSearchData.getStoreId()!=null,"store_id",orderSearchData.getStoreId())
                .like(!orderSearchData.getUserName().equals(""),"buyer_name",orderSearchData.getUserName())
                .like(!orderSearchData.getTelephone().equals(""),"buyer_tel",orderSearchData.getTelephone())
                .gt(orderSearchData.getStartTime()!=null,"time",orderSearchData.getStartTime())
                .lt(orderSearchData.getEndTime()!=null,"time",orderSearchData.getEndTime())
                .orderBy(true,false,"time");


        IPage<Orders> iPage=ordersDao.selectPage(page,queryWrapper);

        List<Orders> ordersList=iPage.getRecords();
        for (int i = 0; i < ordersList.size(); i++) {
            //
            ordersList.get(i).setItemsLists((List<OrderItemsData>) JSON.parse(ordersList.get(i).getOrderInfo()));
        }
        System.out.println("-------------------------------");
        System.out.println(orderSearchData);
        iPage.setRecords(ordersList);
        return R.success(iPage);
    }

    @PutMapping
    public R changeStatus(@RequestBody Orders orders)
    {
        int i = ordersDao.updateById(orders);
        if(i<=0) return R.error("出错了");


        return R.success(orders);
    }

    @PostMapping("/user")
    public R getUserOrders(@RequestBody OrderUserSearchData orderUserSearchData)
    {
        System.out.println("获取用户列表。。。。。");
        IPage<Orders> iPage=new Page<>();
        QueryWrapper<Orders> queryWrapper=new QueryWrapper<>();

        iPage.setCurrent(orderUserSearchData.getCurrent());
        iPage.setSize(7);

        queryWrapper.eq(!orderUserSearchData.getUserId().equals(""),"buyer_id",orderUserSearchData.getUserId())
                .eq(orderUserSearchData.getStatus()!=-1,"status",orderUserSearchData.getStatus())
                .like(orderUserSearchData.getOrderId()!=0,"id",orderUserSearchData.getOrderId())
                        .gt(orderUserSearchData.getStartTime()!=null,"time",orderUserSearchData.getStartTime())
                                .lt(orderUserSearchData.getEndTime()!=null,"time",orderUserSearchData.getEndTime())
                .orderBy(true,false,"time");

        queryWrapper.like(!orderUserSearchData.getText().equals(""),"buyer_name",orderUserSearchData.getText())
                .or().like(!orderUserSearchData.getText().equals(""),"buyer_id",orderUserSearchData.getText())
                .or().like(!orderUserSearchData.getText().equals(""),"buyer_name",orderUserSearchData.getText())
                .or().like(!orderUserSearchData.getText().equals(""),"orderInfo",orderUserSearchData.getText())
                .or().like(!orderUserSearchData.getText().equals(""),"remark",orderUserSearchData.getText());

        IPage<Orders> page = ordersDao.selectPage(iPage, queryWrapper);

        List<Orders> ordersList=iPage.getRecords();
        for (int i = 0; i < ordersList.size(); i++) {
            //
            ordersList.get(i).setItemsLists((List<OrderItemsData>) JSON.parse(ordersList.get(i).getOrderInfo()));
        }
        iPage.setRecords(ordersList);
        return R.success(iPage);
    }

    @GetMapping("/update/{orderId}")
    public R getUpdatedOrders(@PathVariable Integer orderId)
    {
        Orders orders=ordersDao.selectById(orderId);
        if(orders==null)
        {
            return R.error("订单获取出错");
        }
        return R.success(orders);
    }

    @Transactional(rollbackFor = Exception.class)
    @GetMapping("/{storeId}")
    public R getWorkSpaceData(@PathVariable String storeId)
    {
        System.out.println("收到了来自"+storeId+"的获取工作台数据请求");
        Map<String,Object> data=new HashMap<>();

        Date start = DateUtil.strToDateLong(DateUtil.dateToStr(new Date(), Locale.CHINA) + " 00:00:00");
        Date end = DateUtil.strToDateLong(DateUtil.dateToStr(new Date(), Locale.CHINA) + " 23:59:59");

        QueryWrapper<Orders> queryWrapper =   new QueryWrapper<Orders>();
        queryWrapper.ge("time",start)
                .lt("time",end);


        return R.error("");
    }

    @PostMapping("/remark")
    public R getRemarksOrders(@RequestBody Orders orders)
    {
        QueryWrapper<Orders> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq(orders.getBuyerId()!=null,"buyer_id",orders.getBuyerId())
                .eq(orders.getStatus()!=null,"status",orders.getStatus());
        
        IPage<Orders> iPage=new Page<>();
        iPage.setCurrent(orders.getCurrent());
        iPage.setSize(5);

        IPage<Orders> res = ordersDao.selectPage(iPage, queryWrapper);
        List<Orders> ordersList=res.getRecords();
        for (int i = 0; i < ordersList.size(); i++) {
            //
            ordersList.get(i).setItemsLists((List<OrderItemsData>) JSON.parse(ordersList.get(i).getOrderInfo()));

            Store store = storeDao.selectById(ordersList.get(i).getStoreId());

            ordersList.get(i).setStoreUrl(store.getStoreUrl());
            ordersList.get(i).setStoreName(store.getName());
        }
        res.setRecords(ordersList);
        return R.success(res);
    }



    private Date getTodayZero()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long todayZero = calendar.getTimeInMillis();

        System.out.println(new Date(todayZero));
        return new Date(todayZero);
    }

}
