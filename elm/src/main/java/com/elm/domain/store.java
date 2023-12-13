package com.elm.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("store")
public class Store {

    @TableId
    private String id;

    private String logoUrl;

    private String storeUrl;

    private String description;

    private String telephone;

    private Double deliverStartFee;

    private Double packageFee;

    private int addressId;

    private String category;

    private String name;

    private Integer status;

    @Override
    public String toString() {
        return "Store{" +
                "id='" + id + '\'' +
                ", logoUrl='" + logoUrl + '\'' +
                ", storeUrl='" + storeUrl + '\'' +
                ", description='" + description + '\'' +
                ", telephone='" + telephone + '\'' +
                ", deliverStartFee=" + deliverStartFee +
                ", packageFee=" + packageFee +
                ", addressId=" + addressId +
                ", category='" + category + '\'' +
                ", name='" + name + '\'' +
                ", status=" + status +
                '}';
    }
}
