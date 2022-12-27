package com.ch7x.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ch7x.domain.Commodity;
import com.ch7x.domain.Putwh;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class PutwhDto extends Putwh {
    private String cName;
    private String cManufacturer;
    private String cModel;
    private String cSize;
}
