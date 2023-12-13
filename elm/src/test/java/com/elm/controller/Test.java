package com.elm.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.elm.dao.MessageDao;
import com.elm.dao.OrdersDao;
import com.elm.domain.Message;
import com.elm.domain.Orders;
import com.elm.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@SpringBootTest
public class Test {

    @Autowired
    private OrdersDao ordersDao;

    @Autowired
    private MessageDao messageDao;

    @org.junit.jupiter.api.Test
    void test()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long todayZero = calendar.getTimeInMillis();

        System.out.println(new Date(todayZero));
    }

    @org.junit.jupiter.api.Test
    void testDate()
    {
        //调用的代码
         Date start = DateUtil.strToDateLong(DateUtil.dateToStr(new Date(), Locale.CHINA) + " 00:00:00");
        Date end = DateUtil.strToDateLong(DateUtil.dateToStr(new Date(), Locale.CHINA) + " 23:59:59");

        QueryWrapper<Orders> queryWrapper =   new QueryWrapper<Orders>();
        queryWrapper.ge("time",start);
        queryWrapper.lt("time",end);
        List<Orders> list1 = ordersDao.selectList(queryWrapper);

        System.out.println(list1.size());
        System.out.println(list1);
    }

    @org.junit.jupiter.api.Test
    void testMessageDao()
    {
        List<Message> messages = messageDao.selectList(null);
        System.out.println(messages);
    }
}
