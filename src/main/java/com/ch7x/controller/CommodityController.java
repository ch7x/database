package com.ch7x.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ch7x.domain.Commodity;
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
     * 商品添加
     * http://localhost:8080/commodity
     */
    @PostMapping()
    public boolean insert(@RequestBody Commodity commodity) {
        //首先判断是否已经有该商品
        LambdaQueryWrapper<Commodity> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Commodity::getCName,commodity.getCName());
        if (commodityService.count(lqw)!=0){
            return false;
        }
        return commodityService.save(commodity);
    }

    /**
     * 商品查找
     */
    @GetMapping()
    public List<Commodity> getByCno(@RequestParam List<String> cnos){
        LambdaQueryWrapper<Commodity> lqw = new LambdaQueryWrapper<>();
        lqw.in(Commodity::getCNo, cnos);
        return commodityService.list(lqw);
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
