package com.homesharing.controller;

import com.homesharing.dao.BlogDAO;
import com.homesharing.dao.impl.BlogDAOImpl;
import com.homesharing.model.BlogPost;
import com.homesharing.model.Category;
import com.homesharing.service.BlogService;
import com.homesharing.service.impl.BlogServiceImpl;
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
@WebServlet("/update-blog")
public class UpdateBlogServlet extends HttpServlet {
    private BlogService blogService;
    private BlogDAO blogDAO;
    private static final String UPLOAD_DIRECTORY = "assets/img/home-images";
    private static final Logger logger = LoggerFactory.getLogger(UpdateBlogServlet.class);

    @Override
    public void init() throws ServletException {
        this.blogDAO = new BlogDAOImpl();
        this.blogService = new BlogServiceImpl(blogDAO);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Get the list of categories and the blog post to update
        int postId = Integer.parseInt(req.getParameter("postId"));
        BlogPost blogPost = blogService.getPostById(postId);
        List<Category> categories = blogDAO.getAllCategories();
        List<Category> selectedCategories = blogService.getCategoriesByBlogPostId(postId);

        // If the blog post does not exist, show an error
        if (blogPost == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Blog post not found");
            return;
        }
        blogService.deleteBlogPostCategories(postId);


        // Set the blog post and categories as request attributes
        req.setAttribute("blogPost", blogPost);
        req.setAttribute("categories", categories);
        req.setAttribute("selectedCategories", selectedCategories);
        req.getRequestDispatcher("/update-blog.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the post ID and other parameters from the request
        int postId = Integer.parseInt(request.getParameter("postId"));
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String shortDescription = request.getParameter("shortDescription");
        String status = request.getParameter("status");
        String[] categoryIds = request.getParameterValues("categoryIds");

        // Validate required fields
        if (title == null || title.trim().isEmpty() || content == null || content.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing required fields");
            return;
        }

        // Update the blog post object
        BlogPost blogPost = new BlogPost();
        blogPost.setId(postId);
        blogPost.setTitle(title);
        blogPost.setContent(content);
        blogPost.setShortDescription(shortDescription);
        blogPost.setModifiedDate(LocalDateTime.now());
        blogPost.setStatus(status != null && (status.equals("drafted") || status.equals("pending")) ? status : "pending");

        Part filePart = request.getPart("images");
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = ImageUtil.getUniqueFileName(filePart.getSubmittedFileName());
            String filePath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY + File.separator + fileName;

            // Create the upload directory if it doesn't exist
            File uploadDir = new File(getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            try {
                // Write the uploaded file to the server
                filePart.write(filePath);
                blogPost.setImagePath(UPLOAD_DIRECTORY + File.separator + fileName); // Set the image path in the blog post
            } catch (IOException e) {
                logger.error("Error writing file: {}", e.getMessage());
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Error saving image: " + e.getMessage());
                return;
            }
        }

        // Update the blog post in the database
        blogService.deleteBlogPostCategories(postId);

        // Update the categories if any are selected
        if (categoryIds != null && categoryIds.length > 0) {
            for (String categoryId : categoryIds) {
                blogDAO.saveBlogPostCategory(postId, Integer.parseInt(categoryId));
            }
        }
        blogService.updatePost(blogPost);

        // Redirect the user to the blog post detail page
        String successMessage = blogPost.getStatus().equals("drafted") ?
                "Bài viết được lưu thành công!" :
                "Bài viết được cập nhật thành công vui lòng chờ admin duyệt!";
        request.getSession().setAttribute("successMessage", successMessage);
        response.sendRedirect("blog-detail?postId=" + postId); // Redirect to the updated blog post detail page
    }
}
