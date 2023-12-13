package com.elm.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.elm.common.R;
import com.elm.dao.FlavorsDao;
import com.elm.dao.GoodsDao;
import com.elm.dao.ShopCartDao;
import com.elm.dao.StoreDao;
import com.elm.dao.requestData.ShopCartSearchData;
import com.elm.dao.responseData.ShopCartResData;
import com.elm.domain.Flavors;
import com.elm.domain.Goods;
import com.elm.domain.ShopCart;
import com.elm.domain.Store;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@EnableTransactionManagement
@Slf4j
@RestController
@ResponseBody
@RequestMapping("/api/shopcart")
public class ShopCartController {

    @Autowired
    private ShopCartDao shopCartDao;

    @Autowired
    private StoreDao storeDao;

    @Autowired
    private FlavorsDao flavorsDao;

    @Autowired
    private GoodsDao goodsDao;


    @PostMapping
    public R getShopList(@RequestBody ShopCartSearchData shopCartSearchData)
    {

        //得到所有的列表
        QueryWrapper<ShopCart> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",shopCartSearchData.getUserId())
                .orderBy(true,false,"store_id")
                .groupBy("store_id","goods_id","user_id","goods_info","number","time","id");

        IPage<ShopCart> iPage=new Page<>();
//        iPage.setSize(7);
        iPage.setCurrent(shopCartSearchData.getCurrent());

        IPage<ShopCart> res = shopCartDao.selectPage(iPage, queryWrapper);
        List<ShopCart> list=res.getRecords();

        //开始遍历
        List<ShopCartResData> result=new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {

            List<Flavors> listStr = JSON.parseArray(list.get(i).getGoodsInfo(), Flavors.class);
            for (Flavors flavors : listStr) {
                System.out.println(flavors.getId());
            }

            list.get(i).setFlavors(listStr);

            Goods goods = goodsDao.selectById(list.get(i).getGoodsId());
            list.get(i).setGoods(goods);

            //先设置flavors
            for (int j = 0; list.get(i).getFlavors()!=null && j < list.get(i).getFlavors().size(); j++) {
                Flavors flavors=list.get(i).getFlavors().get(j);

                Flavors select = flavorsDao.selectById(flavors.getId());
                if(select==null)
                {
                    //选择其他的
                    QueryWrapper<Flavors> flavorsQueryWrapper=new QueryWrapper<>();
                    flavorsQueryWrapper.eq("name",flavors.getName())
                            .eq("goods_id",flavors.getGoodsId());

                    List<Flavors> selectList = flavorsDao.selectList(flavorsQueryWrapper);
                    if(selectList!=null)
                    {
                        //如果其他口味不为null
                        flavors=selectList.get(0);
                        list.get(i).getFlavors().set(j,selectList.get(0));
                    }
                    else
                    {
                        //删除这个口味
                        flavorsDao.deleteById(flavors.getId());
                        list.get(i).getFlavors().remove(j);
                    }
                }
                else
                {
                    list.get(i).getFlavors().set(j,select);
                }
            }

            if(i==0|| !Objects.equals(list.get(i).getStoreId(), list.get(i - 1).getStoreId()))
            {
                ShopCartResData shopCartResData=new ShopCartResData();
                Store store=storeDao.selectById(list.get(i).getStoreId());

                shopCartResData.setStore(store);
                shopCartResData.setShopCarts(new ArrayList<>());
                shopCartResData.getShopCarts().add(list.get(i));

                result.add(shopCartResData);
                //还有flavors需要考虑
            }
            else
            {
                result.get(result.size()-1).getShopCarts().add(list.get(i));
            }

        }

        res.setRecords(null);

        List<ShopCartResData> shopCartResDataList=new ArrayList<>();

        for (int i = (shopCartSearchData.getCurrent()-1)*5; i<result.size()&&i < (shopCartSearchData.getCurrent()-1)+5; i++) {
            shopCartResDataList.add(result.get(i));
        }

        return R.success(res).add("res",shopCartResDataList);
    }

    @PutMapping
    public R addToShopList(@RequestBody ShopCart shopCart)
    {
        //加入购物车
        String s = JSON.toJSONString(shopCart.getFlavors());
        shopCart.setGoodsInfo(s);
        shopCart.setTime(new Date(System.currentTimeMillis()));

        QueryWrapper<ShopCart> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",shopCart.getUserId())
                .eq("goods_id",shopCart.getGoodsId())
                .eq("goods_info",shopCart.getGoodsInfo())
                .eq("store_id",shopCart.getStoreId());

        ShopCart one = shopCartDao.selectOne(queryWrapper);
        if(one!=null)
        {
            one.setTime(new Date(System.currentTimeMillis()));
            one.setNumber(one.getNumber()+1);
            int i = shopCartDao.updateById(one);
            if(i<=0) return R.error("添加失败");
            return R.success(one);
        }

        int insert = shopCartDao.insert(shopCart);
        if(insert<=0) return R.error("添加失败");

        return R.success(shopCart);
    }

    @DeleteMapping("/{id}")
    public  R deleteOne(@PathVariable Integer id)
    {
        //删除一个
        int i = shopCartDao.deleteById(id);
        if(i<=0) return R.error("出错了");
        return R.success(i);
    }

    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping
    public R deleteList(@RequestBody List<Integer> list) throws Exception {
        R r=null;
        System.out.println(list);
        for (int i = 0; i < list.size(); i++) {
            int id = shopCartDao.deleteById(list.get(i));

            if(id<=0)
            {
                throw new Exception("出错了");
            }
        }

        return R.success("成功");
    }

    @PostMapping("/update")
    public R updateShopCart(@RequestBody ShopCart shopCart)
    {
        int i = shopCartDao.updateById(shopCart);
        if(i<=0) return R.error("更新失败");
        return R.success(i);
    }
}
