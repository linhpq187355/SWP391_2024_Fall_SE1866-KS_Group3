package com.homesharing.dao;

import com.homesharing.model.BlogPost;
import com.homesharing.model.Category;
import com.homesharing.model.Comment;

import java.util.List;

public interface BlogDAO {
    void createPost(BlogPost blogPost);

    void createPostt(BlogPost blogPost);

    List<BlogPost> getAllPosts();

    BlogPost getPostById(int id);

    void updatePost(BlogPost blogPost);

    void deletePost(int id);

    void addComment(Comment comment);

    void updateCommentStatus(int id, String status);

    List<BlogPost> getPendingPosts();

    void approvePost(int postId);

    List<BlogPost> getPostsByAuthorId(int authorId);

    void updatePostStatus(int postId, String status);

    void deletePostByAdmin(int postId);

    String getAuthorNameById(int authorId);

    String getAuthorNameByIdd(int authorId);

    List<Comment> getCommentsByPostId(int postId);

    List<BlogPost> getAllPosts(int offset, int limit);

    void deleteComment(int commentId);

    boolean isPostOwner(int userId, int postId);

    boolean isCommentOwner(int userId, int commentId);

    void saveBlogPostCategory(int blogPostId, int categoryId);

    int getTotalBlogPosts();

    int getTotalBlogPostsByCategory(int categoryId);

    List<BlogPost> getPostsByCategory(int categoryId, int offset, int limit);

    List<BlogPost> getBlogPosts(int offset, int limit);

    List<Category> getAllCategories();

    int savePost(BlogPost blogPost);

    void deleteBlogPostCategories(int postId);

    List<Category> getCategoriesByBlogPostId(int blogPostId);

    int countBlog();
}
