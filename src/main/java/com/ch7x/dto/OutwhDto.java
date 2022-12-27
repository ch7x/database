package com.ch7x.dto;

import com.ch7x.domain.Outwh;
import lombok.Data;

@Data
public class OutwhDto extends Outwh {
    private String cName;
    private String cManufacturer;
    private String cModel;
    private String cSize;
}
