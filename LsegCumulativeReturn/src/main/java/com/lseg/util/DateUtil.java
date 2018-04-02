package com.lseg.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil
{   
    // given the string date and date format, this method converts string to appropriate date and format
    public static Date toDate(String date, String dateFormat)
    {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }
}
