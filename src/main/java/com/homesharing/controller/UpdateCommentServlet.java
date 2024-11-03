package com.homesharing.controller;

import com.homesharing.dao.BlogDAO;
import com.homesharing.dao.impl.BlogDAOImpl;
import com.homesharing.service.BlogService;
import com.homesharing.service.impl.BlogServiceImpl;
import com.homesharing.util.CookieUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/delete-comment")
public class UpdateCommentServlet extends HttpServlet {
    private BlogService blogService;
    private BlogDAO blogDAO;

    @Override
    public void init() throws ServletException {
        this.blogDAO = new BlogDAOImpl();
        this.blogService = new BlogServiceImpl(blogDAO);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commentIdStr = request.getParameter("commentId");
        String postIdStr = request.getParameter("postId");
        String userIdStr = CookieUtil.getCookie(request, "id");

        if (userIdStr == null || userIdStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        int userId = Integer.parseInt(userIdStr);
        request.setAttribute("userId", userId);
        if (commentIdStr == null || postIdStr == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Comment ID and post ID are required");
            return;
        }

        int commentId, postId;
        try {
            commentId = Integer.parseInt(commentIdStr);
            postId = Integer.parseInt(postIdStr);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid comment ID or post ID");
            return;
        }

        try {
            boolean isPostOwner = blogService.isPostOwner(userId, postId);
            boolean isCommentOwner = blogService.isCommentOwner(userId, commentId);
            if (isPostOwner || isCommentOwner) {
                blogService.deleteComment(commentId);
                response.sendRedirect("blog-detail?postId=" + postId);
            } else {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "You do not have permission to delete this comment");
            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Failed to delete comment: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}

