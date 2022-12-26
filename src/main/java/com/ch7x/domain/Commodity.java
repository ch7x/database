package com.ch7x.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Objects;

@Data
@TableName("commodity")   //数据库中表名
public class Commodity {
    @TableId(type = IdType.AUTO)
    @TableField("c_no")   //指定数据表中的字段名
    private Integer cNo;
    @TableField("c_name")
    private String cName;
    @TableField("c_manufacturer")
    private String cManufacturer;
    @TableField("c_model")
    private String cModel;
    @TableField("c_size")
    private String cSize;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Commodity commodity = (Commodity) o;
        return Objects.equals(cName, commodity.cName) && Objects.equals(cManufacturer, commodity.cManufacturer) && Objects.equals(cModel, commodity.cModel) && Objects.equals(cSize, commodity.cSize);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cNo, cName, cManufacturer, cModel, cSize);
    }
}
