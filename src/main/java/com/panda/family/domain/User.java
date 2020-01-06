package com.panda.family.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User {
    private long id;
    private String userName;
    private String nickname;
    private String password;
    private double income;
    private double realIncome;
    private String email;
    private int status;
    private int code;
    private int codeTime;
    private int ctime;
    private int utime;
}
