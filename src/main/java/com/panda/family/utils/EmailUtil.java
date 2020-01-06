package com.panda.family.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailUtil {

    public static boolean checkEmailFormat(String email) {
        String checkReg = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(checkReg);
        Matcher matcher = regex.matcher(email);
        return matcher.matches();
    }

}
