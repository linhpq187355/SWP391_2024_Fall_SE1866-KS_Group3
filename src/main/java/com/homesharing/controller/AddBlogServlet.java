package com.homesharing.controller;

import com.homesharing.dao.BlogDAO;
import com.homesharing.dao.impl.BlogDAOImpl;
import com.homesharing.model.AmentityHome;
import com.homesharing.model.BlogPost;
import com.homesharing.model.BlogPostCategory;
import com.homesharing.model.Category;
import com.homesharing.service.BlogService;
import com.homesharing.service.impl.BlogServiceImpl;
import com.homesharing.util.CookieUtil;
import com.homesharing.util.ImageUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.List;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 50) // 50 MB
@WebServlet("/add-blog")
public class AddBlogServlet extends HttpServlet {
    private BlogService blogService;
    private BlogDAO blogDAO;
    private static final String UPLOAD_DIRECTORY = "assets/img/home-images";
    private static final Logger logger = LoggerFactory.getLogger(AddBlogServlet.class);

    @Override
    public void init() throws ServletException {
        this.blogDAO = new BlogDAOImpl();
        this.blogService = new BlogServiceImpl(blogDAO);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Category> categories = blogDAO.getAllCategories();
        req.setAttribute("categories", categories); // Đặt danh sách thể loại vào request
        req.getRequestDispatcher("/create-blog.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userIdStr = CookieUtil.getCookie(request, "id");

        // Kiểm tra user ID
        if (userIdStr == null || userIdStr.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing user ID");
            return;
        }

        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String shortDescription = request.getParameter("shortDescription");
        String[] categoryIds  = request.getParameterValues("categoryIds");
        String status = request.getParameter("status"); // Lấy trạng thái từ request
        if (title == null || title.trim().isEmpty() || content == null || content.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing required fields");
            return;
        }

        try {
            int userId = Integer.parseInt(userIdStr);
            BlogPost blogPost = new BlogPost();
            blogPost.setTitle(title);
            blogPost.setContent(content);
            blogPost.setCreatedAt(LocalDateTime.now());
            blogPost.setModifiedDate(LocalDateTime.now());
            blogPost.setShortDescription(shortDescription);
            blogPost.setAuthorId(userId);
            blogPost.setStatus(status);
            if (status != null && (status.equals("drafted") || status.equals("pending"))) {
                blogPost.setStatus(status);
            } else {
                blogPost.setStatus("pending"); // Trạng thái mặc định
            }
            Part filePart = request.getPart("images"); // Lấy phần ảnh từ request
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = ImageUtil.getUniqueFileName(filePart.getSubmittedFileName());
                String filePath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY + File.separator + fileName;

                // Kiểm tra thư mục upload
                File uploadDir = new File(getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                try {
                    filePart.write(filePath);
                    blogPost.setImagePath(UPLOAD_DIRECTORY + File.separator + fileName);
                } catch (IOException e) {
                    logger.error("Error writing file: {}", e.getMessage());
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Error saving image: " + e.getMessage());
                    return;
                }
            }
            int postId = blogDAO.savePost(blogPost);
            if (categoryIds == null || categoryIds.length == 0) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No categories selected");
                return;
            }
            for (String categoryId : categoryIds) {
                blogDAO.saveBlogPostCategory(postId, Integer.parseInt(categoryId));
            }
            String successMessage = blogPost.getStatus().equals("drafted")
                    ? "Bài viết được lưu thành công!"
                    : "Baì viết đã được thêm vui lòng chờ admin duyệt bài!";
            request.getSession().setAttribute("successMessage", successMessage);
            response.sendRedirect("blog-detail?postId=" + postId);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid user ID format");
            return;
        } catch (Exception e) {
            logger.error("Error occurred while adding blog post and image: {}", e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error occurred while adding blog post and image");
            return;
        }
    }
}