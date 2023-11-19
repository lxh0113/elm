package com.elm.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.elm.common.R;
import com.elm.dao.UserDao;
import com.elm.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.util.UUID;

@ResponseBody
@RestController
@RequestMapping("/api/upload")
public class UploadController {

    @Autowired
    private UserDao userDao;

    @PostMapping("/userAvatar") // 在URL中添加用户ID参数
    public R upload(@RequestParam("userId") String userId,@RequestPart MultipartFile file) {
        // 从URL中获取用户ID
        if(userId==null)
        {
            return R.error("用户未登录");
        }
        try {
            // 例如：将文件保存到指定文件夹
            String fileName = file.getOriginalFilename();
            String suffixName = null;
            if (fileName != null) {
                suffixName = fileName.substring(fileName.lastIndexOf("."));
            }
            fileName = UUID.randomUUID() + suffixName;
            fileName=userId+fileName;
            String filePath = "F:\\elmImg\\";
            file.transferTo(new File(filePath + fileName));

            User user=new User();
            user.setAvatar("http://localhost:8081/img/"+fileName);
            user.setId(userId);

            QueryWrapper<User> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("id",userId);

            userDao.update(user,queryWrapper);

            System.out.println("http://localhost:8081/img/"+fileName);
            return R.success("http://localhost:8081/img/"+fileName);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("上传失败");
        }
    }
}
