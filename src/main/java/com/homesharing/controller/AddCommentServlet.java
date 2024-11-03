package com.homesharing.controller;

import com.homesharing.dao.BlogDAO;
import com.homesharing.dao.impl.BlogDAOImpl;
import com.homesharing.model.Comment;
import com.homesharing.service.BlogService;
import com.homesharing.service.impl.BlogServiceImpl;
import com.homesharing.util.CookieUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/add-comment")
public class AddCommentServlet extends HttpServlet {
    private BlogService blogService;
    private BlogDAO blogDAO;

    @Override
    public void init() throws ServletException {
        this.blogDAO = new BlogDAOImpl();
        this.blogService = new BlogServiceImpl(blogDAO);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userIdStr = CookieUtil.getCookie(request, "id");

        // Kiểm tra user ID
        if (userIdStr == null || userIdStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return; // Dừng thực hiện sau khi chuyển hướng
        }

        int userId;
        try {
            userId = Integer.parseInt(userIdStr);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid user ID");
            return;
        }

        // Thực hiện thêm bình luận
        String content = request.getParameter("comment");
        String postIdStr = request.getParameter("postId");

        if (postIdStr == null || postIdStr.isEmpty()) {
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        int postId;
        try {
            postId = Integer.parseInt(postIdStr);
        } catch (NumberFormatException e) {
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }
        Comment newComment = new Comment();
        newComment.setPostId(postId);
        newComment.setUserId(userId);
        newComment.setContent(content);
        newComment.setCreatedAt(LocalDateTime.now());
        newComment.setStatus("ACTIVE");

        try {
            blogService.addComment(newComment);
            response.sendRedirect("blog-detail?postId=" + postId);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Failed to add comment: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}
