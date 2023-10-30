package com.elm.domain;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("flavors")
public class flavors {

    private String name;

    private String options;

    private Integer flag;

    private Integer flag_id;

    public flavors() {
    }

    public flavors(String name, String options, Integer flag, Integer flag_id) {
        this.name = name;
        this.options = options;
        this.flag = flag;
        this.flag_id = flag_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getFlag_id() {
        return flag_id;
    }

    public void setFlag_id(Integer flag_id) {
        this.flag_id = flag_id;
    }
}
