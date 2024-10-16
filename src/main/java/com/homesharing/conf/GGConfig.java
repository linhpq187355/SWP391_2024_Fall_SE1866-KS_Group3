/*
 * Copyright(C) 2024, HomeSharing Project.
 * H.SYS:
 *  Home Sharing System
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-9-18      1.0                 ManhNC         First Implement
 */
package com.homesharing.conf;

/**
 * The {@code GGConfig} class contains configuration constants for Google OAuth 2.0 integration.
 * These constants are used to interact with the Google API for user authentication and authorization.
 * <p>
 * This class cannot be instantiated or subclassed. All fields are {@code public static final} constants.
 * <p>
 * Bugs: None known.
 *
 * @author ManhNC
 */
public final class GGConfig {

    /**
     * The client ID obtained from the Google Developer Console.  This ID uniquely identifies your application
     * to Google's OAuth 2.0 service.  This value is retrieved from the `GOOGLE_CLIENT_ID` environment variable.
     */
    public static final String GOOGLE_CLIENT_ID = System.getenv("GOOGLE_CLIENT_ID");

    /**
     * The client secret obtained from the Google Developer Console. This secret is used to authenticate your
     * application to Google's OAuth 2.0 service.  This value is retrieved from the `GOOGLE_CLIENT_SECRET`
     * environment variable.
     */
    public static final String GOOGLE_CLIENT_SECRET = System.getenv("GOOGLE_CLIENT_SECRET");

    /**
     * The redirect URI to which Google redirects users after authentication.  This URI must be registered
     * in the Google Developer Console and must match the redirect URI configured for your application.
     */
    public static final String GOOGLE_REDIRECT_URI = "http://localhost:9999/homeSharing/sign-up-google";

    /**
     * The grant type for OAuth 2.0. This value is typically "authorization_code" for web applications.
     */
    public static final String GOOGLE_GRANT_TYPE = "authorization_code";

    /**
     * The URL to get the access token from Google.  This URL is used to exchange an authorization code
     * for an access token, which can then be used to access Google APIs.
     */
    public static final String GOOGLE_LINK_GET_TOKEN = "https://accounts.google.com/o/oauth2/token";

    /**
     * The base URL to get user information using the access token.  An access token must be appended to this
     * URL to retrieve user information. Example: `GOOGLE_LINK_GET_USER_INFO + accessToken`.
     */
    public static final String GOOGLE_LINK_GET_USER_INFO = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=";

}
