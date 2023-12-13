package com.elm.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.elm.common.R;
import com.elm.dao.CommentsDao;
import com.elm.dao.requestData.CommentsSearchData;
import com.elm.domain.Comments;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@ResponseBody
@RequestMapping("/api/comments")
public class CommentsController {

    @Autowired
    private CommentsDao commentsDao;

    @PostMapping
    public R getRemarkedLists(@RequestBody CommentsSearchData commentsSearchData)
    {
        QueryWrapper<Comments> queryWrapper=new QueryWrapper<>();

        queryWrapper.eq("sender_id",commentsSearchData.getUserId());

        IPage<Comments> iPage=new Page<>();
        iPage.setCurrent(commentsSearchData.getCurrent());
        iPage.setSize(5);

        IPage<Comments> res = commentsDao.selectPage(iPage, queryWrapper);

        return R.success(res);
    }
}
