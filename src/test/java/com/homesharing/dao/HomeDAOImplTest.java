package com.homesharing.dao;

import com.homesharing.conf.DBContext;
import com.homesharing.dao.impl.HomeDAOImpl;
import com.homesharing.dao.impl.UserDAOImpl;
import com.homesharing.model.Home;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class HomeDAOImplTest {
    private HomeDAOImpl homeDAO;

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    private MockedStatic<DBContext> mockedDBContext;

    @BeforeEach
    public void setUp() {
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);

        // Mock DBContext.getConnection() to return the mock connection
        mockedDBContext = Mockito.mockStatic(DBContext.class);
        mockedDBContext.when(DBContext::getConnection).thenReturn(connection);
        homeDAO = new HomeDAOImpl();
    }

    @AfterEach
    public void tearDown() {
        // Close static mock after each test
        mockedDBContext.close();
    }

    @Test
    public void testGetNewHomes_Success() throws SQLException, IOException, ClassNotFoundException {
        List<Home> expectedHomes = new ArrayList<>();

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        // Tạo dữ liệu giả lập cho resultSet
        when(resultSet.next()).thenReturn(true, true, false);  // Có 2 bản ghi
        when(resultSet.getInt("id")).thenReturn(1, 2);
        when(resultSet.getString("address")).thenReturn("123 Main St", "456 Elm St");
        when(resultSet.getBigDecimal("area")).thenReturn(BigDecimal.valueOf(100.5), BigDecimal.valueOf(150.75));
        when(resultSet.getTimestamp("createdDate")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));
        when(resultSet.getInt("priceId")).thenReturn(1001, 1002);

        // Gọi phương thức cần kiểm tra
        List<Home> homes = homeDAO.getNewHomes();

        // Kiểm tra kết quả
        assertEquals(2, homes.size());

        // Kiểm tra chi tiết của từng Home
        assertEquals(1, homes.get(0).getId());
        assertEquals("123 Main St", homes.get(0).getAddress());
        assertEquals(BigDecimal.valueOf(100.5), homes.get(0).getArea());
        assertNotNull(homes.get(0).getCreatedDate());
        assertEquals(1001, homes.get(0).getPriceId());

        assertEquals(2, homes.get(1).getId());
        assertEquals("456 Elm St", homes.get(1).getAddress());
        assertEquals(BigDecimal.valueOf(150.75), homes.get(1).getArea());
        assertNotNull(homes.get(1).getCreatedDate());
        assertEquals(1002, homes.get(1).getPriceId());

        // Xác minh rằng các phương thức đã được gọi đúng với các tham số tương ứng
        verify(resultSet, times(3)).next();
        verify(resultSet, times(2)).getInt("id");
        verify(resultSet, times(2)).getString("address");
        verify(resultSet, times(2)).getBigDecimal("area");
        verify(resultSet, times(2)).getTimestamp("createdDate");
        verify(resultSet, times(2)).getInt("priceId");
    }

    @Test
    public void testGetNewHomes_NoResults() throws SQLException, IOException, ClassNotFoundException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        // Giả lập resultSet không có bản ghi nào
        when(resultSet.next()).thenReturn(false);

        // Gọi phương thức cần kiểm tra
        List<Home> homes = homeDAO.getNewHomes();

        // Kiểm tra danh sách homes phải rỗng
        assertTrue(homes.isEmpty());

        // Xác minh rằng next() chỉ được gọi đúng một lần
        verify(resultSet, times(1)).next();
    }

    @Test
    public void testGetNewHomes_SQLException() throws SQLException, IOException, ClassNotFoundException {

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        // Giả lập SQLException
        when(preparedStatement.executeQuery()).thenThrow(new SQLException("Database error"));

        // Kiểm tra ngoại lệ RuntimeException được ném ra khi có lỗi SQL
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            homeDAO.getNewHomes();
        });

        // Kiểm tra thông điệp ngoại lệ
        assertTrue(exception.getMessage().contains("Error retrieving homes from the database"));

        // Xác minh rằng executeQuery() đã được gọi
        verify(preparedStatement).executeQuery();
    }



}
