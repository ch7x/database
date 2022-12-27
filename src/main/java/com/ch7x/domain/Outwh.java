package com.ch7x.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
@TableName("outwh")
public class Outwh {
    @TableId(type = IdType.AUTO)
    private Integer oNo;
    @TableField("c_no")
    private Integer cNo;
    @TableField("w_no")
    private Integer wNo;
    @TableField("number")
    private Integer oNumber;
    @TableField("o_date")
    @JsonFormat(pattern = "yyyy-MM-dd" , timezone = "GMT+8")
    private Date oDate;
    @TableField("purchaser")
    private String purchaser;
}
