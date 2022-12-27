package com.ch7x.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch7x.domain.Commodity;
import com.ch7x.domain.Putwh;
import com.ch7x.domain.Warehouse;
import com.ch7x.dto.PutwhDto;
import com.ch7x.service.CommodityService;
import com.ch7x.service.PutwhService;
import com.ch7x.service.WarehouseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
    public Page<PutwhDto> page(@PathVariable("currentPage") Integer page,
                               @PathVariable("pageSize") Integer pageSize, Commodity commodity,
                               @RequestParam("value") String value) {
//        System.out.println(commodity);
//        System.out.println(value);
//        System.out.println(value.length());

        Page<Putwh> pageInfo = new Page<>(page, pageSize);
        Page<PutwhDto> putwhDtoPage = new Page<>();

        LambdaQueryWrapper<Putwh> putLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (value.length() == 4) {
            String d1 = value + "-01-01";
            int temp = Integer.parseInt(value) + 1;
            String d2 = (temp) + "-01-01";
            System.out.println(d1 + d2);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date1, date2;
            try {
                date1 = sdf.parse(d1);
                date2 = sdf.parse(d2);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            putLambdaQueryWrapper.ge(date1 != null, Putwh::getPDate, date1);
            putLambdaQueryWrapper.le(date2 != null, Putwh::getPDate, date2);
        }

        if (value.length() == 7) {
            String d1 = value + "-01";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date1, date2;
            try {
                date1 = sdf.parse(d1);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date1);
            calendar.add(Calendar.MONTH, 1);
            date2 = calendar.getTime();
            putLambdaQueryWrapper.ge(Putwh::getPDate, date1);
            putLambdaQueryWrapper.le(Putwh::getPDate, date2);
        }

        if (value.length() == 10) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date1, date2;
            try {
                date1 = sdf.parse(value);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date1);
            calendar.add(Calendar.HOUR, 24);
            date2 = calendar.getTime();

            putLambdaQueryWrapper.ge(Putwh::getPDate, date1);
            putLambdaQueryWrapper.le(Putwh::getPDate, date2);
        }

        putwhService.page(pageInfo, putLambdaQueryWrapper);

        BeanUtils.copyProperties(pageInfo, putwhDtoPage, "records");
        List<Putwh> records = pageInfo.getRecords();
        List<PutwhDto> list = records.stream().map((item) -> {
            PutwhDto putwhDto = new PutwhDto();
            BeanUtils.copyProperties(item, putwhDto);

            Integer cNo = item.getCNo();
            //根据id查询商品对象
            Commodity tempCommodity = commodityService.getById(cNo);
            if (tempCommodity != null) {
                String cName = tempCommodity.getCName();
                String cManufacturer = tempCommodity.getCManufacturer();
                String cModel = tempCommodity.getCModel();
                String cSize = tempCommodity.getCSize();
                putwhDto.setCName(cName);
                putwhDto.setCManufacturer(cManufacturer);
                putwhDto.setCModel(cModel);
                putwhDto.setCSize(cSize);
            }
            return putwhDto;
        }).collect(Collectors.toList());

        putwhDtoPage.setRecords(list);

        //1,通过商品名字得到所有商品cno
        //select cno from commodity where cname like commodity.getCName()
        int cno = 0;
        //2，通过cno查询入库表
        LambdaQueryWrapper<PutwhDto> lqw = new LambdaQueryWrapper<>();
        lqw.eq(commodity.getCName() != null, PutwhDto::getCNo, cno);


        return putwhDtoPage;
    }
}
