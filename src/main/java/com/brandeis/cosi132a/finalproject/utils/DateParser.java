package com.brandeis.cosi132a.finalproject.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateParser {
    public static String parseDate(String dateFrom) throws ParseException {
        if (dateFrom != null && dateFrom.length() > 0) {
            DateFormat origFormat = new SimpleDateFormat("MMM-dd-yyyy-");
            DateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");

            if (dateFrom.toCharArray()[0] >= '0' && dateFrom.toCharArray()[0] <= '9') {
                dateFrom = dateFrom.split("T")[0];
            } else {
                String formatDateFrom = "";
                for (int i = 1; i <= 3; i++) {
                    formatDateFrom += dateFrom.split(" ")[i] + "-";
                }
                Date dateFromDate = origFormat.parse(formatDateFrom);
                dateFrom = newFormat.format(dateFromDate);
            }
        }
        return dateFrom;
    }
}
