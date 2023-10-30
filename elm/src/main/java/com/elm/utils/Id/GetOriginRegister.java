package com.elm.utils.Id;


import com.elm.domain.User;

public class GetOriginRegister {

    public static User getUserOrigin()
    {
        //设置原始的数据
        User user=new User();
        user.setGender(0);
        user.setNickname("饿了么小用户");
        user.setAge(0);
        user.setAvatar("");
        user.setStatus(1);
        user.setIdentity(1);

        return user;
    }

//    public User getStoreOrigin(User user)
//    {
//
//        return user;
//    }

}
