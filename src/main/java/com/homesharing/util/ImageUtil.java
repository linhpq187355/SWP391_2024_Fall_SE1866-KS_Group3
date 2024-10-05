package com.homesharing.util;

import java.util.UUID;

public class ImageUtil {
    public static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0) {
            return fileName.substring(dotIndex);
        } else {
            return "";
        }
    }

    // Helper method to get a unique file name based on the upload time
    public static String getUniqueFileName(String originalFileName) {
        return UUID.randomUUID().toString() + "_" + originalFileName;
    }
}
