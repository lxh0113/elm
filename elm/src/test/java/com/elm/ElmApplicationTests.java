package com.elm;

import com.elm.dao.UserDao;
import com.elm.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ElmApplicationTests {

    @Autowired
    private UserDao userDao;

    @Test
    void contextLoads() {
        User user=userDao.selectById("4345516958");
        System.out.println(user);
    }

}
