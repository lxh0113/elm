package com.elm.dao.requestData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BecomeStoreData {
    private String id;

    private String logoUrl;

    private String storeUrl;

    private String description;

    private String telephone;

    private Double deliverStartFee;

    private Double packageFee;

    private String addressId;

    private String category;

//    private int status;

    private String name;

    private String province;

    private String city;

    private String district;

    private String detail;

    private ArrayList<String> storeEnv;
}
