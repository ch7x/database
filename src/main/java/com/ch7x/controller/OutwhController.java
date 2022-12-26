package com.ch7x.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ch7x.domain.Commodity;
import com.ch7x.domain.Outwh;
import com.ch7x.domain.Warehouse;
import com.ch7x.service.CommodityService;
import com.ch7x.service.OutwhService;
import com.ch7x.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/out")
public class OutwhController {

    @Autowired
    private CommodityService commodityService;
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private OutwhService outwhService;

    /**
     * 1,出库操作
     * 2,记录出库日志
     */
    @PutMapping()
    public boolean modify(@RequestBody Commodity commodity,             //出库商品
                          @RequestParam Integer oNumber,                //出库商品数量
                          @RequestParam String purchaser) {             //出库人
        //出库日志
        Outwh outwh = new Outwh();
        outwh.setPurchaser(purchaser);
        outwh.setODate(new Date());
        outwh.setONumber(oNumber);

        //查询商品表中有没有重名的
        LambdaQueryWrapper<Commodity> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Commodity::getCName, commodity.getCName());
        List<Commodity> list1 = commodityService.list(lqw);

        Integer cNo = -1;
        boolean flag;
        for (Commodity commodity1 : list1) {
            flag = commodity1.equals(commodity);
            if (flag) {
                cNo = commodity1.getCNo();
                break;
            }
        }
        System.out.println(commodityService.count(lqw));
        //如果没有,结束
        if (commodityService.count(lqw) == 0) {
            System.out.println("仓库中没有这件商品");
            return false;
        }

        //如果有,得到此商品在仓库的数据
        LambdaQueryWrapper<Warehouse> lqw2 = new LambdaQueryWrapper<>();
        lqw2.eq(Warehouse::getCNo, cNo);

        //因为cno是唯一的所以这个list里只会有一个参数
        List<Warehouse> list = warehouseService.list(lqw2);
        Warehouse warehouse = list.get(0);

        //如果数量不足,结束
        if (oNumber > warehouse.getCNumber()) {
            System.out.println("仓库中这件商品数量不足");
            return false;
        }

        //如果数量充足,则出库
        warehouse.setCNumber(warehouse.getCNumber() - oNumber);
        warehouseService.updateById(warehouse);

        //记录出库日志
        outwh.setWNo(warehouse.getWNo());
        outwh.setCNo(warehouse.getCNo());
        return outwhService.save(outwh);
    }

}
