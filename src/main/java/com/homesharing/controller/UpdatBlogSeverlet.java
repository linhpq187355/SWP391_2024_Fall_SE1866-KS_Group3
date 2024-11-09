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
import java.time.LocalDateTime;
@WebServlet("/update-blog")
public class UpdatBlogSeverlet extends HttpServlet {
    private BlogService blogService;
    private BlogDAO blogDAO;

    @Override
    public void init() throws ServletException {
        this.blogDAO = new BlogDAOImpl();
        this.blogService = new BlogServiceImpl(blogDAO);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int postId = Integer.parseInt(request.getParameter("postId"));

        BlogPost blogPost = blogService.getPostById(postId);

        request.setAttribute("blogPost", blogPost);

        request.getRequestDispatcher("/update-blog.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int postId = Integer.parseInt(request.getParameter("postId"));
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String shortDescription = request.getParameter("shortDescription");

        // Cập nhật thông tin bài viết
        BlogPost blogPost = new BlogPost();
        blogPost.setId(postId);
        blogPost.setTitle(title);
        blogPost.setContent(content);
        blogPost.setShortDescription(shortDescription);
        blogPost.setModifiedDate(LocalDateTime.now());

        // Gọi phương thức update để lưu thay đổi vào cơ sở dữ liệu
        blogService.updatePost(blogPost);

        // Chuyển hướng về trang xem bài viết sau khi cập nhật thành công
        response.sendRedirect("blog-detail?postId=" + postId);
    }
}
