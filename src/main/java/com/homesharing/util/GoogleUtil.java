/*
 * Copyright(C) 2024, HomeSharing Project.
 * H.SYS:
 *  Home Sharing System
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-9-18      1.0                 ManhNC         First Implement
 */
package com.homesharing.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.homesharing.conf.GGConfig;
import com.homesharing.model.GoogleAccount;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * GoogleUtil provides utility methods for interacting with Google services,
 * including obtaining access tokens and user information using the Google API.
 * @author ManhNC
 */
public class GoogleUtil {
    private static final Logger logger = LoggerFactory.getLogger(CookieUtil.class); // Logger instance

    private GoogleUtil() {}

    private static final OkHttpClient client = new OkHttpClient();

    /**
     * Retrieves the access token from Google using the provided authorization code.
     *
     * @param code The authorization code received from Google.
     * @return The access token as a String, or null if an error occurs.
     * @throws IOException If an input or output exception occurs.
     */
    public static String getToken(String code) throws IOException {
        // Build the POST request body
        RequestBody formBody = new FormBody.Builder()
                .add("client_id", GGConfig.GOOGLE_CLIENT_ID)
                .add("client_secret", GGConfig.GOOGLE_CLIENT_SECRET)
                .add("redirect_uri", GGConfig.GOOGLE_REDIRECT_URI)
                .add("code", code)
                .add("grant_type", GGConfig.GOOGLE_GRANT_TYPE)
                .build();
        Request request = new Request.Builder()
                .url(GGConfig.GOOGLE_LINK_GET_TOKEN)
                .post(formBody)
                .build();

        // Send the request and receive the response
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            String responseBody = response.body().string();
            logger.info("Response: " + responseBody);

            // Parse the JSON response
            JsonObject jobs = new Gson().fromJson(responseBody, JsonObject.class);
            return jobs.get("access_token").getAsString();
        } catch (JsonParseException e) {
            logger.error("Error parsing JSON: " + e.getMessage());
            return null;
        }
    }

    /**
     * Retrieves user information from Google using the provided access token.
     *
     * @param accessToken The access token for authorization.
     * @return A GoogleAccount object containing user information, or null if an error occurs.
     * @throws IOException If an input or output exception occurs.
     */
    public static GoogleAccount getUserInfo(final String accessToken) throws IOException {
        // Create a GET request with the Authorization header
        Request request = new Request.Builder()
                .url(GGConfig.GOOGLE_LINK_GET_USER_INFO)
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();

        // Send the request and receive the response
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseBody = response.body().string();

                // Log the response for verification
                logger.info("Response: " + responseBody);

                return new Gson().fromJson(responseBody, GoogleAccount.class);
            } else {
                logger.error("GET request failed: " + response.code());
                return null;
            }
        }
    }
}
