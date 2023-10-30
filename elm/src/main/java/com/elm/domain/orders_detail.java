package com.elm.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.models.auth.In;

@TableName("orders_detail")
public class orders_detail {

    private Integer id;

    private Integer type_id;

    private Integer order_id;

    private Integer number;

    private Integer type;

    public orders_detail() {
    }

    public orders_detail(Integer id, Integer type_id, Integer order_id, Integer number, Integer type) {
        this.id = id;
        this.type_id = type_id;
        this.order_id = order_id;
        this.number = number;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType_id() {
        return type_id;
    }

    public void setType_id(Integer type_id) {
        this.type_id = type_id;
    }

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
