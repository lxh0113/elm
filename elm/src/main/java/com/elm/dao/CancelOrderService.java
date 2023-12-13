package com.elm.dao;

import com.elm.common.R;
import com.elm.domain.Orders;
import io.swagger.models.auth.In;

import java.util.concurrent.DelayQueue;

public interface CancelOrderService {

    void cancelOrder();
    R payOrder(Integer id);
    DelayQueue<Orders> getAllOrder();

    DelayQueue<Orders> getOrder();

}
