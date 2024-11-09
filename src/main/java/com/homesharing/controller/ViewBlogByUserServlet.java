package com.homesharing.controller;

import com.homesharing.dao.BlogDAO;
import com.homesharing.dao.impl.BlogDAOImpl;
import com.homesharing.model.BlogPost;
import com.homesharing.service.BlogService;
import com.homesharing.service.impl.BlogServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/view-blog")
public class ViewBlogByUserServlet extends HttpServlet {
    private BlogService blogService;
    private BlogDAO blogDAO;

    @Override
    public void init() throws ServletException {
        this.blogDAO = new BlogDAOImpl();
        this.blogService = new BlogServiceImpl(blogDAO);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int pageSize = 6;
        int pageNumber = 1;

        String pageParam = request.getParameter("page");
        if (pageParam != null && !pageParam.isEmpty()) {
            try {
                pageNumber = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                pageNumber = 1;
            }
        }
        String categoryIdParam = request.getParameter("categoryId");

        int offset = (pageNumber - 1) * pageSize;
        List<BlogPost> blogPosts = blogService.getAllBlogPosts(offset, pageSize);
        int totalPosts = blogDAO.getTotalBlogPosts();
        int totalPages = (int) Math.ceil((double) totalPosts / pageSize);
        List<String> formattedDates = new ArrayList<>();
        List<String> authorNames = new ArrayList<>();
        for (BlogPost blogPost : blogPosts) {
            String authorName = blogService.getAuthorNameById(blogPost.getAuthorId());
            authorNames.add(authorName);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            String formattedDate = blogPost.getCreatedAt().format(formatter);
            formattedDates.add(formattedDate);
        }
        if (categoryIdParam != null && !categoryIdParam.isEmpty()) {
            int categoryId = Integer.parseInt(categoryIdParam);
            blogPosts = blogService.getPostsByCategory(categoryId, offset, pageSize);
            totalPosts = blogDAO.getTotalBlogPostsByCategory(categoryId);
        }
        System.out.println("Page Number: " + pageNumber);
        System.out.println("Offset: " + offset);
        System.out.println("Total Posts: " + totalPosts);
        System.out.println("Total Pages: " + totalPages);

        request.setAttribute("formattedCreatedAt", formattedDates);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", pageNumber);
        request.setAttribute("blogPosts", blogPosts);
        request.setAttribute("authorNames", authorNames);

        // Forward to JSP page for displaying blog posts
        request.getRequestDispatcher("blog-page.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String postIdParam = request.getParameter("postId");
        String authorIdParam = request.getParameter("authorId");

        if (postIdParam != null && !postIdParam.isEmpty() && authorIdParam != null && !authorIdParam.isEmpty()) {
            int postId = Integer.parseInt(postIdParam);
            int authorId = Integer.parseInt(authorIdParam);

            try {
                if ("delete".equalsIgnoreCase(action)){
                    blogService.updatePostStatus(postId, "DELETED");
                }
                response.sendRedirect("user-blog?authorId=" + authorId);// Sử dụng authorId đã nhận
            } catch (Exception e) {
                throw new ServletException("Error handling blog post action: " + e.getMessage(), e);
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Post ID and Author ID are required.");
        }
    }

}
