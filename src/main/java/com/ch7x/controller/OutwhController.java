package com.ch7x.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch7x.domain.Commodity;
import com.ch7x.domain.Outwh;
import com.ch7x.domain.Warehouse;
import com.ch7x.dto.OutwhDto;
import com.ch7x.dto.PutwhDto;
import com.ch7x.service.CommodityService;
import com.ch7x.service.OutwhService;
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
    @PostMapping()
    public boolean modify(@RequestBody OutwhDto outwhDto) {
        System.out.println(outwhDto);
        //出库人
        //出库日志
        Outwh outwh = new Outwh();
        outwh.setPurchaser(outwhDto.getPurchaser());
        outwh.setODate(new Date());
        outwh.setONumber(outwhDto.getONumber());


        //如果有,得到此商品在仓库的数据
        LambdaQueryWrapper<Warehouse> lqw2 = new LambdaQueryWrapper<>();
        lqw2.eq(Warehouse::getCNo, outwhDto.getCNo());

        //因为cno是唯一的所以这个list里只会有一个参数
        List<Warehouse> list = warehouseService.list(lqw2);
        Warehouse warehouse = list.get(0);

        //如果数量不足,结束
        if (outwhDto.getONumber() > warehouse.getCNumber()) {
            System.out.println("仓库中这件商品数量不足");
            return false;
        }

        //如果数量充足,则出库
        warehouse.setCNumber(warehouse.getCNumber() - outwhDto.getONumber());
        warehouseService.updateById(warehouse);

        //记录出库日志
        outwh.setWNo(warehouse.getWNo());
        outwh.setCNo(warehouse.getCNo());
        return outwhService.save(outwh);
    }

    /**
     * 出库的分页查询
     */
    @GetMapping("/{currentPage}/{pageSize}")
    public Page<OutwhDto> page(@PathVariable("currentPage") Integer page,
                               @PathVariable("pageSize") Integer pageSize, Commodity commodity,
                               @RequestParam("value") String value) {

        Page<Outwh> pageInfo = new Page<>(page, pageSize);
        Page<OutwhDto> outwhDtoPage = new Page<>();

        LambdaQueryWrapper<Outwh> outLambdaQueryWrapper = new LambdaQueryWrapper<>();
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
            outLambdaQueryWrapper.ge(date1 != null, Outwh::getODate, date1);
            outLambdaQueryWrapper.le(date2 != null, Outwh::getODate, date2);
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
            outLambdaQueryWrapper.ge(Outwh::getODate, date1);
            outLambdaQueryWrapper.le(Outwh::getODate, date2);
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

            outLambdaQueryWrapper.ge(Outwh::getODate, date1);
            outLambdaQueryWrapper.le(Outwh::getODate, date2);
        }

        outwhService.page(pageInfo, outLambdaQueryWrapper);

        BeanUtils.copyProperties(pageInfo, outwhDtoPage, "records");
        List<Outwh> records = pageInfo.getRecords();
        List<OutwhDto> list = records.stream().map((item) -> {
            OutwhDto outwhDto = new OutwhDto();
            BeanUtils.copyProperties(item, outwhDto);

            Integer cNo = item.getCNo();
            //根据id查询商品对象
            Commodity tempCommodity = commodityService.getById(cNo);
            if (tempCommodity != null) {
                String cName = tempCommodity.getCName();
                String cManufacturer = tempCommodity.getCManufacturer();
                String cModel = tempCommodity.getCModel();
                String cSize = tempCommodity.getCSize();
                outwhDto.setCName(cName);
                outwhDto.setCManufacturer(cManufacturer);
                outwhDto.setCModel(cModel);
                outwhDto.setCSize(cSize);
            }
            return outwhDto;
        }).collect(Collectors.toList());

        outwhDtoPage.setRecords(list);

        //1,通过商品名字得到所有商品cno
        //select cno from commodity where cname like commodity.getCName()
        int cno = 0;
        //2，通过cno查询入库表
        LambdaQueryWrapper<PutwhDto> lqw = new LambdaQueryWrapper<>();
        lqw.eq(commodity.getCName() != null, PutwhDto::getCNo, cno);

        return outwhDtoPage;
    }
}
