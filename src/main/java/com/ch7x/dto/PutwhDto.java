package com.ch7x.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ch7x.domain.Putwh;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class PutwhDto extends Putwh {
    private String cName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @TableField(exist = false)
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @TableField(exist = false)
    private Date endTime;


}
