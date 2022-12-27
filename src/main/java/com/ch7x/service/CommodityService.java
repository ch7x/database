package com.ch7x.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ch7x.domain.Commodity;
import com.ch7x.dto.CommodityDto;


public interface CommodityService extends IService<Commodity> {
    IPage<Commodity> getPage(int currentPage, int pageSize, CommodityDto commodity);

    IPage<CommodityDto> findPage(Page<CommodityDto> page, QueryWrapper<CommodityDto> queryWrapper);
}
