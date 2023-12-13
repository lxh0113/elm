package com.elm.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("flavors")
public class Flavors {
    private String name;
    private String options;
    private Double price;
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("goods_id")
    private String goodsId;

    @Override
    public String toString() {
        return "Flavors{" +
                "name='" + name + '\'' +
                ", options='" + options + '\'' +
                ", price=" + price +
                ", id=" + id +
                ", goodsId=" + goodsId +
                '}';
    }
}
