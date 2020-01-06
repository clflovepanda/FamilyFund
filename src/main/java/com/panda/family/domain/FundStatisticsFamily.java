package com.panda.family.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FundStatisticsFamily {
    private long id;
    private long familyId;
    private int year;
    private int month;
    private double familyBudget;
    private double familyRealAmount;
    private int ctime;
    private int utime;
}
