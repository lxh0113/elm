package com.elm.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.elm.common.R;
import com.elm.dao.MessageDao;
import com.elm.dao.UserDao;
import com.elm.domain.Message;
import com.elm.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@ResponseBody
@RequestMapping("/api/message")
public class MessageController {

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private UserDao userDao;

    @PostMapping("/lists")
    public R getMessageList(@RequestBody Message message)
    {
        IPage<Message> iPage=new Page<>();
        iPage.setSize(message.getSize());
        iPage.setCurrent(1);
        //获取聊天列表
        QueryWrapper<Message> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("sender_id",message.getSenderId())
                .eq("receiver_Id",message.getReceiverId())
                .or().
                eq("sender_Id",message.getReceiverId())
                        .eq("receiver_Id",message.getSenderId());

        queryWrapper.orderBy(true,false,"time");

        IPage<Message> res = messageDao.selectPage(iPage, queryWrapper);
        List<Message> list=res.getRecords();

        //翻转数组是因为 需要这样显示
        Collections.reverse(list);
        if (list!=null && (list.size() < message.getSize())) {

            return R.success(list).add("isEnd",true);
        }
        //说明没有更多聊天记录了

        return R.success(list).add("isEnd",false);
    }

    @PostMapping("/people")
    public R getPeoples(@RequestBody Message message)
    {
        //
        List<String> friends = messageDao.selectLeftFriends(message.getSenderId());
        List<User> users=new ArrayList<>();
        List<Message> list=new ArrayList<>();

        for (int i = 0; i < friends.size(); i++) {
            User user = userDao.selectById(friends.get(i));
            User newUser=new User(user.getId(),user.getGender(),user.getNickname(),user.getAge(),null,null,user.getAvatar(),null,null,null);

//            QueryWrapper<Message> queryWrapper=new QueryWrapper<>();
//            queryWrapper.eq("sender_id",message.getSenderId())
//                    .eq("receiver_Id",message.getReceiverId())
//                    .or().
//                    eq("sender_Id",message.getReceiverId())
//                    .eq("receiver_Id",message.getSenderId());
//
//            queryWrapper.orderBy(true,false,"time");
//            Message mes = messageDao.selectOne(queryWrapper);

            users.add(newUser);
        }

        return R.success(users);
    }

}

