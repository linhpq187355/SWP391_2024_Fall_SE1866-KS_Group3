package com.homesharing.util;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.auth.oauth2.GoogleCredentials;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Logger;

public class FirebaseUtil {

    private static final Logger logger = Logger.getLogger(FirebaseUtil.class.getName());


    private static boolean initialized = false;

    // Phương thức khởi tạo Firebase
    public static void initializeFirebase() {
        if (!initialized) {
            try {
                FileInputStream serviceAccount =
                        new FileInputStream("E:\\IntelliJ\\SWP391_2024_Fall_SE1866-KS_Group3\\src\\main\\resources\\firebase-admin.json");

                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .setDatabaseUrl("https://home-sharing-3c037-default-rtdb.asia-southeast1.firebasedatabase.app/")
                        .build();

                FirebaseApp.initializeApp(options);
                initialized = true; // Đánh dấu đã khởi tạo
                System.out.println("Firebase initialized successfully.");
            } catch (IOException e) {
                logger.severe("Firebase initialization failed.");
            }
        } else {
            logger.info("Firebase is already initialized.");
        }
    }
}
