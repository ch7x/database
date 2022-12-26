package com.ch7x.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("warehouse")
public class Warehouse {
    @TableId
    @TableField("w_no")
    private Integer wNo;
    @TableField("c_no")
    private Integer cNo;
    @TableField("number")
    private Integer cNumber;
}
