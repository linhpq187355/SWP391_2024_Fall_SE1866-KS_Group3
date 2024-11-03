package com.homesharing.model;

public class BlogPostCategory {
    private int blogPostId;
    private int categoryId;

    public BlogPostCategory() {
    }

    public BlogPostCategory(int blogPostId, int categoryId) {
        this.blogPostId = blogPostId;
        this.categoryId = categoryId;
    }

    public int getBlogPostId() {
        return blogPostId;
    }

    public void setBlogPostId(int blogPostId) {
        this.blogPostId = blogPostId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
