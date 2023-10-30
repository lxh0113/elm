package com.elm.domain;


import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.models.auth.In;

@TableName("shopping_cart")
public class shopping_cart {

    private Integer id;

    private String user_id;

    private Integer goods_id;

    private String store_id;

    private Integer number;

    public shopping_cart() {
    }

    public shopping_cart(Integer id, String user_id, Integer goods_id, String store_id, Integer number) {
        this.id = id;
        this.user_id = user_id;
        this.goods_id = goods_id;
        this.store_id = store_id;
        this.number = number;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Integer getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(Integer goods_id) {
        this.goods_id = goods_id;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
