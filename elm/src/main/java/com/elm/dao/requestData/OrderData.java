package com.elm.dao.requestData;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderData {

    private String storeId;
    private String buyerId;
    private String addressText;
    private Double packageFee;
    private Integer status;
    private Date time;
    private String remark;
    private Integer isCancel;
    private String cancelReason;
    private String orderInfo;
    private Double price;
    private List<OrderItemsData> itemsLists;
}
