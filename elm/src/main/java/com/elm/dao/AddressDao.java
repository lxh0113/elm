package com.elm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.elm.domain.Address;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AddressDao extends BaseMapper<Address> {
}
