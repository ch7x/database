package com.ch7x.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Outwh {
    private String ono;
    private String cno;
    private String wno;
    private int number;
    private Date odate;
    private String purchaser;
}
