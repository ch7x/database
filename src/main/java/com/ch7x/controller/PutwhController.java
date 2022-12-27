package com.ch7x.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch7x.domain.Commodity;
import com.ch7x.domain.Putwh;
import com.ch7x.domain.Warehouse;
import com.ch7x.dto.CommodityDto;
import com.ch7x.dto.PutwhDto;
import com.ch7x.service.CommodityService;
import com.ch7x.service.PutwhService;
import com.ch7x.service.WarehouseService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/put")
public class PutwhController {
    @Autowired
    private CommodityService commodityService;
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private PutwhService putwhService;

    /**
     * 1,商品添加
     * 2,入库操作
     * 3,记录入库日志
     * http://localhost:8080/put?pNumber=1&deliveryman="黄 章"
     */
    @PostMapping()
    public boolean insert(@RequestBody Commodity commodity,
                          @RequestParam Integer pNumber,                //入库商品数量
                          @RequestParam String deliveryman) {           //入库人

        //入库日志
        Putwh putwh = new Putwh();
        putwh.setDeliveryman(deliveryman);
        putwh.setPDate(new Date());
        putwh.setPNumber(pNumber);

        //查询商品表中有没有重名的
        LambdaQueryWrapper<Commodity> lqw1 = new LambdaQueryWrapper<>();
        lqw1.eq(Commodity::getCName, commodity.getCName());
        List<Commodity> list1 = commodityService.list(lqw1);

        Integer cNo = -1;
        boolean flag = false;
        for (Commodity commodity1 : list1) {
            flag = commodity1.equals(commodity);
            if (flag) {
                cNo = commodity1.getCNo();
                break;
            }
        }

        if (commodityService.count(lqw1) != 0 && flag) {
            //如果仓库中有该商品，则添加数量
            LambdaQueryWrapper<Warehouse> lqw2 = new LambdaQueryWrapper<>();
            lqw2.eq(Warehouse::getCNo, cNo);

            //因为cno是唯一的所以这个list里只会有一个参数
            List<Warehouse> list = warehouseService.list(lqw2);
            Warehouse warehouse = list.get(0);
            warehouse.setCNumber(warehouse.getCNumber() + pNumber);
            warehouseService.updateById(warehouse);

            //记录入库日志
            putwh.setWNo(warehouse.getWNo());
            putwh.setCNo(cNo);
        } else {

            //如果仓库中没有该商品，则添加进商品表和仓库表
            commodityService.save(commodity);

            Warehouse warehouse = new Warehouse();
            warehouse.setCNumber(pNumber);
            warehouse.setCNo(commodity.getCNo());
            warehouseService.save(warehouse);

            //记录入库日志
            putwh.setWNo(warehouse.getWNo());
            putwh.setCNo(commodity.getCNo());
        }
        return putwhService.save(putwh);
    }

    /**
     * 入库的分页查询
     */
    @GetMapping("/{currentPage}/{pageSize}")
    public Page<PutwhDto> page(@PathVariable("currentPage") Integer page, @PathVariable("pageSize") Integer pageSize, Commodity commodity, @RequestParam("value") String value) {
        System.out.println(commodity);
        System.out.println(value);
        Page<Putwh> pageInfo = new Page<>(page, pageSize);
        Page<PutwhDto> commodityDtoPage = new Page<>();

//        LambdaQueryWrapper<Putwh> lqw = new LambdaQueryWrapper<>();
//        lqw.like(commodity.getCName() != null, Commodity::getCName, commodity.getCName());
//        lqw.like(commodity.getCManufacturer() != null, Commodity::getCManufacturer, commodity.getCManufacturer());
//
//        Page<Commodity> page1 = commodityService.page(pageInfo, lqw);
//
//        BeanUtils.copyProperties(pageInfo, commodityDtoPage, "records");
//        List<Commodity> records = pageInfo.getRecords();
//        List<CommodityDto> list = records.stream().map((item) -> {
//            CommodityDto commodityDto = new CommodityDto();
//            BeanUtils.copyProperties(item, commodityDto);
//
//            Integer cNo = item.getCNo();
//            //根据id查询仓库对象
//            Warehouse warehouse = warehouseService.getById(cNo);
//            if (warehouse != null){
//                Integer cNumber = warehouse.getCNumber();
//                commodityDto.setNumber(cNumber);
//            }
//            return commodityDto;
//        }).collect(Collectors.toList());
//
//        commodityDtoPage.setRecords(list);

        return null;
    }
}
