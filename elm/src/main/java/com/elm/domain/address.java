package com.elm.domain;

import io.swagger.models.auth.In;

public class address {

    private Integer id;

    private String province;

    private String city;

    private String district;

    private String detail;

    private String user_id;

    private Integer identity;

    private Integer is_default;

    private String receiver_name;

    private Integer gender;

    private String telephone;

    public address() {
    }

    public address(Integer id, String province, String city, String district, String detail, String user_id, Integer identity, Integer is_default, String receiver_name, Integer gender, String telephone) {
        this.id = id;
        this.province = province;
        this.city = city;
        this.district = district;
        this.detail = detail;
        this.user_id = user_id;
        this.identity = identity;
        this.is_default = is_default;
        this.receiver_name = receiver_name;
        this.gender = gender;
        this.telephone = telephone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Integer getIdentity() {
        return identity;
    }

    public void setIdentity(Integer identity) {
        this.identity = identity;
    }

    public Integer getIs_default() {
        return is_default;
    }

    public void setIs_default(Integer is_default) {
        this.is_default = is_default;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
