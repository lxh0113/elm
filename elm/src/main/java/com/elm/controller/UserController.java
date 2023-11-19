package com.elm.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.elm.common.R;
import com.elm.dao.UserDao;
import com.elm.dao.requestData.PreData;
import com.elm.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ResponseBody
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserDao userDao;

    @Autowired
    HttpServletRequest request;

    @Autowired
    HttpServletResponse response;

    @PutMapping
    public R changeInfo(@RequestBody User user)
    {
        System.out.println("收到了数据"+user);
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",user.getId());

        int i = userDao.update(user, queryWrapper);
        if(i>0)
        {
            System.out.println("修改成功");
            //修改成功
            return R.success("修改成功");
        }
        System.out.println("修改失败");
        return R.error("修改失败");
    }
}
