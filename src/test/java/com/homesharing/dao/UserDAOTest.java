package com.homesharing.dao;

import com.homesharing.dao.impl.UserDAOImpl;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.User;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UserDAOTest {
    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockStatement;

    @InjectMocks
    private UserDAOImpl userDAO;  // Lớp cần test

    @BeforeEach
    public void setUp() throws SQLException {
        // Khởi tạo Mockito và mock các đối tượng
        MockitoAnnotations.openMocks(this);

        // Giả lập khi chuẩn bị statement
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
    }

    @Test
    public void testUpdateUserProfile_Success() throws SQLException, IOException, ClassNotFoundException {
        // Tạo một đối tượng User giả lập
        User mockUser = new User();
        mockUser.setId(1);
        mockUser.setEmail("test@example.com");
        mockUser.setPhoneNumber("0123456789");
        mockUser.setFirstName("John");
        mockUser.setLastName("Doe");
        mockUser.setAvatar("avatar.png");
        mockUser.setDob(java.time.LocalDate.of(1990, 1, 1));

        // Giả lập kết quả khi `executeUpdate()` được gọi
        when(mockStatement.executeUpdate()).thenReturn(1);

        // Gọi hàm updateUserProfile
        int rowsUpdated = userDAO.updateUserProfile(mockUser);

        // Kiểm tra kết quả
        assertEquals(1, rowsUpdated);

        // Kiểm tra xem các phương thức set được gọi với đúng tham số
        verify(mockStatement).setString(1, mockUser.getEmail());
        verify(mockStatement).setString(2, mockUser.getPhoneNumber());
        verify(mockStatement).setString(3, mockUser.getFirstName());
        verify(mockStatement).setString(4, mockUser.getLastName());
        verify(mockStatement).setString(5, mockUser.getAvatar());
        verify(mockStatement).setDate(6, Date.valueOf(mockUser.getDob()));
        verify(mockStatement).setInt(7, mockUser.getId());

        // Kiểm tra executeUpdate được gọi đúng 1 lần
        verify(mockStatement, times(1)).executeUpdate();
    }

    @Test
    public void testUpdateUserProfile_SQLException() throws SQLException {
        // Tạo một đối tượng User giả lập
        User mockUser = new User();
        mockUser.setId(1);
        mockUser.setEmail("test@example.com");

        // Giả lập SQLException khi gọi executeUpdate
        when(mockStatement.executeUpdate()).thenThrow(new SQLException("Database error"));

        // Kiểm tra nếu SQLException bị ném ra trong quá trình thực thi
        try {
            userDAO.updateUserProfile(mockUser);
        } catch (GeneralException e) {
            assertEquals("Error update user profile: Database error", e.getMessage());
        }
    }
}
