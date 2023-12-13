package com.elm.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class User {
    @TableId
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

}
