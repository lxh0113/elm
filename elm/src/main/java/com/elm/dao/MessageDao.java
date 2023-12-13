package com.elm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.elm.domain.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface MessageDao extends BaseMapper<Message> {

    @Select("SELECT DISTINCT column_name FROM (SELECT receiver_id AS column_name FROM message WHERE sender_id = #{userId} UNION SELECT sender_id AS column_name FROM message WHERE receiver_id = #{userId}) AS subquery")
    List<String> selectLeftFriends(String userId);

}
