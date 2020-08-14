package com.erp.mastro.common;

import javax.servlet.http.HttpServletRequest;

public class UrlUtils {
    private UrlUtils() {
    }

    public static String getAppurl(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        return url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/";
    }

    public static String getFileExtension(String uri) {
        if (uri.contains(".")) {
            return uri.substring(uri.lastIndexOf("."));
        } else {
            return null;
        }
    }
}
