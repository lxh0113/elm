package com.elm.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("rc_district")
public class RcDistrict {

//    @TableField("district_id")
    private short districtId;

    private short pid;

    private String district;

    private byte level;
}
