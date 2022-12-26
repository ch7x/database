package com.ch7x.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch7x.domain.Warehouse;
import com.ch7x.mapper.WarehouseMapper;
import com.ch7x.service.WarehouseService;
import org.springframework.stereotype.Service;

@Service
public class WarehouseServiceImpl extends ServiceImpl<WarehouseMapper, Warehouse> implements WarehouseService {
}
