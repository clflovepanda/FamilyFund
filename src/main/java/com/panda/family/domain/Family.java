package com.panda.family.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Family {
    private long id;
    private String familyName;
    private long creator;
    private String member;
    private int ctime;
    private int utime;
}
