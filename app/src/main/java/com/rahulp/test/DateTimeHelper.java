package com.rahulp.test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeHelper {
    private final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSX");
    public static String getCurrentDay() {

        Date date = new Date();
        return simpleDateFormat.format(date);
    }
//
}