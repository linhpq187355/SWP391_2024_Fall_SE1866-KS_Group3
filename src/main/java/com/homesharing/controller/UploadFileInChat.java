package com.homesharing.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/upload-file-chat")  // Đường dẫn bạn sẽ dùng trong hàm fetch
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class UploadFileInChat extends HttpServlet {
    // Đường dẫn tuyệt đối để lưu file
    private static final String UPLOAD_DIR = "E:\\IntelliJ\\SWP391_2024_Fall_SE1866-KS_Group3\\src\\main\\webapp\\assets\\uploads";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Tạo thư mục lưu file nếu chưa tồn tại
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        List<Map<String, String>> fileDetails = new ArrayList<>();

            // Lặp qua tất cả các phần của request để lấy file
            for (Part part : request.getParts()) {
                if (part.getName().equals("files")) {  // Kiểm tra part có tên "files"
                    String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString(); // Lấy tên file

                    // Lấy kiểu MIME của file
                    String mimeType = part.getContentType();

                    // Xác định loại file và lưu thông tin
                    Map<String, String> fileInfo = new HashMap<>();
                    String filePath = UPLOAD_DIR + File.separator + fileName;

                    // Ghi file lên server
                    part.write(filePath);

                    // Tạo URL cho file
                    String fileUrl = request.getContextPath() + "/assets/uploads/" + fileName;

                    // Phân loại type
                    String type;
                    if (mimeType != null) {
                        if (mimeType.startsWith("image/")) {
                            type = "image";
                        } else if (mimeType.startsWith("video/")) {
                            type = "video";
                        } else {
                            type = "file";  // Đối với các loại file khác
                        }
                    } else {
                        type = "unknown"; // Nếu không xác định được kiểu
                    }

                    // Lưu thông tin file
                    fileInfo.put("url", fileUrl);
                    fileInfo.put("type", type);
                    fileDetails.add(fileInfo); // Thêm vào danh sách
                }
            }

        // Sử dụng Gson để tạo JSON
        Gson gson = new GsonBuilder().disableHtmlEscaping().create(); // Tạo Gson object

        String json = gson.toJson(fileDetails); // Chuyển đổi fileDetails sang JSON


        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json); // Ghi chuỗi JSON vào response

    }

}
