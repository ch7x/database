package com.ch7x.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ch7x.domain.Commodity;


public interface CommodityService extends IService<Commodity> {
    IPage<Commodity> getPage(int currentPage, int pageSize,Commodity commodity);
}
