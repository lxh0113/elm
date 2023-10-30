package com.elm.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.elm.common.R;
import com.elm.dao.Rc_district;
import com.elm.dao.requestData.RcDistrictData;
import com.elm.domain.rc_district;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ResponseBody
@RestController
@RequestMapping("/api/district")
public class RcDistrictController {

    @Autowired
    private Rc_district rc_districtDao;

    @PostMapping
    public R getDistrict(@RequestBody RcDistrictData rcDistrictData)
    {
        System.out.println(rcDistrictData.getPid()+" "+rcDistrictData.getLevel());
        QueryWrapper<rc_district> queryWrapper=new QueryWrapper<>();
        queryWrapper.select("district_id","pid","district","level")
                .eq("pid",rcDistrictData.getPid())
                .eq("level",rcDistrictData.getLevel());

        List<rc_district> list=rc_districtDao.selectList(queryWrapper);
//        System.out.println(list);
        if(list==null)
        {
            return R.error("服务器繁忙");
        }
        else if(list.size()==0)
        {
            return R.error("服务器繁忙");
        }
        else return R.success(list);

    }
}
