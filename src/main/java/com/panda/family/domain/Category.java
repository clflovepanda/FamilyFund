package com.panda.family.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Category {
    private long id;
    private String categoryName;
    private long familyId;
    private int categoryLevel;
    private long parentId;
    private double budget;
    private String ruleDesc;
    private int ctime;
    private int utime;
}
