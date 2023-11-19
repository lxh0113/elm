package com.elm.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Type;

@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("goods")
public class Goods {

    @TableId
    private String id;

    private String url;

    private Integer status;

    private String description;

    @TableField("category_id")
    private Integer categoryId;

    private double price;

    private String name;

    @TableField("store_id")
    private String storeId;

    @TableField(exist = false)
    private Integer current;

    @TableField(exist = false)
    private boolean isSelected;


    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", status=" + status +
                ", description='" + description + '\'' +
                ", categoryId=" + categoryId +
                ", price=" + price +
                ", name='" + name + '\'' +
                ", storeId='" + storeId + '\'' +
                ", current=" + current +
                ", isSelected=" + isSelected +
                '}';
    }
}
