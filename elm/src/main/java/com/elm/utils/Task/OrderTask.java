package com.elm.utils.Task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.elm.dao.OrdersDao;
import com.elm.domain.Orders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 自定义定时任务，实现订单状态定时处理
 */
@Component
@Slf4j
public class OrderTask {

    @Autowired
    private OrdersDao ordersDao;

    /**
     * 处理支付超时订单
     */
    @Scheduled(cron = "0 * * * * ?")
    public void processTimeoutOrder(){
        log.info("处理支付超时订单：{}", new Date());

        LocalDateTime time = LocalDateTime.now().plusMinutes(-15);

        QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 0)  //寻找还未支付 但是时间已经超过15分钟的
                .lt("order_time", LocalDateTime.now().minusMinutes(15));
        List<Orders> ordersList = ordersDao.selectList(queryWrapper);
        if(ordersList != null && ordersList.size() > 0){
            ordersList.forEach(order -> {
                order.setStatus(2);
                order.setIsCancel(1);
                order.setCancelReason("支付超时，自动取消");
                order.setTime(new Date(System.currentTimeMillis()));
                ordersDao.updateById(order);
            });
        }
    }
}