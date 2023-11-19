package com.elm.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.elm.common.R;
import com.elm.dao.FlavorsDao;
import com.elm.dao.responseData.FlavorsListData;
import com.elm.domain.Flavors;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@ResponseBody
@RestController
@RequestMapping("/api/flavors")
public class FlavorsController {

    @Autowired
    private FlavorsDao flavorsDao;

    @GetMapping("/{goodsId}")
    public R getAllFlavors(@PathVariable String goodsId)
    {
        System.out.println("看看我进去了嘛");
        try{
            List<FlavorsListData> lists = getAll(goodsId);
            return R.success(lists);
        }catch (Exception e)
        {
            e.printStackTrace();
            return R.error("获取出错");
        }
    }

    @PostMapping
    public R addNewFlavors(@RequestBody Flavors flavors)
    {
        int insert = flavorsDao.insert(flavors);
        if(insert<=0) return R.error("添加失败");

        //添加成功后 重新显示
        try{
            List<FlavorsListData> lists = getAll(flavors.getGoodsId());
            return R.success(lists);
        }catch (Exception e)
        {
            e.printStackTrace();
            return R.error("获取出错");
        }
    }

    @PutMapping
    public R changeFlavors(@RequestBody Flavors flavors)
    {
        QueryWrapper<Flavors> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",flavors.getId());

        int update = flavorsDao.update(flavors, queryWrapper);
        if(update<=0) return R.error("更新出错");

        try{
            List<FlavorsListData> lists = getAll(flavors.getGoodsId());
            return R.success(lists);
        }catch (Exception e)
        {
            e.printStackTrace();
            return R.error("获取出错");
        }
    }

    //这个是删除单个
    @DeleteMapping
    public R deleteFlavors(@RequestBody Flavors flavors)
    {

        int i = flavorsDao.deleteById(flavors.getId());
        if(i<=0) return R.error("删除出错");

        try{
            List<FlavorsListData> lists = getAll(flavors.getGoodsId());
            return R.success(lists);
        }catch (Exception e)
        {
            e.printStackTrace();
            return R.error("获取出错");
        }
    }

    //删除多个
    @PostMapping("/delName")
    public R deleteAllFlavorsByName(@RequestBody Flavors flavors)
    {
        QueryWrapper<Flavors> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("goods_id",flavors.getGoodsId())
                .eq("name",flavors.getName());

        int delete = flavorsDao.delete(queryWrapper);
        if(delete<=0) return R.error("删除出错");

        try{
            List<FlavorsListData> lists = getAll(flavors.getGoodsId());
            return R.success(lists);
        }catch (Exception e)
        {
            e.printStackTrace();
            return R.error("获取出错");
        }
    }

    public List<FlavorsListData> getAll(String goodsId)
    {
        QueryWrapper<Flavors> queryWrapper=new QueryWrapper<>();
//        queryWrapper.select("*", "SUM(goods_id) as totalValue")
//                .groupBy("name","price");
        queryWrapper.eq("goods_id",goodsId)
                .groupBy("name","id","goods_id","price","options");
        //上面是设置查询
        List<Flavors> flavors = flavorsDao.selectList(queryWrapper);

        ArrayList<FlavorsListData> list=new ArrayList<>();
        for (int i = 0; i < flavors.size(); i++) {
            if(i==0||!flavors.get(i).getName().equals(flavors.get(i-1).getName()))
            {
                //出现了新的 需要new
                FlavorsListData flavorsListData=new FlavorsListData();
                flavorsListData.setName(flavors.get(i).getName());
                ArrayList<Flavors> newFlavors=new ArrayList<>();
                newFlavors.add(flavors.get(i));
                flavorsListData.setFlavors(newFlavors);

                list.add(flavorsListData);
            }
            else
            {
                ArrayList<Flavors> newFlavors=list.get(list.size()-1).getFlavors();
                newFlavors.add(flavors.get(i));

                FlavorsListData flavorsListData=new FlavorsListData(list.get(list.size()-1).getName(),newFlavors);

                list.set(list.size()-1, flavorsListData);
            }
        }
        return list;
    }
}
