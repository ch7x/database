package com.ch7x.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch7x.domain.Commodity;
import com.ch7x.mapper.CommodityMapper;
import com.ch7x.service.CommodityService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommodityServiceImpl extends ServiceImpl<CommodityMapper, Commodity> implements CommodityService {

    @Autowired
    private CommodityMapper commodityMapper;

    @Override
    public IPage<Commodity> getPage(int currentPage, int pageSize, Commodity commodity) {
        LambdaQueryWrapper<Commodity> lqw = new LambdaQueryWrapper<>();
        lqw.like(Strings.isNotEmpty(commodity.getCName()) , Commodity::getCName, commodity.getCName());
        lqw.like(Strings.isNotEmpty(commodity.getCManufacturer()) , Commodity::getCManufacturer, commodity.getCManufacturer());
        IPage<Commodity> page = new Page<>(currentPage, pageSize);
        commodityMapper.selectPage(page,lqw);
        return page;
    }
}
