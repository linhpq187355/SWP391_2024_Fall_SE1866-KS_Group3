/*
 * Copyright(C) 2024, HomeSharing Project.
 * H.SYS:
 *  Home Sharing System
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-10-20      1.0                 ManhNC         First Implement
 */
package com.homesharing.util;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A custom Gson {@link TypeAdapter} for serializing and deserializing {@link LocalDateTime} objects.
 * This adapter uses the format "yyyy-MM-dd'T'HH:mm:ss".
 */
public class LocalDateTimeUtil extends TypeAdapter<LocalDateTime> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    /**
     * Serializes a {@link LocalDateTime} object to JSON.
     *
     * @param jsonWriter The JSON writer.
     * @param localDateTime The {@link LocalDateTime} object to serialize, or null.
     * @throws IOException If an I/O error occurs during serialization.
     */
    @Override
    public void write(JsonWriter jsonWriter, LocalDateTime localDateTime) throws IOException {
        if (localDateTime == null) {
            jsonWriter.nullValue();
        } else {
            jsonWriter.value(localDateTime.format(formatter));
        }
    }

    /**
     * Deserializes a {@link LocalDateTime} object from JSON.
     *
     * @param jsonReader The JSON reader.
     * @return The deserialized {@link LocalDateTime} object, or null if the input is null.
     * @throws IOException If an I/O error occurs during deserialization.
     */
    @Override
    public LocalDateTime read(JsonReader jsonReader) throws IOException {
        String dateTimeStr = jsonReader.nextString();
        return dateTimeStr != null ? LocalDateTime.parse(dateTimeStr, formatter) : null;
    }
}
