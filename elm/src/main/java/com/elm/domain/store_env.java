package com.elm.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("store_env")
public class Store_env {

    private String storeId;

    private String url;

    @Override
    public String toString() {
        return "Store_env{" +
                "storeId='" + storeId + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
