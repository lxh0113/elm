package com.elm.domain;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("store")
public class store {

    private String id;

    private String logo_url;

    private String store_url;

    private String description;

    private String telephone;

    private Double deliver_start_fee;

    private Double package_fee;

    private String address_id;

    private String category;

    private String name;

    public store() {
    }

    public store(String id, String logo_url, String store_url, String description, String telephone, Double deliver_start_fee, Double package_fee, String address_id, String category, String name) {
        this.id = id;
        this.logo_url = logo_url;
        this.store_url = store_url;
        this.description = description;
        this.telephone = telephone;
        this.deliver_start_fee = deliver_start_fee;
        this.package_fee = package_fee;
        this.address_id = address_id;
        this.category = category;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    public String getStore_url() {
        return store_url;
    }

    public void setStore_url(String store_url) {
        this.store_url = store_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Double getDeliver_start_fee() {
        return deliver_start_fee;
    }

    public void setDeliver_start_fee(Double deliver_start_fee) {
        this.deliver_start_fee = deliver_start_fee;
    }

    public Double getPackage_fee() {
        return package_fee;
    }

    public void setPackage_fee(Double package_fee) {
        this.package_fee = package_fee;
    }

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
