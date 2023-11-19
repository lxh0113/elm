package com.elm.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.elm.common.R;
import com.elm.dao.Rc_district;
import com.elm.dao.requestData.RcDistrictData;
import com.elm.domain.RcDistrict;
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
        QueryWrapper<RcDistrict> queryWrapper=new QueryWrapper<>();
        queryWrapper.select("district_id","pid","district","level")
                .eq("pid",rcDistrictData.getPid())
                .eq("level",rcDistrictData.getLevel());

        List<RcDistrict> list=rc_districtDao.selectList(queryWrapper);
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

    @GetMapping("/{id}")
    public R getAddress(@PathVariable String id)
    {
        QueryWrapper<RcDistrict> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("district_id",id);
        RcDistrict rcDistrict=rc_districtDao.selectOne(queryWrapper);
        return R.success(rcDistrict);
    }
}
