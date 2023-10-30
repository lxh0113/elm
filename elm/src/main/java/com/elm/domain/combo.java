package com.elm.domain;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("combo")
public class combo {

    private Integer id;

    private Integer category_id;

    private String name;

    private String description;

    private String url;

    private Integer status;

    private Double price;

    public combo() {
    }

    public combo(Integer id, Integer category_id, String name, String description, String url, Integer status, Double price) {
        this.id = id;
        this.category_id = category_id;
        this.name = name;
        this.description = description;
        this.url = url;
        this.status = status;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
