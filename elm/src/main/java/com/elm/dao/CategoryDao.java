package com.elm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.elm.domain.Category;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryDao extends BaseMapper<Category> {
}
