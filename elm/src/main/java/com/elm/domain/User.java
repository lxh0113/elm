package com.elm.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.models.auth.In;

@TableName("user")
public class User {
    private String id;

    private Integer gender;

    private String nickname;

    private Integer age;

    private String telephone;

    private String email;

    private String avatar;

    private Integer status;

    private Integer identity;

    private String password;

    public User() {
    }

    public User(String id, Integer gender, String nickname, Integer age, String telephone, String email, String avatar, Integer status, Integer identity, String password) {
        this.id = id;
        this.gender = gender;
        this.nickname = nickname;
        this.age = age;
        this.telephone = telephone;
        this.email = email;
        this.avatar = avatar;
        this.status = status;
        this.identity = identity;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIdentity() {
        return identity;
    }

    public void setIdentity(Integer identity) {
        this.identity = identity;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
