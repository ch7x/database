package com.ch7x.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch7x.domain.Commodity;
import com.ch7x.domain.Warehouse;
import com.ch7x.dto.CommodityDto;
import com.ch7x.service.CommodityService;
import com.ch7x.service.WarehouseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品管理
 */
@RestController
@RequestMapping("/commodity")
public class CommodityController {
    @Autowired
    private CommodityService commodityService;

    @Autowired
    private WarehouseService warehouseService;

    /**
     * 商品查找
     */
    @GetMapping("/{currentPage}/{pageSize}")
    public Page<CommodityDto> page(@PathVariable("currentPage") Integer page, @PathVariable("pageSize") Integer pageSize, Commodity commodity) {
        Page<Commodity> pageInfo = new Page<>(page, pageSize);
        Page<CommodityDto> commodityDtoPage = new Page<>();

        LambdaQueryWrapper<Commodity> lqw = new LambdaQueryWrapper<>();
        lqw.like(commodity.getCName() != null, Commodity::getCName, commodity.getCName());
        lqw.like(commodity.getCManufacturer() != null, Commodity::getCManufacturer, commodity.getCManufacturer());

        commodityService.page(pageInfo, lqw);

        BeanUtils.copyProperties(pageInfo, commodityDtoPage, "records");
        List<Commodity> records = pageInfo.getRecords();
        List<CommodityDto> list = records.stream().map((item) -> {
            CommodityDto commodityDto = new CommodityDto();
            BeanUtils.copyProperties(item, commodityDto);

            Integer cNo = item.getCNo();
            //根据id查询仓库对象
            Warehouse warehouse = warehouseService.getById(cNo);
            if (warehouse != null){
                Integer cNumber = warehouse.getCNumber();
                commodityDto.setNumber(cNumber);
            }
            return commodityDto;
        }).collect(Collectors.toList());

        commodityDtoPage.setRecords(list);

        return commodityDtoPage;
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
