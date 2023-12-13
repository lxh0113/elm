package com.elm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.elm.domain.ShopCart;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShopCartDao extends BaseMapper<ShopCart> {
}
