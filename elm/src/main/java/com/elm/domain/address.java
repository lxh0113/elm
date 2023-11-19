package com.elm.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Address {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String province;

    private String city;

    private String district;

    private String detail;

    private String userId;

    private Integer identity;

    private Integer isDefault;

    private String receiverName;

    private Integer gender;

    private String telephone;

    public Address(String province, String city, String district, String detail, String userId, Integer identity, Integer isDefault, String receiverName, Integer gender, String telephone) {
        this.province = province;
        this.city = city;
        this.district = district;
        this.detail = detail;
        this.userId = userId;
        this.identity = identity;
        this.isDefault = isDefault;
        this.receiverName = receiverName;
        this.gender = gender;
        this.telephone = telephone;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", detail='" + detail + '\'' +
                ", userId='" + userId + '\'' +
                ", identity=" + identity +
                ", isDefault=" + isDefault +
                ", receiverName='" + receiverName + '\'' +
                ", gender=" + gender +
                ", telephone='" + telephone + '\'' +
                '}';
    }
}
