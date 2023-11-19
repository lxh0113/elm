package com.elm.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.elm.common.R;
import com.elm.dao.CategoryDao;
import com.elm.dao.requestData.TwoCategoryData;
import com.elm.domain.Category;
import com.elm.exception.BecomeStoreException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@ResponseBody
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryDao categoryDao;

    @PostMapping("/all")
    public R getAllCategory(@RequestBody Category category)
    {
        System.out.println("获取到了"+category.getStoreId()+"的请求");

        QueryWrapper<Category> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("store_id",category.getStoreId())
                .orderBy(true,true,"sort");
        List<Category> categoryLists = categoryDao.selectList(queryWrapper);

        return R.success(categoryLists);
    }

    @PutMapping
    public R addNewCategory(@RequestBody Category category)
    {
        System.out.println("收到了增加的请求");

        int insert = categoryDao.insert(category);
        if(insert<=0) return R.error("添加失败");

        QueryWrapper<Category> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("store_id",category.getStoreId())
                .orderBy(true,true,"sort");

        List<Category> list=categoryDao.selectList(queryWrapper);

        return R.success(list);
    }

    @DeleteMapping
    public R deleteCategory(@RequestBody Category category)
    {
        System.out.println("收到了删除的请求");

        //删除的时候update一下

        QueryWrapper<Category> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",category.getId());
        int delete = categoryDao.delete(queryWrapper);
        if(delete<=0) return R.error("删除失败");

        //将所有的排序字段减去1 因为已经删除了
        UpdateWrapper<Category> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("store_id",category.getStoreId())
                .gt("sort",category.getSort())
                        .setSql("sort=sort-1");

        categoryDao.update(null, updateWrapper);

        //删除后获取到新的列表返回
        queryWrapper.clear();
        queryWrapper.eq("store_id",category.getStoreId())
                .orderBy(true,true,"sort");
        List<Category> list=categoryDao.selectList(queryWrapper);

        return R.success(list);
    }

    @PostMapping
    public R changeCategory(@RequestBody Category category)
    {
        System.out.println("收到了更改的请求");

        QueryWrapper<Category> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",category.getId());

        int update = categoryDao.update(category, queryWrapper);
        if(update<=0) return R.error("更改失败");

        queryWrapper.clear();
        queryWrapper.eq("store_id",category.getStoreId())
                .orderBy(true,true,"sort");

        List<Category> list=categoryDao.selectList(queryWrapper);
        return R.success(list);
    }

    @PostMapping("/change")
    public R modifyCategory(@RequestBody ArrayList<Category> list)
    {
        System.out.println("收到了需要改变顺序的请求");
        //俩个category交换
//        ArrayList<Category> list=twoCategoryData.getList();
        try {
            List<Category> res=changeTwoCategory(list);
            if(res==null)
                return R.error("修改失败");
            else return R.success(res);
        }catch (Exception e)
        {
            e.printStackTrace();
            return R.error("修改失败");
        }

    }


    @PostMapping("/search")
    public R searchCategory(@RequestBody Category category)
    {
        System.out.println("收到了需要查询的消息");
        QueryWrapper<Category> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("store_id",category.getStoreId())
                .like(!category.getName().equals(""),"name",category.getName());

        List<Category> list=categoryDao.selectList(queryWrapper);

        return R.success(list);
    }

    @Transactional(rollbackFor = {Exception.class})
    public List<Category> changeTwoCategory(List<Category> list) throws Exception
    {
        List<Category> res=null;

        if(list.size()!=2)
        {
            throw new Exception("数组不为2");
        }
        //交换 sort 字段
        int t=list.get(0).getSort();
        list.get(0).setSort(list.get(1).getSort());
        list.get(1).setSort(t);

        //找到第一个替换成第二个字段
        QueryWrapper<Category> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",list.get(0).getId());

        int update=categoryDao.update(list.get(0),queryWrapper);
        if(update<=0) throw new Exception("未成功更改");

        queryWrapper.clear();
        queryWrapper.eq("id",list.get(1).getId());

        update=categoryDao.update(list.get(1),queryWrapper);
        if(update<=0) throw new Exception("未成功更改");

        queryWrapper.clear();
        queryWrapper.eq("store_id",list.get(0).getStoreId())
                .orderBy(true,true,"sort");

        res=categoryDao.selectList(queryWrapper);
        return res;
    }
}
