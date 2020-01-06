package com.panda.family.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Item {
    private long id;
    private long familyId;
    private long userId;
    private long categoryId;
    private double amount;
    private int ctime;
    private int utime;
}
