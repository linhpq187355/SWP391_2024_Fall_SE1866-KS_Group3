package com.homesharing.dao.impl;

import com.homesharing.conf.DBContext;
import com.homesharing.dao.BlogDAO;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.BlogPost;
import com.homesharing.model.Category;
import com.homesharing.model.Comment;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BlogDAOImpl implements BlogDAO {

    @Override
    public void createPost(BlogPost blogPost) {
        String sql = "INSERT INTO BlogPost (title, author_id, status, content,image_path,short_description) VALUES (?, ?, ?, ?, ?,?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, blogPost.getTitle());
            preparedStatement.setInt(2, blogPost.getAuthorId());
            preparedStatement.setString(3, blogPost.getStatus());
            preparedStatement.setString(4, blogPost.getContent());
            preparedStatement.setString(5,blogPost.getImagePath());
            preparedStatement.setString(6, blogPost.getShortDescription());
            preparedStatement.executeUpdate();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error creating post: " + e.getMessage(), e);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new GeneralException("Error closing database resources: " + e.getMessage(), e);
            }
        }
    }
    @Override
    public void createPostt(BlogPost blogPost) {
        String insertPostSQL = "INSERT INTO BlogPost (title, author_id, status, content, image_path, short_description) VALUES (?, ?, ?, ?, ?, ?)";
        String insertCategorySQL = "INSERT INTO BlogPostCategory (blog_post_id, category_id) VALUES (?, ?)"; // Thay đổi cho bảng liên kết
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement categoryStatement = null;

        try {
            connection = DBContext.getConnection();
            connection.setAutoCommit(false); // Bắt đầu giao dịch

            // Chèn bài viết vào bảng BlogPost
            preparedStatement = connection.prepareStatement(insertPostSQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, blogPost.getTitle());
            preparedStatement.setInt(2, blogPost.getAuthorId());
            preparedStatement.setString(3, blogPost.getStatus());
            preparedStatement.setString(4, blogPost.getContent());
            preparedStatement.setString(5, blogPost.getImagePath());
            preparedStatement.setString(6, blogPost.getShortDescription());
            preparedStatement.executeUpdate();

            // Lấy ID của bài viết vừa chèn
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int blogPostId = generatedKeys.getInt(1); // Lấy ID của bài viết vừa chèn

                // Chèn các category cho bài viết
                if (blogPost.getCategories() != null) {
                    for (Category category : blogPost.getCategories()) {
                        categoryStatement = connection.prepareStatement(insertCategorySQL);
                        categoryStatement.setInt(1, blogPostId);
                        categoryStatement.setInt(2, category.getId()); // Giả sử bạn đã có ID của category
                        categoryStatement.executeUpdate();
                    }
                }
            }

            connection.commit(); // Cam kết giao dịch
        } catch (SQLException | IOException | ClassNotFoundException e) {
            try {
                if (connection != null) {
                    connection.rollback(); // Rollback nếu có lỗi
                }
            } catch (SQLException rollbackEx) {
                throw new GeneralException("Error rolling back transaction: " + rollbackEx.getMessage(), rollbackEx);
            }
            throw new GeneralException("Error creating post: " + e.getMessage(), e);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (categoryStatement != null) {
                    categoryStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new GeneralException("Error closing database resources: " + e.getMessage(), e);
            }
        }
    }



    @Override
    public List<BlogPost> getAllPosts() {
        List<BlogPost> blogPosts = new ArrayList<>();
        String sql = "SELECT bp.id, bp.title, bp.author_id, bp.created_at, bp.modifiedDate, bp.status, bp.content, bp.image_path,bp.short_description, "
                + "u.firstName, u.lastName "
                + "FROM BlogPost bp "
                + "JOIN Hss_users u ON bp.author_id = u.id";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                BlogPost blogPost = new BlogPost();
                blogPost.setId(resultSet.getInt("id"));
                blogPost.setTitle(resultSet.getString("title"));
                blogPost.setContent(resultSet.getString("content"));
                blogPost.setAuthorId(resultSet.getInt("author_id"));
                blogPost.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                blogPost.setModifiedDate(resultSet.getTimestamp("modifiedDate").toLocalDateTime());
                blogPost.setStatus(resultSet.getString("status"));
                blogPost.setImagePath(resultSet.getString("image_path"));
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                blogPost.setShortDescription(resultSet.getString("short_description"));
                String authorName = firstName + " " + lastName; // Kết hợp tên và họ
                blogPost.setAuthorName(authorName); // Thiết lập tên tác giả cho bài viết

                blogPosts.add(blogPost);                }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error retrieving posts: " + e.getMessage(), e);
        } finally {
            // Closing resources
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new GeneralException("Error closing database resources: " + e.getMessage(), e);
            }
        }
        return blogPosts;
    }

    @Override
    public BlogPost getPostById(int id) {
        BlogPost blogPost = null;
        String sql = "SELECT bp.id, bp.title, bp.content, bp.author_id, bp.created_at, bp.modifiedDate, bp.status, bp.image_path,bp.short_description, u.firstName, u.lastName " +
                "FROM BlogPost bp " +
                "JOIN Hss_users u ON bp.author_id = u.id " +
                "WHERE bp.id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                blogPost = new BlogPost();
                blogPost.setId(resultSet.getInt("id"));
                blogPost.setTitle(resultSet.getString("title"));
                blogPost.setContent(resultSet.getString("content"));
                blogPost.setAuthorId(resultSet.getInt("author_id"));
                blogPost.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                blogPost.setModifiedDate(resultSet.getTimestamp("modifiedDate").toLocalDateTime());
                blogPost.setStatus(resultSet.getString("status"));
                blogPost.setImagePath(resultSet.getString("image_path"));
                blogPost.setShortDescription(resultSet.getString("short_description"));
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                blogPost.setAuthorName(firstName + " " + lastName);
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error retrieving post by id: " + e.getMessage(), e);
        } finally {
            // Closing resources
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new GeneralException("Error closing database resources: " + e.getMessage(), e);
            }
        }
        return blogPost;
    }

    @Override
    public void updatePost(BlogPost blogPost) {
        String sql = "UPDATE BlogPost SET title = ?, content = ?, modifiedDate = ?, short_description = ?, image_path = ?, status = ? WHERE id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, blogPost.getTitle());
            preparedStatement.setString(2, blogPost.getContent());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(blogPost.getModifiedDate()));
            preparedStatement.setString(4, blogPost.getShortDescription());
            preparedStatement.setString(5, blogPost.getImagePath());
            preparedStatement.setString(6, blogPost.getStatus());
            preparedStatement.setInt(7, blogPost.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error updating post: " + e.getMessage(), e);
        } finally {
            // Đóng các tài nguyên
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new GeneralException("Error closing database resources: " + e.getMessage(), e);
            }
        }
    }


    @Override
    public void deletePost(int id) {
        String sql = "UPDATE BlogPost SET status = 'deleted' WHERE id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error deleting post: " + e.getMessage(), e);
        } finally {
            // Closing resources
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new GeneralException("Error closing database resources: " + e.getMessage(), e);
            }
        }

    }
    @Override
    public void addComment(Comment comment) {
        String sql = "INSERT INTO Comment (post_id, user_id, content, created_at, status) VALUES (?, ?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, comment.getPostId());
            preparedStatement.setInt(2, comment.getUserId());
            preparedStatement.setString(3, comment.getContent());
            preparedStatement.setTimestamp(4, java.sql.Timestamp.valueOf(comment.getCreatedAt()));
            preparedStatement.setString(5, comment.getStatus());
            preparedStatement.executeUpdate();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error adding comment: " + e.getMessage(), e);
        } finally {
            // Closing resources
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new GeneralException("Error closing database resources: " + e.getMessage(), e);
            }
        }
    }

    @Override
    public void updateCommentStatus(int id, String status) {
        String sql = "UPDATE Comment SET status = ? WHERE id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error updating comment status: " + e.getMessage(), e);
        } finally {
            // Đóng kết nối
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new GeneralException("Error closing database resources: " + e.getMessage(), e);
            }
        }
    }
    @Override
    public List<BlogPost> getPendingPosts() {
        List<BlogPost> pendingPosts = new ArrayList<>();
        String sql = "SELECT * FROM BlogPost WHERE status = 'pending'";

        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                BlogPost blogPost = new BlogPost();
                blogPost.setId(resultSet.getInt("id"));
                blogPost.setTitle(resultSet.getString("title"));
                blogPost.setContent(resultSet.getString("content"));
                blogPost.setAuthorId(resultSet.getInt("author_id"));
                blogPost.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                blogPost.setModifiedDate(resultSet.getTimestamp("modifiedDate").toLocalDateTime());
                blogPost.setStatus(resultSet.getString("status"));
                pendingPosts.add(blogPost);
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error retrieving pending posts from the database: " + e.getMessage(), e);
        }
        return pendingPosts;
    }

    @Override
    public void approvePost(int postId) {

    }
    @Override
    public List<BlogPost> getPostsByAuthorId(int authorId) {
        List<BlogPost> posts = new ArrayList<>();
        String sql = "SELECT id, title, created_at, status, image_path FROM BlogPost WHERE author_id = ?";

        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, authorId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                BlogPost post = new BlogPost();
                post.setId(resultSet.getInt("id"));
                post.setTitle(resultSet.getString("title"));
                post.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                post.setStatus(resultSet.getString("status"));
                post.setImagePath(resultSet.getString("image_path"));
                posts.add(post);
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error fetching posts by author ID: " + e.getMessage(), e);
        }

        return posts;
    }

    @Override
    public void updatePostStatus(int postId, String status) {
        String sql = "UPDATE BlogPost SET status = ? WHERE id = ?";

        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, postId);
            preparedStatement.executeUpdate();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error updating post status: " + e.getMessage(), e);
        }
    }

    @Override
    public void deletePostByAdmin(int postId) {

    }


    @Override
    public String getAuthorNameById(int authorId) {
        String sql = "SELECT firstName, lastName FROM Hss_users WHERE id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String authorName = "";

        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, authorId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                authorName = firstName + " " + lastName; // Kết hợp tên và họ
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error retrieving author name: " + e.getMessage(), e);
        } finally {
            // Đóng tài nguyên
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new GeneralException("Error closing database resources: " + e.getMessage(), e);
            }
        }
        return authorName;
    }
    @Override
    public String getAuthorNameByIdd(int authorId) {
        String sql = "SELECT first_name, last_name FROM Hss_users WHERE id = ?";
        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, authorId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String fullName = firstName + " " + lastName;
                System.out.println("Author Name: " + fullName); // In ra tên tác giả
                return fullName; // Kết hợp tên và họ
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error retrieving author name: " + e.getMessage(), e);
        }
        return null; // Trả về null nếu không tìm thấy
    }
    @Override
    public List<Comment> getCommentsByPostId(int postId) {
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT c.id, c.post_id, c.user_id, c.content, c.created_at, c.status, " +
                "u.firstName, u.lastName " +
                "FROM Comment c " +
                "JOIN Hss_users u ON c.user_id = u.id " +
                "WHERE c.post_id = ?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, postId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Comment comment = new Comment();
                comment.setId(resultSet.getInt("id"));
                comment.setPostId(resultSet.getInt("post_id"));
                comment.setUserId(resultSet.getInt("user_id"));
                comment.setContent(resultSet.getString("content"));
                comment.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                comment.setStatus(resultSet.getString("status"));

                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                comment.setUserName(firstName + " " + lastName); // Set username
                comments.add(comment);
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error retrieving comments by post id: " + e.getMessage(), e);
        } finally {
            // Closing resources
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new GeneralException("Error closing database resources: " + e.getMessage(), e);
            }
        }
        return comments;
    }
    @Override
    public List<BlogPost> getAllPosts(int offset, int limit) {
        List<BlogPost> blogPosts = new ArrayList<>();
        String sql = "SELECT bp.id, bp.title, bp.author_id, bp.created_at, bp.modifiedDate, bp.status, bp.content, bp.image_path, bp.short_description, "
                + "u.firstName, u.lastName "
                + "FROM BlogPost bp "
                + "JOIN Hss_users u ON bp.author_id = u.id "
                + "WHERE bp.status = ? "
                + "ORDER BY bp.created_at DESC " // Sắp xếp theo ngày tạo
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY"; // Sử dụng OFFSET và FETCH NEXT cho phân trang

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "APPROVED"); // Thiết lập điều kiện cho status
            preparedStatement.setInt(2, offset);
            preparedStatement.setInt(3, limit); // Thiết lập limit cho số bài viết lấy

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                BlogPost blogPost = new BlogPost();
                blogPost.setId(resultSet.getInt("id"));
                blogPost.setTitle(resultSet.getString("title"));
                blogPost.setContent(resultSet.getString("content"));
                blogPost.setAuthorId(resultSet.getInt("author_id"));
                blogPost.setCreatedAt(resultSet.getTimestamp("created_at") != null ? resultSet.getTimestamp("created_at").toLocalDateTime() : null);
                blogPost.setModifiedDate(resultSet.getTimestamp("modifiedDate") != null ? resultSet.getTimestamp("modifiedDate").toLocalDateTime() : null);
                blogPost.setStatus(resultSet.getString("status"));
                blogPost.setImagePath(resultSet.getString("image_path"));
                blogPost.setShortDescription(resultSet.getString("short_description"));

                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String authorName = (firstName != null ? firstName : "") + " " + (lastName != null ? lastName : ""); // Kiểm tra null trước khi nối

                blogPost.setAuthorName(authorName); // Thiết lập tên tác giả cho bài viết
                blogPosts.add(blogPost);
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error retrieving posts: " + e.getMessage(), e);
        } finally {
            // Đóng các tài nguyên
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new GeneralException("Error closing database resources: " + e.getMessage(), e);
            }
        }
        return blogPosts;
    }

    @Override
    public void deleteComment(int commentId) {
        String sql = "DELETE FROM Comment WHERE id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, commentId);
            preparedStatement.executeUpdate();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error deleting comment: " + e.getMessage(), e);
        } finally {
            // Closing resources
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new GeneralException("Error closing database resources: " + e.getMessage(), e);
            }
        }
    }
    @Override
    public boolean isPostOwner(int userId, int postId) {
        String sql = "SELECT 1 FROM BlogPost WHERE id = ? AND author_id = ?";
        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, postId);
            preparedStatement.setInt(2, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean isCommentOwner(int userId, int commentId) {
        String sql = "SELECT 1 FROM Comment WHERE id = ? AND user_id = ?";
        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, commentId);
            preparedStatement.setInt(2, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void saveBlogPostCategory(int blogPostId, int categoryId) {
        String sql = "INSERT INTO [dbo].[BlogPostCategories] ([blogPost_Id], [category_Id]) VALUES (?, ?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, blogPostId);
            preparedStatement.setInt(2, categoryId);
            preparedStatement.executeUpdate();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error saving blog post category to the database: " + e.getMessage(), e);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new GeneralException("Error closing database resources: " + e.getMessage(), e);
            }
        }
    }
    @Override
    public int getTotalBlogPosts() {
        int totalPosts = 0;
        String sql = "SELECT COUNT(*) FROM BlogPost WHERE status = ?";
        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) { // Chỉ cần tạo PreparedStatement
            preparedStatement.setString(1, "APPROVED"); // Đặt giá trị trước khi gọi executeQuery
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    totalPosts = resultSet.getInt(1);
                }
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace(); // Xử lý lỗi
        }

        return totalPosts;
    }
    @Override
    public int getTotalBlogPostsByCategory(int categoryId) {
        int totalPosts = 0;
        String sql = "SELECT COUNT(*) FROM BlogPost bp "
                + "JOIN BlogPostCategories bpc ON bp.id = bpc.blogpost_id "
                + "WHERE bpc.category_id = ? AND bp.status = 'APPROVED'";
        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, categoryId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                totalPosts = resultSet.getInt(1);
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return totalPosts;
    }

    @Override
    public List<BlogPost> getPostsByCategory(int categoryId, int offset, int limit) {
        List<BlogPost> blogPosts = new ArrayList<>();
        String sql = "SELECT bp.* FROM BlogPost bp "
                + "JOIN BlogPostCategories bpc ON bp.id = bpc.blogpost_id "
                + "WHERE bpc.category_id = ? AND bp.status = 'APPROVED' "
                + "ORDER BY bp.created_at DESC "
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, categoryId);
            preparedStatement.setInt(2, offset);
            preparedStatement.setInt(3, limit);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                // Khởi tạo đối tượng BlogPost và thêm vào danh sách
                BlogPost blogPost = new BlogPost();
                // Đặt các thuộc tính từ ResultSet
                blogPost.setId(resultSet.getInt("id"));
                blogPost.setTitle(resultSet.getString("title"));
                blogPost.setAuthorId(resultSet.getInt("author_id"));
                blogPost.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                blogPost.setStatus(resultSet.getString("status"));
                // Thêm các thuộc tính khác...
                blogPosts.add(blogPost);
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace(); // Xử lý lỗi
        }
        return blogPosts;
    }

    @Override
    public List<BlogPost> getBlogPosts(int offset, int limit) {
        List<BlogPost> posts = new ArrayList<>();
        String sql = "SELECT * FROM BlogPost ORDER BY created_at DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, offset);
            preparedStatement.setInt(2, limit);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                BlogPost post = new BlogPost();
                // Đặt giá trị cho đối tượng post từ resultSet
                post.setId(resultSet.getInt("id"));
                post.setTitle(resultSet.getString("title"));
                post.setContent(resultSet.getString("content"));
                post.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                post.setModifiedDate(resultSet.getTimestamp("modifiedDate").toLocalDateTime());
                post.setShortDescription(resultSet.getString("short_description"));
                post.setAuthorId(resultSet.getInt("author_id"));
                // Thêm các thuộc tính khác nếu cần
                posts.add(post);
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace(); // Xử lý lỗi
        }

        return posts;
    }
    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT [id], [name] FROM [dbo].[Categories]";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Category category = new Category();
                category.setId(resultSet.getInt("id"));
                category.setName(resultSet.getString("name"));
                categories.add(category);
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error retrieving categories from the database: " + e.getMessage(), e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new GeneralException("Error closing database resources: " + e.getMessage(), e);
            }
        }
        return categories;
    }

    @Override
    public int savePost(BlogPost blogPost) {
        String sql = "INSERT INTO [dbo].[BlogPost] ([title], [author_id], [created_at], [modifiedDate], [status], [content], [image_path], [short_description]) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        // Using try-with-resources to manage the database connection and resources
        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Setting parameters for the PreparedStatement using the BlogPost object
            preparedStatement.setString(1, blogPost.getTitle());
            preparedStatement.setInt(2, blogPost.getAuthorId());
            preparedStatement.setTimestamp(3, java.sql.Timestamp.valueOf(blogPost.getCreatedAt()));
            preparedStatement.setTimestamp(4, blogPost.getModifiedDate() != null ? java.sql.Timestamp.valueOf(blogPost.getModifiedDate()) : null);
            preparedStatement.setString(5, blogPost.getStatus());
            preparedStatement.setString(6, blogPost.getContent());
            preparedStatement.setString(7, blogPost.getImagePath());
            preparedStatement.setString(8, blogPost.getShortDescription());

            // Execute the insert statement and capture affected rows
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                // Retrieve the generated BlogPost ID
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);  // Return the generated BlogPost ID
                    } else {
                        throw new SQLException("Creating blog post failed, no ID obtained.");
                    }
                }
            } else {
                throw new SQLException("Creating blog post failed, no rows affected.");
            }

        } catch (SQLException | IOException | ClassNotFoundException e) {
            // Re-throw as runtime exception to be handled by the service layer
            throw new IllegalArgumentException("Error saving blog post to the database: " + e.getMessage(), e);
        }
    }
    @Override
    public void deleteBlogPostCategories(int postId) {
        String sql = "DELETE FROM BlogPostCategories WHERE Blogpost_id = ?";
        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, postId); // Đặt giá trị trước khi gọi executeUpdate
            preparedStatement.executeUpdate();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace(); // Xử lý lỗi
        }
    }
    @Override
    public List<Category> getCategoriesByBlogPostId(int blogPostId) {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT c.id, c.name FROM Categories c " +
                "JOIN BlogPostCategories bpc ON c.id = bpc.category_id " +
                "WHERE bpc.blogPost_id = ?";

        try (Connection connection = DBContext.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, blogPostId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Category category = new Category();
                category.setId(resultSet.getInt("id"));
                category.setName(resultSet.getString("name"));
                categories.add(category);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return categories;
    }




}




