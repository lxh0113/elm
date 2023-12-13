package com.elm.dao.requestData;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderSearchData {
    private Integer status;
    private Integer current;
    private String storeId;
    private String userName;
    private Integer orderId;
    private Date startTime;
    private Date endTime;

    private String telephone;

    @Override
    public String toString() {
        return "OrderSearchData{" +
                "status=" + status +
                ", current=" + current +
                ", storeId='" + storeId + '\'' +
                ", userName='" + userName + '\'' +
                ", orderId=" + orderId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
