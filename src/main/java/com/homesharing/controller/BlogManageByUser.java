package com.homesharing.controller;

import com.homesharing.dao.BlogDAO;
import com.homesharing.dao.impl.BlogDAOImpl;
import com.homesharing.model.BlogPost;
import com.homesharing.model.Home;
import com.homesharing.service.BlogService;
import com.homesharing.service.impl.BlogServiceImpl;
import com.homesharing.util.CookieUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/user-blog")
public class BlogManageByUser extends HttpServlet {
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
        String userId = CookieUtil.getCookie(request, "id");

        // Validate if the user ID is missing or empty
        if (userId == null || userId.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing user ID");
            return;
        }

        try {
            int userIdInt = Integer.parseInt(userId);
            List<BlogPost> posts = blogService.getPostsByAuthorId(userIdInt);
            request.setAttribute("posts", posts);
            int pageSize = 5;
            int pageNumber = 1;

            // Check if the request contains a page parameter, and parse it
            String pageParam = request.getParameter("page");
            if (pageParam != null) {
                pageNumber = Integer.parseInt(pageParam);
            }
            List<String> formattedDates = new ArrayList<>();
            for (BlogPost post : posts) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                String formattedDate = post.getCreatedAt().format(formatter);
                formattedDates.add(formattedDate);
            }

            // Calculate total items and total pages for pagination
            int totalItems = posts.size();
            int totalPages = (int) Math.ceil((double) totalItems / pageSize);
            int startItem = (pageNumber - 1) * pageSize;
            List<BlogPost> paginatedPosts = posts.subList(
                    Math.min(startItem, totalItems),
                    Math.min(startItem + pageSize, totalItems));
            request.setAttribute("posts", paginatedPosts);
            request.setAttribute("currentPage", pageNumber);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("formattedCreatedAt", formattedDates);

            request.getRequestDispatcher("manage-blog.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            // Handle invalid user ID format
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid user ID format");
        } catch (Exception e) {
            // Catch and handle other exceptions
            e.printStackTrace();
            response.getWriter().write("An error occurred: " + e.getMessage());
        }


    }

}
