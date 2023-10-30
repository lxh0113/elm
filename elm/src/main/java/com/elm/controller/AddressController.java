package com.elm.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.elm.common.R;
import com.elm.dao.AddressDao;
import com.elm.dao.requestData.AddressData;
import org.apache.tomcat.jni.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@ResponseBody
@RestController
@RequestMapping("/api/address")
public class AddressController {

    @Autowired
    private AddressDao addressDao;

    //处理地址相关的请求

    @PostMapping("/user")
    public R getAddress(@RequestBody AddressData addressData)
    {
        System.out.println("收到了"+addressData.getId());
        QueryWrapper<Address> queryWrapper=new QueryWrapper<>();
        queryWrapper.select("province","city","district","detail")
                .eq("user_id",addressData.getId())
                .eq("is_default",1);
        Address address=addressDao.selectOne(queryWrapper);

        if(address==null)
        {
            System.out.println("没有获取到地址");
            return R.error("没有获取到地址");
        }
        else
        {
            return R.success(address);
        }
    }

}
