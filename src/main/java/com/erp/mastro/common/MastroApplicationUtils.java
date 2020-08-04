package com.erp.mastro.common;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Class that included all common Util methods
 */
public class MastroApplicationUtils {

    /**
     * Returns converted Date String
     *
     * @param dateString String from the view
     * @param date       Date from Entity
     * @return String format of Date
     */
    public static String getStringFromDate(String dateString, Date date) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        if (dateString != null) {
            return dateString;
        }
        if (date == null) {
            return "";
        } else {
            return df.format(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        }
    }

}
