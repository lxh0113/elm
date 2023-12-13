package com.elm.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.elm.common.R;
import com.elm.dao.FlavorsDao;
import com.elm.dao.GoodsDao;
import com.elm.dao.StoreDao;
import com.elm.dao.requestData.DishData;
import com.elm.dao.responseData.FlavorsListData;
import com.elm.dao.responseData.StoreGoodsData;
import com.elm.domain.Flavors;
import com.elm.domain.Goods;
import com.elm.domain.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.PastOrPresent;
import java.util.*;

@ResponseBody
@RestController
@RequestMapping("/api/goods")
public class GoodsController {

    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private FlavorsDao flavorsDao;

    @Autowired
    private StoreDao storeDao;

    @PostMapping("/all")
    public R getAllGoods(@RequestBody Goods goods)
    {
        IPage<Goods> page=new Page<>(1,7);
        if(goods.getCurrent()!=null) page.setCurrent(goods.getCurrent());

        QueryWrapper<Goods> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("store_id",goods.getStoreId());

        IPage<Goods> newPage = goodsDao.selectPage(page, queryWrapper);

        return R.success(newPage);
    }

    @PostMapping
    public R addNewDish(@RequestBody DishData dishData)
    {
        System.out.println("收到了需要添加商品的信息");
        Goods goods=dishData.getGoods();

        IPage<Goods> result= null;
        try {
            result = addNewDishOperation(goods,dishData.getFlavorsListData());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("添加失败");
        }

        return R.success(result);
    }

    @Transactional
    public IPage<Goods> addNewDishOperation(Goods goods, ArrayList<FlavorsListData> flavorsListData) throws Exception {
        System.out.println(goods);
        //分配一个UUID
        goods.setId(goods.getStoreId()+ new Date(System.currentTimeMillis()).toString());
        int insert = goodsDao.insert(goods);
        if(insert<=0) throw new Exception("插入出错");

        System.out.println(flavorsListData);
        for (FlavorsListData flavorsListDatum : flavorsListData) {
            for (int j = 0; j < flavorsListDatum.getFlavors().size(); j++) {
                //设置id
                flavorsListDatum.getFlavors().get(j).setGoodsId(goods.getId());

                insert = flavorsDao.insert(flavorsListDatum.getFlavors().get(j));
                if (insert <= 0) throw new Exception("插入出错");
            }
        }

        IPage<Goods> page=new Page<>(goods.getCurrent()==null?1:goods.getCurrent(),7);
        QueryWrapper<Goods> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("store_id",goods.getStoreId());

        return goodsDao.selectPage(page, queryWrapper);
    }

//    @PostMapping
//    public R addNewDish(@RequestBody Goods goods)
//    {
//        System.out.println("收到了需要添加商品的信息");
//        int insert = goodsDao.insert(goods);
//        if(insert<=0) return R.error("添加失败");
//
//        IPage<Goods> page=new Page<>(goods.getCurrent()==null?1:goods.getCurrent(),7);
//        QueryWrapper<Goods> queryWrapper=new QueryWrapper<>();
//        queryWrapper.eq("store_id",goods.getStoreId());
//
//        IPage<Goods> result = goodsDao.selectPage(page, queryWrapper);
//        return R.success(result);
//    }

    @DeleteMapping
    public R deleteDish(@RequestBody Goods goods)
    {
        System.out.println("收到了需要删除商品的消息"+goods.getId());
        int i = goodsDao.deleteById(goods.getId());
        if(i<=0) return R.error("删除失败");

        IPage<Goods> page=new Page<>(goods.getCurrent(),7);
        QueryWrapper<Goods> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("store_id",goods.getStoreId());

        IPage<Goods> result = goodsDao.selectPage(page, queryWrapper);

        return R.success(result);
    }

    @PutMapping
    public R modifyDish(@RequestBody Goods goods)
    {
        System.out.println("收到一个修改商品的消息");
        QueryWrapper<Goods> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",goods.getId());

        int update = goodsDao.update(goods, queryWrapper);
        if(update<=0) return R.error("修改失败");

        queryWrapper.clear();
        IPage<Goods> page=new Page<>(1,7);
        if(goods.getCurrent()!=null) page.setCurrent(goods.getCurrent());

        queryWrapper.eq("store_id",goods.getStoreId());

        IPage<Goods> result = goodsDao.selectPage(page, queryWrapper);
        return R.success(result);
    }

    @PostMapping("/search")
    public R searchGoods(@RequestBody Goods goods)
    {
        System.out.println(goods);

        IPage<Goods> page=new Page<>(1,7);
        if(goods.getCurrent()!=0) page.setCurrent(goods.getCurrent());


        QueryWrapper<Goods> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(!goods.getName().equals(""),"name",goods.getName())
                .eq(goods.getCategoryId()!=0,"category_id",goods.getCategoryId())
                .eq("store_id",goods.getStoreId())
                .eq(goods.getStatus()!=2,"status",goods.getStatus());

        IPage<Goods> iPage = goodsDao.selectPage(page, queryWrapper);

        return R.success(iPage);
    }

    @DeleteMapping("/muldel")
    public R mulDeleteDish(@RequestBody ArrayList<Goods> lists)
    {
        try {
            IPage<Goods> result = multiplyDelete(lists);
            return R.success(result);
        }catch (Exception e)
        {
            e.printStackTrace();
            return R.error("删除出错");
        }
    }

    @Transactional
    public IPage<Goods> multiplyDelete(ArrayList<Goods> lists) throws Exception {

        for (int i = 0; i < lists.size(); i++) {
            int delete = goodsDao.deleteById(lists.get(i).getId());
            if(delete<=0) throw new Exception("删除出错");
        }

        IPage<Goods> page=new Page<>(1,7);
        QueryWrapper<Goods> queryWrapper=new QueryWrapper<>();
        page=goodsDao.selectPage(page,queryWrapper);

        return page;
    }

    @PostMapping("/category")
    public R getGoodsByCategory(@RequestBody Goods goods)
    {
        QueryWrapper<Goods> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("store_id",goods.getStoreId())
                .eq("category_id",goods.getCategoryId());

        List<Goods> list=goodsDao.selectList(queryWrapper);
        return R.success(list);
    }

    @GetMapping("/data/{id}")
    public R getGoodsData(@PathVariable String id)
    {
        //先获取到 商品信息
        Goods goods=goodsDao.selectById(id);

        QueryWrapper<Flavors> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("goods_id",id)
                .groupBy("name","options","price","id","goods_id");
        List<Flavors> list=flavorsDao.selectList(queryWrapper);

        ArrayList<FlavorsListData> flavorLists=new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            //如果出现了新的名字 是这样的
            if(i==0||!list.get(i).getName().equals(list.get(i-1).getName()))
            {
                ArrayList<Flavors> arrayList=new ArrayList<>();
                arrayList.add(list.get(i));
                FlavorsListData flavorsListData=new FlavorsListData(list.get(i).getName(),arrayList);

                flavorLists.add(flavorsListData);
            }
            else
            {
                //否则
                FlavorsListData flavorsListData=flavorLists.get(flavorLists.size()-1);
                flavorsListData.getFlavors().add(list.get(i));

                flavorLists.set(flavorLists.size()-1,flavorsListData);
            }
        }
        DishData dishData=new DishData(goods,flavorLists);

        //这个地方还要考虑到数据在前端的使用
        return R.success(dishData);
    }

    @PostMapping("/user/search")
    public R search(@RequestParam String text,@RequestParam Boolean status,@RequestParam Integer choose)
    {
        System.out.println(text+"---"+status+"---"+choose);
        List<StoreGoodsData> list=new ArrayList<>();

        //先搜索店铺，在搜索商品
        if(status)
        {
            //搜索商家
            QueryWrapper<Store> storeQueryWrapper=new QueryWrapper<>();
            storeQueryWrapper.like("name",text);

            IPage page=new Page();
            page.setCurrent(1);
            page.setSize(5);

            IPage storeIpage = storeDao.selectPage(page, storeQueryWrapper);

            List<Store> storeList=storeIpage.getRecords();
            for (int i = 0; i < storeList.size(); i++) {

                StoreGoodsData storeGoodsData=new StoreGoodsData();
                storeGoodsData.setStore(storeList.get(i));

                QueryWrapper<Goods> queryWrapper=new QueryWrapper<>();
                queryWrapper
                        .like("name",text)
                        .or()
                        .like("name","");
                queryWrapper.eq("store_id",storeList.get(i).getId())
                        .eq("status",1);

                IPage iPage=new Page();
                iPage.setSize(3);

                IPage selectPage = goodsDao.selectPage(iPage, queryWrapper);

                storeGoodsData.setList(selectPage.getRecords());

                list.add(storeGoodsData);
            }

            return R.success(list).add("page",storeIpage);
        }
        else
        {
            //搜索商品
            QueryWrapper<Goods> goodsQueryWrapper=new QueryWrapper<>();
            goodsQueryWrapper.like("name",text)
                    .eq("status",1)
                    .groupBy("store_id","id","url","status","description","category_id","price","name");


            List<Goods> goodsList=goodsDao.selectList(goodsQueryWrapper);

            for (int i = 0; i < goodsList.size(); i++) {

                if(list.size()!=0&&list.get(list.size()-1).getList().size()>3) continue;

                if(i==0||!goodsList.get(i).getStoreId().equals(goodsList.get(i-1).getStoreId()))
                {
                    StoreGoodsData storeGoodsData=new StoreGoodsData();
                    Store store=storeDao.selectById(goodsList.get(i).getStoreId());

                    List<Goods> goodList=new ArrayList<>();
                    goodList.add(goodsList.get(i));

                    storeGoodsData.setList(goodList);
                    storeGoodsData.setStore(store);

                    list.add(storeGoodsData);
                }
                else
                {
                    list.get(list.size()-1).getList().add(goodsList.get(i));
                }

                if(list.size()>5) break;
            }


        }
        return R.success(list);
    }

}
