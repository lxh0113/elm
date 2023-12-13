package com.elm.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.elm.common.R;
import com.elm.dao.AddressDao;
import com.elm.dao.CategoryDao;
import com.elm.dao.StoreDao;
import com.elm.dao.StoreEnvDao;
import com.elm.dao.requestData.BecomeStoreData;
import com.elm.dao.requestData.ChangeStoreData;
import com.elm.dao.requestData.OrderSearchData;
import com.elm.dao.responseData.RecommendData;
import com.elm.domain.*;
import com.elm.exception.BecomeStoreException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@ResponseBody
@RestController
@RequestMapping("/api/store")
public class StoreController {

    @Autowired
    private StoreDao storeDao;

    @Autowired
    private AddressDao addressDao;

    @Autowired
    private StoreEnvDao storeEnvDao;

    @Autowired
    private CategoryDao categoryDao;

    @GetMapping("/getInfo/{storeId}")
    public R getStoreInfo(@PathVariable String storeId)
    {
        //根据storeId来获取到 商家信息 以及 商家环境 和 商家地址
        System.out.println("获取商家信息");
        Store store=storeDao.selectById(storeId);

        QueryWrapper<Address> addressQueryWrapper=new QueryWrapper<>();
        addressQueryWrapper.eq("user_id",storeId)
                .eq("identity",1);

        Address address=addressDao.selectOne(addressQueryWrapper);

        QueryWrapper<Store_env> store_envQueryWrapper=new QueryWrapper<>();
        store_envQueryWrapper.eq("store_id",storeId);

        List<Store_env> list=storeEnvDao.selectList(store_envQueryWrapper);

        Map<String,Object> data=new HashMap<>();
        data.put("store",store);
        data.put("address",address);
        data.put("storeEnv",list);

        return R.success(data);
    }

    @PostMapping("/become")
    public R becomeStore(@RequestBody BecomeStoreData becomeStoreData)
    {
        System.out.println("收到了需要成为"+becomeStoreData.getId()+"商家的请求");
        //先插入地址

        Store store=new Store(becomeStoreData.getId(),becomeStoreData.getLogoUrl(),
                becomeStoreData.getStoreUrl(),becomeStoreData.getDescription(),
                becomeStoreData.getTelephone(),2.00,becomeStoreData.getPackageFee(),
                0,becomeStoreData.getCategory(),becomeStoreData.getName(),4);
        Address address = new Address( becomeStoreData.getProvince(),
                becomeStoreData.getCity(), becomeStoreData.getDistrict(),
                becomeStoreData.getDetail(), becomeStoreData.getId(), 1, 1, null, null,
                becomeStoreData.getTelephone());

        ArrayList<Store_env> lists=new ArrayList<>();
        for (int i = 0; i < becomeStoreData.getStoreEnv().size(); i++) {
            Store_env store_env=new Store_env(becomeStoreData.getId(),becomeStoreData.getStoreEnv().get(i));
            lists.add(store_env);
        }

        //上面是新建一个store类
        QueryWrapper<Store> queryWrapper=new QueryWrapper<>();
        Store getStore = storeDao.selectById(becomeStoreData.getId());
        if(getStore!=null)
        {
            return R.error("店家已经存在！不可注册");
        }

        //这里进行 插入操作 保证数据的原子性
        try {
            becomeStore(store,address,lists);
        } catch (BecomeStoreException e) {

            e.printStackTrace();
            return R.error(e.getMessage());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return R.error("服务器繁忙，请改天再试");
        }

        return R.success("注册成功");

    }

    @PostMapping("/change")
    public R changeStoreInfo(@RequestBody ChangeStoreData changeStoreData)
    {
        System.out.println("收到了需要修改"+changeStoreData.getStore().getId()+"商家的请求");
        //先插入地址

        Store store=changeStoreData.getStore();

        Address address=changeStoreData.getAddress();

        List<Store_env> lists=changeStoreData.getStoreEnv();

        //这里进行 插入操作 保证数据的原子性
        try {
            changeStore(store,address,lists);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return R.error("服务器繁忙，请改天再试");
        }

        return R.success("修改成功");
    }

    @Transactional(rollbackFor = {Exception.class} )
    public void changeStore(Store store, Address address, List<Store_env> lists) throws Exception {
        try {
            // 修改strore
            QueryWrapper<Store> storeQueryWrapper = new QueryWrapper<>();
            storeQueryWrapper.eq("id", store.getId());
            int i = storeDao.update(store, storeQueryWrapper);
            if (i <= 0) {
                throw new Exception("更改错误");
            }

            // 修改地址
            QueryWrapper<Address> addressQueryWrapper = new QueryWrapper<>();
            addressQueryWrapper.eq("id", address.getId());
            i = addressDao.update(address, addressQueryWrapper);
            if (i <= 0) {
                throw new Exception("地址插入错误");
            }

            // 商家环境
            QueryWrapper<Store_env> store_envQueryWrapper = new QueryWrapper<>();
            store_envQueryWrapper.eq("store_id", store.getId());
            i = storeEnvDao.delete(store_envQueryWrapper);
            if (i <= 0) {
                throw new Exception("删除商家信息出错");
            }

            for (Store_env env : lists) {
                i = storeEnvDao.insert(env);
                if (i <= 0) {
                    throw new Exception("插入出错");
                }
            }
        } catch (Exception e) {
            // 异常会在此传播，触发事务回滚
            throw e;
        }
    }


    @Transactional(rollbackFor = {Exception.class} )
    public void becomeStore(Store store, Address address, ArrayList<Store_env> storeLists) throws Exception
    {
        //对这三条语句进行一个插入
        int i=0;
        i=addressDao.insert(address);
        if(i<=0) throw new BecomeStoreException("商家地址插入异常");

        QueryWrapper<Address> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("identity",1)
                .eq("user_id",address.getUserId());

        Address storeAddress = addressDao.selectOne(queryWrapper);
        if(storeAddress==null) throw new BecomeStoreException("地址未找到");
        store.setAddressId(storeAddress.getId());

        i=storeDao.insert(store);
        if(i<=0) throw new BecomeStoreException("商家插入异常");

        for (Store_env item : storeLists) {
            i=storeEnvDao.insert(item);
            if(i<=0) throw new BecomeStoreException("商家环境插入异常");
        }

        //添加一个默认分类
        Category category=new Category(null,store.getId(),1,"老板推荐",1);
        i=categoryDao.insert(category);
        if(i<=0) throw new BecomeStoreException("商家分类插入异常");
    }

    @GetMapping("/getRecommend/{userId}")
    public R getRecommendStore(@PathVariable String userId)
    {
        //暂时写一个
        List<RecommendData> list=new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            RecommendData recommendData=new RecommendData("附近","发现附近美食",null);

            List<Store> storeList=storeDao.selectList(null);
            recommendData.setList(storeList);
            list.add(recommendData);
        }
        return R.success(list);
    }

    @GetMapping("/getStore/{storeId}")
    public R getStoreOrigin(@PathVariable String storeId)
    {
        Store store=storeDao.selectById(storeId);
        if(store==null) return R.error("获取出错");
        return R.success(store);
    }

    @PutMapping("changeStatus")
    public R changeStoreStatus(@RequestBody Store store)
    {
        System.out.println(store);
        int i = storeDao.updateById(store);
        if(i<=0) return R.error("获取出错");

        return R.success(store);
    }
}

