package com.ch7x.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch7x.domain.Outwh;
import com.ch7x.mapper.OutwhMapper;
import com.ch7x.service.OutwhService;
import org.springframework.stereotype.Service;

@Service
public class OutwhServiceImpl extends ServiceImpl<OutwhMapper, Outwh> implements OutwhService {
}
