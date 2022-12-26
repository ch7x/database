package com.ch7x.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch7x.domain.Commodity;
import com.ch7x.mapper.CommodityMapper;
import com.ch7x.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommodityServiceImpl extends ServiceImpl<CommodityMapper, Commodity> implements CommodityService {
}
