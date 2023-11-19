package com.elm.domain;

import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("orders")
public class Orders {

    private Integer id;

    private String buyer_id;

    private String store_id;

    private Double price;

    private Integer address_id;

    private Double package_fee;

    private Integer status;

    private Date time;

    private String remark;

    private Integer is_cancel;

    private String cancel_reason;

    public Orders() {
    }

    public Orders(Integer id, String buyer_id, String store_id, Double price, Integer address_id, Double package_fee, Integer status, Date time, String remark, Integer is_cancel, String cancel_reason) {
        this.id = id;
        this.buyer_id = buyer_id;
        this.store_id = store_id;
        this.price = price;
        this.address_id = address_id;
        this.package_fee = package_fee;
        this.status = status;
        this.time = time;
        this.remark = remark;
        this.is_cancel = is_cancel;
        this.cancel_reason = cancel_reason;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBuyer_id() {
        return buyer_id;
    }

    public void setBuyer_id(String buyer_id) {
        this.buyer_id = buyer_id;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getAddress_id() {
        return address_id;
    }

    public void setAddress_id(Integer address_id) {
        this.address_id = address_id;
    }

    public Double getPackage_fee() {
        return package_fee;
    }

    public void setPackage_fee(Double package_fee) {
        this.package_fee = package_fee;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getIs_cancel() {
        return is_cancel;
    }

    public void setIs_cancel(Integer is_cancel) {
        this.is_cancel = is_cancel;
    }

    public String getCancel_reason() {
        return cancel_reason;
    }

    public void setCancel_reason(String cancel_reason) {
        this.cancel_reason = cancel_reason;
    }
}
