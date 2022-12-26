package com.ch7x.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch7x.domain.Putwh;
import com.ch7x.mapper.PutwhMapper;
import com.ch7x.service.PutwhService;
import org.springframework.stereotype.Service;

@Service
public class PutwhServiceImpl extends ServiceImpl<PutwhMapper, Putwh> implements PutwhService {
}
