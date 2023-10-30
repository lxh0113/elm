package com.elm.domain;

import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("comments")
public class comments {

    private Integer id;

    private String store_id;

    private String orders_id;

    private String content;

    private String sender_id;

    private String receiver_id;

    private Date time;

    private Integer star;

    public comments() {
    }

    public comments(Integer id, String store_id, String orders_id, String content, String sender_id, String receiver_id, Date time, Integer star) {
        this.id = id;
        this.store_id = store_id;
        this.orders_id = orders_id;
        this.content = content;
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
        this.time = time;
        this.star = star;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getOrders_id() {
        return orders_id;
    }

    public void setOrders_id(String orders_id) {
        this.orders_id = orders_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(String receiver_id) {
        this.receiver_id = receiver_id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }
}
