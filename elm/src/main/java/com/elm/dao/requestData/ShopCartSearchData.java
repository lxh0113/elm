package com.elm.dao.requestData;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopCartSearchData {

    private Integer id;

    private String userId;

    private String goodsId;

    private String goodsInfo;

    private String storeId;

    private Integer number;

    private Date time;

    private Integer current;
}
