package com.elm.domain;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("store_env")
public class store_env {

    private String store_id;

    private String url;

    public store_env() {
    }

    public store_env(String store_id, String url) {
        this.store_id = store_id;
        this.url = url;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
