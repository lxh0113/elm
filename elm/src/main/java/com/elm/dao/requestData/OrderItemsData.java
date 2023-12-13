package com.elm.dao.requestData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderItemsData {
    private String name;
    private String url;
    private String flavors;
    private Integer count;
    private Double price;
}
