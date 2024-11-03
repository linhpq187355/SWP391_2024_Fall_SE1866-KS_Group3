package com.homesharing.controller;

import com.homesharing.dao.BlogDAO;
import com.homesharing.dao.impl.BlogDAOImpl;
import com.homesharing.model.BlogPost;
import com.homesharing.model.Comment;
import com.homesharing.service.BlogService;
import com.homesharing.service.impl.BlogServiceImpl;
import com.homesharing.util.CookieUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.format.DateTimeFormatter;

import java.io.IOException;
import java.util.List;

@WebServlet("/blog-detail")
public class BlogDetailServlet extends HttpServlet {
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
        String postIdParam = request.getParameter("postId");
        int postId = Integer.parseInt(postIdParam);
        String userIdStr = CookieUtil.getCookie(request, "id");

        if (userIdStr == null || userIdStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        int userId = Integer.parseInt(userIdStr);
        request.setAttribute("userId", userId);

        // Retrieve the blog post along with the author name
        BlogPost blogPost = blogService.getPostById(postId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String formattedDate = blogPost.getCreatedAt().format(formatter);

        request.setAttribute("formattedCreatedAt", formattedDate);
        List<Comment> comments = blogService.getCommentsByPostId(postId);
        request.setAttribute("blogPost", blogPost);
        request.setAttribute("authorName", blogPost.getAuthorName()); // Get author's name directly from the BlogPost object
        request.setAttribute("comments",comments);
        // Forward to the JSP for displaying the blog post details
        request.getRequestDispatcher("blog-detail.jsp").forward(request, response);
    }


}
