package com.ch7x.dto;

import com.ch7x.domain.Putwh;
import lombok.Data;


@Data
public class PutwhDto extends Putwh {
    private String cName;
    private String cManufacturer;
    private String cModel;
    private String cSize;
}
