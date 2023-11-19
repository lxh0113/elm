package com.elm.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.elm.common.R;
import com.elm.dao.AddressDao;
import com.elm.dao.requestData.AddressData;
import com.elm.domain.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ResponseBody
@RestController
@RequestMapping("/api/address")
public class AddressController {

    @Autowired
    private AddressDao addressDao;

    //处理地址相关的请求

    @GetMapping("/{id}")
    public R getAddress(@PathVariable String id)
    {
        System.out.println("收到了"+id);
        QueryWrapper<com.elm.domain.Address> queryWrapper=new QueryWrapper<>();
        queryWrapper
                .eq("user_id",id)
                .eq("identity",0)
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

    @PostMapping("/{id}")
    public R getDefaultAddress(@PathVariable String id)
    {
        System.out.println("看看"+id);

        List<Address> list=getUserAddress(id);

        return R.success(list);
    }

    @PostMapping
    public R addNewAddress(@RequestBody Address address)
    {
        System.out.println("添加地址");
        int insert = addressDao.insert(address);
        if(insert<=0) return R.error("添加失败");

        List<Address> list = getUserAddress(address.getUserId());

        return R.success(list);
    }

    @PutMapping
    public R changeAddress(@RequestBody Address address)
    {
        QueryWrapper<Address> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",address.getId());

        int update = addressDao.update(address, queryWrapper);
        if(update<=0) return R.error("修改失败");

        List<Address> list = getUserAddress(address.getUserId());

        return R.success(list);
    }

    @DeleteMapping
    public R deleteAddress(@RequestBody Address address)
    {
        //
        int i = addressDao.deleteById(address.getId());
        if(i<=0) return R.error("删除出错");

        List<Address> lists = getUserAddress(address.getUserId());
        return R.success(lists);
    }


    public List<Address> getUserAddress(String id)
    {
        QueryWrapper<Address> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",id)
                .eq("identity",0)
                .orderBy(true,false,"is_default");

        return addressDao.selectList(queryWrapper);
    }

    @PostMapping("/toDefault")
    public R changeToDefault(@RequestBody Address address)
    {
        //设置为默认地址
        //先把该用户其他的地址设置成非默认
        try {
            changeDefault(address);
        }catch (Exception e)
        {
            e.printStackTrace();
            return R.error("修改失败");
        }

        List<Address> list = getUserAddress(address.getUserId());
        return R.success(list);
    }


    @Transactional(rollbackFor = {Exception.class})
    public void changeDefault(Address address) throws Exception {
        // 构建更新条件
        UpdateWrapper<Address> updateWrapper = new UpdateWrapper<>();
        updateWrapper
                .eq("user_id", address.getUserId())
                .eq("identity", 0);

        // 构建更新字段
        Address updateAddress = new Address();
        updateAddress.setIsDefault(0); // 设置 is_default 属性为 0

        // 执行更新操作
        int updateResult = addressDao.update(updateAddress, updateWrapper);

        if (updateResult>0) {
            // 更新成功的处理
            System.out.println("更新成功");
        } else {
            // 更新失败的处理
            System.out.println("更新失败");
            throw new Exception("修改地址状态失败");
        }

        updateWrapper.clear();
        updateWrapper.eq("id",address.getId())
                .set("is_default",1);
        updateAddress.setIsDefault(1);
        int update = addressDao.update(updateAddress, updateWrapper);
        if(update<=0) throw new Exception("修改地址状态失败");
    }

}
