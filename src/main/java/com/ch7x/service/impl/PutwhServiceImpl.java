package com.ch7x.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch7x.domain.Commodity;
import com.ch7x.domain.Putwh;
import com.ch7x.dto.PutwhDto;
import com.ch7x.mapper.PutwhMapper;
import com.ch7x.service.PutwhService;
import org.springframework.stereotype.Service;

@Service
public class PutwhServiceImpl extends ServiceImpl<PutwhMapper, Putwh> implements PutwhService {
    @Override
    public IPage<Commodity> getPage(int currentPage, int pageSize, PutwhDto putwhDto) {
//        LambdaQueryWrapper<Commodity> lqw = new LambdaQueryWrapper<>();
//        lqw.like(Strings.isNotEmpty(putwhDto.getCName()) , PutwhDto::getCName, putwhDto.getCName());
//        IPage<Commodity> page = new Page<>(currentPage, pageSize);
//        commodityMapper.selectPage(page,lqw);
//        return page;
        return null;
    }
}
