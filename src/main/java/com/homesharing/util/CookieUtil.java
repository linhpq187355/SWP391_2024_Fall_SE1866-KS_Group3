package com.homesharing.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Utility class for handling cookies in HTTP responses and requests.
 * Provides methods to add, retrieve, and remove cookies.
 */
public class CookieUtil {

    private static final Logger logger = LoggerFactory.getLogger(CookieUtil.class); // Logger instance

    // Private constructor to prevent instantiation
    private CookieUtil() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Adds a cookie to the HTTP response.
     *
     * @param response  The HttpServletResponse object to which the cookie will be added.
     * @param name      The name of the cookie.
     * @param value     The value of the cookie.
     * @param cookieAge The maximum age of the cookie in seconds.
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
     * Retrieves the value of a specified cookie from the HTTP request.
     *
     * @param request The HttpServletRequest object from which to retrieve the cookie.
     * @param name    The name of the cookie to retrieve.
     * @return The value of the cookie, or null if the cookie does not exist.
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
     * Removes a cookie from the HTTP response by setting its maximum age to zero.
     *
     * @param response The HttpServletResponse object from which to remove the cookie.
     * @param name     The name of the cookie to remove.
     */
    public static void removeCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
