package com.elm.domain;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("category")
public class category {

    private Integer id;

    private String store_id;

    private Integer flag;

    private Integer sort;

    private String name;

    private Integer status;

    public category() {
    }

    public category(Integer id, String store_id, Integer flag, Integer sort, String name, Integer status) {
        this.id = id;
        this.store_id = store_id;
        this.flag = flag;
        this.sort = sort;
        this.name = name;
        this.status = status;
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

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
