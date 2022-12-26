package com.ch7x.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("putwh")
public class Putwh {
    @TableId(type = IdType.AUTO)
    @TableField("p_no")
    private Integer pNo;
    @TableField("c_no")
    private Integer cNo;
    @TableField("w_no")
    private Integer wNo;
    @TableField("number")
    private Integer pNumber;
    @TableField("p_date")
    private Date pDate;
    @TableField("deliveryman")
    private String deliveryman;
}
