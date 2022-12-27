package com.ch7x.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch7x.domain.Commodity;
import com.ch7x.dto.CommodityDto;
import com.ch7x.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品管理
 */
@RestController
@RequestMapping("/commodity")
public class CommodityController {
    @Autowired
    private CommodityService commodityService;

    /**
     * 商品查找
     */
//    @GetMapping("/{currentPage}/{pageSize}")
//    public IPage<Commodity> getByCno(@PathVariable int currentPage, @PathVariable int pageSize, CommodityDto commodity) {
//
//        return commodityService.getPage(currentPage,pageSize,commodity);
//    }


    @GetMapping("/{currentPage}/{pageSize}")
    public IPage<CommodityDto> pageXml(@PathVariable("currentPage")Integer corund, @PathVariable("pageSize")Integer limit){
        Page<CommodityDto> page = new Page<>(corund,limit);

        return commodityService.findPage(page, new QueryWrapper<>());
    }


    /**
     * 商品修改
     */
    @PutMapping()
    public boolean update(@RequestBody Commodity commodity) {
        return commodityService.updateById(commodity);
    }

    /**
     * 商品删除
     * http://localhost:8080/commodity?cnos=1,2,3
     */
    @DeleteMapping()
    public boolean deleteByCno(@RequestParam List<String> cnos) {
        LambdaQueryWrapper<Commodity> lqw1 = new LambdaQueryWrapper<>();
        lqw1.in(Commodity::getCNo, cnos);
        return commodityService.remove(lqw1);
    }
}
