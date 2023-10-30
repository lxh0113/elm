package com.elm.domain;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("comments_detail")
public class comments_detail {

    private Integer comments_id;

    private String url;

    public comments_detail() {
    }

    public comments_detail(Integer comments_id, String url) {
        this.comments_id = comments_id;
        this.url = url;
    }

    public Integer getComments_id() {
        return comments_id;
    }

    public void setComments_id(Integer comments_id) {
        this.comments_id = comments_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
