package com.homesharing.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Utility class for handling cookie operations.
 */
public class CookieUtil {

    private static final Logger logger = LoggerFactory.getLogger(CookieUtil.class); // Logger instance

    private CookieUtil() {}

    /**
     * Adds a cookie to the response with the specified name, value, and age.
     *
     * @param response  The HttpServletResponse to add the cookie to.
     * @param name      The name of the cookie.
     * @param value     The value of the cookie.
     * @param cookieAge The age of the cookie in seconds.
     */
    public static void addCookie(HttpServletResponse response, String name, String value,int cookieAge) {
        try {
            String encodedValue = URLEncoder.encode(value, StandardCharsets.UTF_8);
            Cookie cookie = new Cookie(name, encodedValue);
            cookie.setPath("/");
            cookie.setMaxAge(cookieAge);
            response.addCookie(cookie);
        } catch (Exception e) {
            logger.error("Error for add cookie: {}", e.getMessage(), e);
        }
    }

    /**
     * Retrieves the value of a cookie with the specified name from the request.
     *
     * @param request The HttpServletRequest to retrieve the cookie from.
     * @param name    The name of the cookie to retrieve.
     * @return The value of the cookie, or null if not found.
     */
    public static String getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    /**
     * Removes a cookie with the specified name from the response.
     *
     * @param response The HttpServletResponse to remove the cookie from.
     * @param name     The name of the cookie to remove.
     */
    public static void removeCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
