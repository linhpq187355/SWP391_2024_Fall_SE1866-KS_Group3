package com.homesharing.service;

import com.homesharing.model.BlogPost;
import com.homesharing.model.Comment;

import java.util.List;

public interface BlogService {
    void createPost(BlogPost blogPost);

    List<BlogPost> getAllPosts();

    List<BlogPost> getBlogPosts(int offset, int pageSize);

    List<BlogPost> getAllBlogPosts(int offset, int pageSize);

    List<BlogPost> getPostsByCategory(int categoryId, int offset, int limit);

    int getTotalBlogPosts();

    BlogPost getPostById(int id);

    void updatePost(BlogPost blogPost);

    void deletePost(int id);

    void addComment(Comment comment);

    List<Comment> getCommentsByPostId(int postId);

    boolean isPostOwner(int userId, int postId);

    boolean isCommentOwner(int userId, int commentId);

    void updateCommentStatus(int id, String status);

    List<BlogPost> getPendingPosts();

    void approvePost(int postId);

    void deleteComment(int commentId);

    void updatePostStatus(int postId, String status);

    void deletePostByAdmin(int postId);

    String getAuthorNameById(int authorId);

    List<BlogPost> getPostsByAuthorId(int authorId);

}


