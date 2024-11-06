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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * LocalDateAdapter is a custom Gson {@link TypeAdapter} for serializing and
 * deserializing {@link LocalDate} objects to and from JSON format.
 * This adapter uses the ISO_LOCAL_DATE format for consistency with standard date representations.
 */
public class LocalDateAdapter extends TypeAdapter<LocalDate> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

    /**
     * Serializes a {@link LocalDate} object to JSON format.
     *
     * @param out   The {@link JsonWriter} to write the date value to.
     * @param value The {@link LocalDate} value to be serialized. Can be null.
     * @throws IOException If an I/O error occurs during writing.
     */
    @Override
    public void write(JsonWriter out, LocalDate value) throws IOException {
        out.value(value != null ? value.format(formatter) : null);
    }

    /**
     * Deserializes a JSON date string to a {@link LocalDate} object.
     *
     * @param in The {@link JsonReader} to read the date value from.
     * @return A {@link LocalDate} object parsed from the JSON string, or null if no date is present.
     * @throws IOException If an I/O error occurs during reading.
     */
    @Override
    public LocalDate read(JsonReader in) throws IOException {
        return in.hasNext() ? LocalDate.parse(in.nextString(), formatter) : null;
    }
}
