package com.elm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.elm.domain.Store;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface StoreDao extends BaseMapper<Store> {
}
