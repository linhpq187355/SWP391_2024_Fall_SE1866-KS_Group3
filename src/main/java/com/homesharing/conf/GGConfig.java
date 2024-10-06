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
 * The {@code GGConfig} class contains configuration constants
 * for the Google OAuth 2.0 integration.
 * This class holds the following constants:
 * <ul>
 *     <li>GOOGLE_CLIENT_ID: The client ID obtained from Google Developer Console.</li>
 *     <li>GOOGLE_CLIENT_SECRET: The client secret obtained from Google Developer Console.</li>
 *     <li>GOOGLE_REDIRECT_URI: The URI to which Google redirects users after authentication.</li>
 *     <li>GOOGLE_GRANT_TYPE: The grant type for OAuth 2.0.</li>
 *     <li>GOOGLE_LINK_GET_TOKEN: The URL to get the access token from Google.</li>
 *     <li>GOOGLE_LINK_GET_USER_INFO: The URL to get user information using the access token.</li>
 * </ul>
 *
 * This class cannot be instantiated or subclassed.
 * All fields are public static final constants.
 */
public class GGConfig {
    /** The client ID obtained from Google Developer Console. */
    public static final String GOOGLE_CLIENT_ID = System.getenv("GOOGLE_CLIENT_ID");

    /** The client secret obtained from Google Developer Console. */
    public static final String GOOGLE_CLIENT_SECRET = System.getenv("GOOGLE_CLIENT_SECRET");

    /** The URI to which Google redirects users after authentication. */
    public static final String GOOGLE_REDIRECT_URI = "http://localhost:9999/homeSharing/sign-up-google";

    /** The grant type for OAuth 2.0. */
    public static final String GOOGLE_GRANT_TYPE = "authorization_code";

    /** The URL to get the access token from Google. */
    public static final String GOOGLE_LINK_GET_TOKEN = "https://accounts.google.com/o/oauth2/token";

    /** The URL to get user information using the access token. */
    public static final String GOOGLE_LINK_GET_USER_INFO = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=";

}
