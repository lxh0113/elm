package com.elm.domain;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("goods")
public class goods {

    private Integer id;

    private String url;

    private Integer status;

    private String description;

    private Integer category_id;

    public goods() {
    }

    public goods(Integer id, String url, Integer status, String description, Integer category_id) {
        this.id = id;
        this.url = url;
        this.status = status;
        this.description = description;
        this.category_id = category_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }
}
