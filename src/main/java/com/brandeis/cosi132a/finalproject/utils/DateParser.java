package com.brandeis.cosi132a.finalproject.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateParser {
    public static String parseDate(String date) throws ParseException {
        if (date != null && date.length() > 0) {
            DateFormat origFormat = new SimpleDateFormat("MMM-dd-yyyy-");
            DateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");

            if (date.toCharArray()[0] >= '0' && date.toCharArray()[0] <= '9') {
                date = date.split("T")[0];
            } else {
                String formatDate = "";
                for (int i = 1; i <= 3; i++) {
                    formatDate += date.split(" ")[i] + "-";
                }
                Date dateFromDate = origFormat.parse(formatDate);
                date = newFormat.format(dateFromDate);
            }
        }
        return date;
    }
}
