package com.elm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.elm.domain.Goods;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GoodsDao extends BaseMapper<Goods> {
}
