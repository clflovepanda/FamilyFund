package com.panda.family.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Deduction {
    private long id;
    private long userId;
    private String itemName;
    private double amount;
    private int ctime;
    private int utime;
}
