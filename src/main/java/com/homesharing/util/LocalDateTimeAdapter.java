/*
 * Copyright(C) 2024, Homesharing Inc.
 * Homesharing:
 *  Roommate Matching and Home Sharing Service
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-10-25      1.0              Pham Quang Linh     First Implement
 */
package com.homesharing.util;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * LocalDateTimeAdapter is a custom Gson {@link TypeAdapter} for serializing and
 * deserializing {@link LocalDateTime} objects to and from JSON format.
 * This adapter uses the ISO_LOCAL_DATE_TIME format for consistency with standard date-time representations.
 */
public class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    /**
     * Serializes a {@link LocalDateTime} object to JSON format.
     *
     * @param out   The {@link JsonWriter} to write the date-time value to.
     * @param value The {@link LocalDateTime} value to be serialized. Can be null.
     * @throws IOException If an I/O error occurs during writing.
     */
    @Override
    public void write(JsonWriter out, LocalDateTime value) throws IOException {
        if (value != null) {
            out.value(value.format(formatter));
        } else {
            out.nullValue();
        }
    }

    /**
     * Deserializes a JSON date-time string to a {@link LocalDateTime} object.
     *
     * @param in The {@link JsonReader} to read the date-time value from.
     * @return A {@link LocalDateTime} object parsed from the JSON string, or null if no date-time is present.
     * @throws IOException If an I/O error occurs during reading.
     */
    @Override
    public LocalDateTime read(JsonReader in) throws IOException {
        return in.hasNext() ? LocalDateTime.parse(in.nextString(), formatter) : null;
    }
}
