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
     * http://localhost:8080/commodity/save
     */
    @PostMapping("/save")
    public String insert(@RequestBody Commodity commodity) {
        commodityService.save(commodity);
        return "添加成功";
    }

    /**
     * 商品查找
     */
    @GetMapping()
    public List<Commodity> getByCno(@RequestParam List<String> cnos){
        LambdaQueryWrapper<Commodity> lqw = new LambdaQueryWrapper<>();
        lqw.in(Commodity::getCno, cnos);
        return commodityService.list(lqw);
    }

    /**
     * 商品修改
     */
    @PutMapping()
    public boolean update(@RequestBody Commodity commodity) {
//        return commodityService.modify(commodity);
        return false;
    }

    /**
     * 商品删除
     * http://localhost:8080/commodity?cnos=1,2,3
     */
    @DeleteMapping()
    public boolean deleteByCno(@RequestParam List<String> cnos) {
        LambdaQueryWrapper<Commodity> lqw1 = new LambdaQueryWrapper<>();
        lqw1.in(Commodity::getCno, cnos);
        return commodityService.remove(lqw1);
    }
}
