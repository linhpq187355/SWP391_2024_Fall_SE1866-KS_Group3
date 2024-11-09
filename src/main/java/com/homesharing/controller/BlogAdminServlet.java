package com.homesharing.controller;

import com.homesharing.dao.BlogDAO;
import com.homesharing.dao.impl.BlogDAOImpl;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.BlogPost;
import com.homesharing.service.BlogService;
import com.homesharing.service.impl.BlogServiceImpl;
import com.homesharing.util.AddNotificationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
@WebServlet("/dashboard/blog-list")
public class BlogAdminServlet extends HttpServlet {
    private BlogService blogService;
    private BlogDAO blogDAO;

    @Override
    public void init() throws ServletException {
        this.blogDAO = new BlogDAOImpl();
        this.blogService = new BlogServiceImpl(blogDAO);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<BlogPost> posts = blogService.getAllPosts();
        for (BlogPost post : posts) {
            String authorName = blogService.getAuthorNameById(post.getAuthorId()); // Fetch author name by authorId
            post.setAuthorName(authorName); // Set the author name in the BlogPost object
        }
        List<String> formattedDates = new ArrayList<>();
        for (BlogPost post : posts) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            String formattedDate = post.getCreatedAt().format(formatter);
            formattedDates.add(formattedDate);
        }
        request.setAttribute("formattedCreatedAt", formattedDates);

        request.setAttribute("posts", posts);
        request.getRequestDispatcher("/dashboard-blog-list.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String postIdParam = request.getParameter("postId");
        String authorIdParam = request.getParameter("authorId");

        if (postIdParam != null && !postIdParam.isEmpty()) {
            int postId = Integer.parseInt(postIdParam);

            try {
                BlogPost post = blogService.getPostById(postId);
                if ("approve".equalsIgnoreCase(action)) {
                    blogService.updatePostStatus(postId, "APPROVED");
                    AddNotificationUtil.getInstance().addNotification(Integer.parseInt(authorIdParam),"Bài viết ngày "+ post.getCreatedAt().getDayOfMonth()+"/"+post.getCreatedAt().getMonthValue()+"/"+post.getCreatedAt().getYear()+" trên diễn đàn của bạn đã được chấp nhận!", "Bài viết trên diễn đàn của bạn đã được chấp nhận!","System", "user-blog?id="+authorIdParam);
                } else if ("delete".equalsIgnoreCase(action)) {
                    blogService.updatePostStatus(postId, "DELETED");
                    AddNotificationUtil.getInstance().addNotification(Integer.parseInt(authorIdParam),"Bài viết ngày "+ post.getCreatedAt().getDayOfMonth()+"/"+post.getCreatedAt().getMonthValue()+"/"+post.getCreatedAt().getYear()+" trên diễn đàn của bạn đã được chấp nhận!", "Bài viết trên diễn đàn của bạn không được chấp nhận!","System", "user-blog?id="+authorIdParam);
                }
                response.sendRedirect("blog-list?authorId=" + authorIdParam);
            } catch (Exception e) {
                throw new ServletException("Error handling blog post action: " + e.getMessage(), e);
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Post ID is required.");
        }
    }


}
