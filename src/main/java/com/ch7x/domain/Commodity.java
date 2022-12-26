package com.ch7x.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("commodity")   //数据库中表名
public class Commodity {
    @TableField("c_no")   //指定数据表中的字段名
    @TableId
    private Integer cNo;
    @TableField("c_name")
    private String cName;
    @TableField("c_manufacturer")
    private String cManufacturer;
    @TableField("c_model")
    private String cModel;
    @TableField("c_size")
    private String cSize;
}
