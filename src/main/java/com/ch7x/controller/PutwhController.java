package com.ch7x.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ch7x.domain.Commodity;
import com.ch7x.domain.Putwh;
import com.ch7x.domain.Warehouse;
import com.ch7x.service.CommodityService;
import com.ch7x.service.PutwhService;
import com.ch7x.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

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
     * http://localhost:8080/putwh?pNumber=1&deliveryman="黄 章"
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
}
