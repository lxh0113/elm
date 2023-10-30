package com.elm.utils.Id;


import com.elm.dao.UserDao;
import com.elm.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

@Service
public class CheckDuplicate {

    @Autowired
    private UserDao userDao;

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
