package com.homesharing.controller;

import com.homesharing.dao.BlogDAO;
import com.homesharing.dao.impl.BlogDAOImpl;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.BlogPost;
import com.homesharing.service.BlogService;
import com.homesharing.service.impl.BlogServiceImpl;
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
                if ("approve".equalsIgnoreCase(action)) {
                    blogService.updatePostStatus(postId, "APPROVED");
                } else if ("delete".equalsIgnoreCase(action)) {
                    blogService.updatePostStatus(postId, "DELETED");
                }
                response.sendRedirect("/dashboard/blog-list?authorId=" + authorIdParam);
            } catch (Exception e) {
                throw new ServletException("Error handling blog post action: " + e.getMessage(), e);
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Post ID is required.");
        }
    }


}
