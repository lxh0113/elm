package com.elm.dao.requestData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderUserSearchData {
    private Integer status;
    private String userId;
    private Integer current;
    private String text;
    private Date startTime;
    private Date endTime;
    private Integer orderId;
}
