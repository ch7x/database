package com.ch7x.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.ch7x.domain.Commodity;
import com.ch7x.dto.CommodityDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CommodityMapper extends BaseMapper<Commodity> {
    IPage<CommodityDto> findPage(IPage<CommodityDto> page, @Param(Constants.WRAPPER) QueryWrapper<CommodityDto> wrapper);

}
