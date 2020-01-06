package com.panda.family.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FundStatisticsCategoryUser {
    private long id;
    private long familyId;
    private long categoryId;
    private long userId;
    private int year;
    private int month;
    private double contribution;
    private double use;
    private int ctime;
    private int utime;
}
