package com.elm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.elm.domain.Comments;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentsDao extends BaseMapper<Comments> {
}
