package com.elm.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("shopping_cart")
public class ShopCart {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("user_id")
    private String userId;

    @TableField("goods_id")
    private String goodsId;

    @TableField("goods_info")
    private String goodsInfo;

    @TableField("store_id")
    private String storeId;

    private Integer number;

    private Date time;

    @TableField(exist = false)
    private Goods goods;

    @TableField(exist = false)
    private List<Flavors> flavors;

    public ShopCart(Integer id, String userId, String goodsId, String goodsInfo, String storeId, Integer number) {
        this.id = id;
        this.userId = userId;
        this.goodsId = goodsId;
        this.goodsInfo = goodsInfo;
        this.storeId = storeId;
        this.number = number;
    }

    @Override
    public String toString() {
        return "ShopCart{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", goodsId='" + goodsId + '\'' +
                ", goodsInfo='" + goodsInfo + '\'' +
                ", storeId='" + storeId + '\'' +
                ", number=" + number +
                ", time=" + time +
                ", goods=" + goods +
                ", flavors=" + flavors +
                '}';
    }
}
