package com.erp.mastro.common;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Class that included all common Util methods
 */
public class MastroApplicationUtils {
    private final GrantedAuthority adminAuthority = new SimpleGrantedAuthority(
            "ROLE_ADMIN");

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

    public static Timestamp converttoTimestamp(LocalDateTime date) {
        Timestamp timestamp = Timestamp.valueOf(date);
        return timestamp;
    }

    public boolean isAdminAuthority(final Authentication authentication) {
        return CollectionUtils.isNotEmpty(authentication.getAuthorities())
                && authentication.getAuthorities().contains(adminAuthority);
        /* || authentication.getAuthorities().contains(Constants.ROLE_SUPERADMIN)*/
    }

    public static Double roundTwoDecimals(Double amount) {
        DecimalFormat twoDForm = new DecimalFormat("#.####");
        return Double.valueOf(twoDForm.format(amount));
    }
}
