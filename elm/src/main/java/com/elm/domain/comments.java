package com.elm.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("comments")
public class Comments {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("store_id")
    private String storeId;

    @TableField("orders_id")
    private Integer ordersId;

    private String content;

    @TableField("sender_id")
    private String senderId;

    @TableField("receiver_id")
    private String receiverId;

    private Date time;

    private Integer star;

    private String images;

}
