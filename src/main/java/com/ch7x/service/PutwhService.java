package com.ch7x.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ch7x.domain.Commodity;
import com.ch7x.domain.Putwh;
import com.ch7x.dto.PutwhDto;

public interface PutwhService extends IService<Putwh> {
    IPage<Commodity> getPage(int currentPage, int pageSize, PutwhDto putwhDto);
}
