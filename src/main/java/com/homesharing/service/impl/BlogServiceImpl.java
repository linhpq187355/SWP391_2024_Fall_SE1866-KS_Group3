package com.homesharing.service.impl;

import com.homesharing.model.Category;
import com.homesharing.service.BlogService;


import com.homesharing.dao.BlogDAO;
import com.homesharing.model.BlogPost;
import com.homesharing.model.Comment;

import java.util.List;

public class BlogServiceImpl implements BlogService {

    private final BlogDAO blogDAO;

    public BlogServiceImpl(BlogDAO blogDAO) {
        this.blogDAO = blogDAO;
    }

    @Override
    public void createPost(BlogPost blogPost) {
        blogDAO.createPost(blogPost);
    }
    @Override
    public List<BlogPost> getPostsByAuthorId(int authorId) {
        return blogDAO.getPostsByAuthorId(authorId);
    }
    @Override
    public List<BlogPost> getAllPosts() {
        return blogDAO.getAllPosts();
    }
    @Override
    public List<BlogPost> getBlogPosts(int offset, int pageSize) {
        return blogDAO.getBlogPosts(offset, pageSize);
    }
    @Override
    public List<BlogPost> getAllBlogPosts(int offset, int pageSize) {
        return blogDAO.getAllPosts(offset, pageSize);
    }
    @Override
    public List<BlogPost> getPostsByCategory(int categoryId, int offset, int limit) {
        return blogDAO.getPostsByCategory(categoryId, offset, limit);
    }

    @Override
    public int getTotalBlogPosts() {
        return blogDAO.getTotalBlogPosts();
    }
    @Override
    public BlogPost getPostById(int id) {
        return blogDAO.getPostById(id);
    }

    @Override
    public void updatePost(BlogPost blogPost) {
        blogDAO.updatePost(blogPost);
    }

    @Override
    public void deletePost(int id) {
        blogDAO.deletePost(id);
    }

    @Override
    public void addComment(Comment comment) {
        blogDAO.addComment(comment);
    }
    @Override
    public String getAuthorNameById(int authorId) {
        return blogDAO.getAuthorNameById(authorId); // Gọi phương thức từ DAO
    }
    @Override
    public List<Comment> getCommentsByPostId(int postId) {
        return blogDAO.getCommentsByPostId(postId);
    }
    @Override
    public boolean isPostOwner(int userId, int postId) {
        return blogDAO.isPostOwner(userId, postId);
    }
    @Override
    public boolean isCommentOwner(int userId, int commentId) {
        return blogDAO.isCommentOwner(userId, commentId);
    }
    @Override
    public void updateCommentStatus(int id, String status) {
        blogDAO.updateCommentStatus(id, status);
    }

    @Override
    public List<BlogPost> getPendingPosts() {
        return blogDAO.getPendingPosts();
    }

    @Override
    public void approvePost(int postId) {
        blogDAO.approvePost(postId);
    }
    @Override
    public void deleteComment(int commentId) {
        blogDAO.deleteComment(commentId);
    }

    @Override
    public void updatePostStatus(int postId, String status) {
        blogDAO.updatePostStatus(postId, status);
    }

    @Override
    public void deletePostByAdmin(int postId) {
        blogDAO.deletePostByAdmin(postId);
    }

    @Override
    public List<Category> getCategoriesByBlogPostId(int blogPostId) {
        return blogDAO.getCategoriesByBlogPostId(blogPostId);
    }
    @Override
    public void deleteBlogPostCategories(int postId) {
        blogDAO.deleteBlogPostCategories(postId);
    }
}

