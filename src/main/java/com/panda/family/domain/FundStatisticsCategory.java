package com.panda.family.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FundStatisticsCategory {
    private long id;
    private long familyId;
    private long categoryId;
    private int year;
    private int month;
    private double categoryBudget;
    private double categoryRealAmount;
    private int ctime;
    private int utime;
}
