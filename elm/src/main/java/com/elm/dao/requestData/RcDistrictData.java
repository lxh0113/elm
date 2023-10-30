package com.elm.dao.requestData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RcDistrictData {

    private short district_id;

    private short pid;

    private String district;

    private byte level;
}
