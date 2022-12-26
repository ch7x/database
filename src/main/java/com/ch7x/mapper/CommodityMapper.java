package com.ch7x.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ch7x.domain.Commodity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommodityMapper extends BaseMapper<Commodity> {
}
