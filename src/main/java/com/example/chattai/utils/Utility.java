package com.example.chattai.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;

public class Utility {
    public static String getSiteURL(HttpServletRequest request){
        String fullURL = request.getRequestURL().toString();
        return fullURL.substring(0, StringUtils.ordinalIndexOf(fullURL, "/", 5));
    }
}

