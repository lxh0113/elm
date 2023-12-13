package com.elm.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDetails {

    //店铺名字
    private String storeId;

    //购买数量
    private Integer number;

    //总价
    private Double price;

    //商品列表
    private ArrayList<Goods> goodsLists;

    //地址字符串
    private String addressText;

    //备注
    private String remark;

    //时间
    private Date time;

}
