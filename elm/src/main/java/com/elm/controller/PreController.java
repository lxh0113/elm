package com.elm.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.elm.common.R;
import com.elm.dao.UserDao;
import com.elm.dao.requestData.PreData;
import com.elm.domain.User;
import com.elm.utils.Email.EmailCaptcha;
import com.elm.utils.Email.ManageCaptcha;
import com.elm.utils.Email.SendCaptcha;
import com.elm.utils.Id.GetId;
import com.elm.utils.Id.GetOriginRegister;
import com.elm.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

@ResponseBody
@RestController
@RequestMapping("/api/pre")
public class PreController {

    @Autowired
    private UserDao userDao;

    @Autowired
    HttpServletRequest request;

    @Autowired
    HttpServletResponse response;


    //@CrossOrigin
    @PostMapping("/login")
    public R login(HttpServletRequest request,@RequestBody PreData preData)
    {
        if(preData==null) return R.error("登录失败");
        User getUser=userDao.selectById(preData.getId());
        System.out.println(preData.getPassword());
        System.out.println(DigestUtils.md5DigestAsHex(preData.getPassword().getBytes()));
        if(getUser==null)
        {
            System.out.println("用户不存在");
            return R.error("用户不存在");
        }
        else if(!(getUser.getPassword().equals(DigestUtils.md5DigestAsHex(preData.getPassword().getBytes()))))
        {
            return R.error("密码错误");
        }
        else {
            getUser.setPassword(null);
            setToken(getUser.getId(),getUser.getIdentity());

            //设置 userId 在 session 里面
            request.getSession().setAttribute("userId",getUser.getId());
            return R.success(getUser);
        }
    }

    @PostMapping("/user")
    public R getUser(@RequestBody PreData preData)
    {
        User getUser = userDao.selectById(preData.getId());
        System.out.println(preData);
        if(getUser==null)
        {
            System.out.println("出错了");
            return R.error("出错了");
        }
        else
        {
            getUser.setPassword(null);

            request.getSession().setAttribute("userId",getUser.getId());
            return R.success(getUser);
        }
//        return R.error("出错了");
    }

    @PostMapping("/register")
    public R register(@RequestBody PreData preData)
    {
        //先判断邮箱是否已经有了
        QueryWrapper<User> queryWrapper=new QueryWrapper<User>();
        queryWrapper.select()
                .eq("email",preData.getEmail());

        //进行筛选
        User newUser = userDao.selectOne(queryWrapper);
        if(newUser!=null)
        {
            //说明当前已经拿这个邮箱注册过了
            return R.error("已有账号，去登陆！");
        }
        else
        {
            EmailCaptcha emailCaptcha=ManageCaptcha.GetcaptchaByEmail(preData.getEmail(),new Date());

            if(emailCaptcha!=null&& Objects.equals(emailCaptcha.getCaptcha(), preData.getCaptcha()))
            {
                //开始注册
                User originUser = GetOriginRegister.getUserOrigin();
                //进行md5加密
                originUser.setPassword(DigestUtils.md5DigestAsHex(preData.getPassword().getBytes()));
                originUser.setEmail(preData.getEmail());
                //给一个全局的id
                originUser.setId(check(0));
                int k = userDao.insert(originUser);

                System.out.println(k);
                if(k>0)
                {
                    System.out.println("注册成功");
                    setToken(originUser.getId(),originUser.getIdentity());

                    request.getSession().setAttribute("userId",originUser.getId());
                    return R.success(originUser);
                }
                else
                {
                    System.out.println("注册失败");
                    return R.error("注册失败");
                }
            }
            else
            {
                return R.error("验证码错误");
            }

        }
    }

    @PostMapping("/forget")
    public R forgetPassword(@RequestBody PreData preData)
    {
        //判断验证码
        EmailCaptcha emailCaptcha = ManageCaptcha.GetcaptchaByEmail(preData.getEmail(), new Date());
        if(emailCaptcha==null)
        {
            return R.error("验证码错误");
        }
        else if(emailCaptcha.getEmail().equals(preData.getEmail()))
        {
            QueryWrapper<User> queryWrapper=new QueryWrapper<User>();
            queryWrapper.eq("email",preData.getEmail());
            User getUser=userDao.selectOne(queryWrapper);

            getUser.setPassword(null);

            setToken(getUser.getId(),getUser.getIdentity());

            request.getSession().setAttribute("userId",getUser.getId());

            return R.success(getUser);
        }
        return R.error("登录失败");
    }

    @PostMapping("/getCaptcha")
    public R getCaptcha(@RequestBody PreData preData)
    {
        System.out.println("获取到的邮箱是"+preData.getEmail());
        //然后这是是发送验证码
        SendCaptcha sendCaptcha=new SendCaptcha();
        String yzm=null;
        try {
            yzm = sendCaptcha.getYzm(preData.getEmail());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        EmailCaptcha emailCaptcha=new EmailCaptcha(preData.getEmail(),yzm,new Date());
        ManageCaptcha.AddCaptchaByEmail(emailCaptcha);

        return R.success("发送成功");
    }

    public void setToken(String id, Integer identity)
    {
        Map<String,String> map= JwtUtils.getToken(id,identity);

        System.out.println(map.get("token"));
        System.out.println(map.get("long_token"));

//        response.addHeader("Access-Control-Expose-Headers", "token, long_token");

        response.addHeader("token", map.get("token"));
        response.addHeader("long_token", map.get("long_token"));

//        response.setHeader("Access-Control-Allow-Headers", "Content-Type,token");
//        response.setHeader("Access-Control-Allow-Headers", "Content-Type,long_token");
//
//
//        response.addHeader("Access-Control-Expose-Headers","token");
//        response.addHeader("token",map.get("token"));
//
////        response.setHeader("Access-Control-Allow-Headers","token,content-type");
//
//        response.addHeader("Access-Control-Expose-Headers","long_token");
//        response.addHeader("long_token",map.get("long_token"));

    }

    public String check(int flag)
    {
        String id=null;
        while(true)
        {
            GetId getId=new GetId();
            long newid=getId.nextId();
            //获取到了 id
            id= String.valueOf(newid).substring(0,10);
            if(flag>0) id="store_"+id;

            User user=userDao.selectById(id);

            if(user!=null&&user.getId().equals(id))
            {
                id=null;
            }
            else break;
        }
        return id;
    }
}
