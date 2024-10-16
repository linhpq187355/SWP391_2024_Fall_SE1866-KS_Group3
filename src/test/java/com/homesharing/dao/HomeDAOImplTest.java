package com.homesharing.dao;

import com.homesharing.conf.DBContext;
import com.homesharing.dao.impl.HomeDAOImpl;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.Home;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.ArgumentCaptor;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

    @Test
    public void testGetMatchingHomes_Success() throws SQLException, IOException, ClassNotFoundException {
        int[] matchingHost = {1, 2};
        List<Home> expectedHomes = new ArrayList<>();

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        // Simulate resultSet with 2 records
        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getInt("id")).thenReturn(1, 2);
        when(resultSet.getString("name")).thenReturn("Home1", "Home2");
        when(resultSet.getString("address")).thenReturn("123 Main St", "456 Elm St");
        when(resultSet.getBigDecimal("area")).thenReturn(BigDecimal.valueOf(100.5), BigDecimal.valueOf(150.75));
        when(resultSet.getInt("leaseDuration")).thenReturn(12, 24);
        when(resultSet.getDate("moveInDate")).thenReturn(
                java.sql.Date.valueOf(LocalDateTime.now().toLocalDate())
        );

        // Call the method
        List<Home> homes = homeDAO.getMatchingHomes(matchingHost);

        // Verify the results
        assertEquals(2, homes.size());
        assertEquals(1, homes.get(0).getId());
        assertEquals("Home1", homes.get(0).getName());
        assertEquals("123 Main St", homes.get(0).getAddress());
        assertEquals(BigDecimal.valueOf(100.5), homes.get(0).getArea());
        assertEquals(12, homes.get(0).getLeaseDuration());
        assertNotNull(homes.get(0).getMoveInDate());

        assertEquals(2, homes.get(1).getId());
        assertEquals("Home2", homes.get(1).getName());
        assertEquals("456 Elm St", homes.get(1).getAddress());
        assertEquals(BigDecimal.valueOf(150.75), homes.get(1).getArea());
        assertEquals(24, homes.get(1).getLeaseDuration());
        assertNotNull(homes.get(1).getMoveInDate());

        verify(resultSet, times(3)).next();
        verify(resultSet, times(2)).getInt("id");
        verify(resultSet, times(2)).getString("name");
        verify(resultSet, times(2)).getString("address");
        verify(resultSet, times(2)).getBigDecimal("area");
        verify(resultSet, times(2)).getInt("leaseDuration");
        verify(resultSet, times(2)).getDate("moveInDate");
    }

    @Test
    public void testGetMatchingHomes_NullInput() {
        int[] matchingHost = null;

        // Call the method
        List<Home> homes = homeDAO.getMatchingHomes(matchingHost);

        // Verify the result is null
        assertEquals(null, homes);
    }

    @Test
    public void testGetMatchingHomes_EmptyArray() {
        int[] matchingHost = {};

        // Call the method
        List<Home> homes = homeDAO.getMatchingHomes(matchingHost);

        // Verify the result is null
        assertEquals(null, homes);
    }

    @Test
    public void testGetMatchingHomes_SQLException() throws SQLException, IOException, ClassNotFoundException {
        int[] matchingHost = {1, 2};

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        // Simulate SQLException
        when(preparedStatement.executeQuery()).thenThrow(new SQLException("Database error"));

        // Verify that a RuntimeException is thrown
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            homeDAO.getMatchingHomes(matchingHost);
        });

        assertTrue(exception.getMessage().contains("Error retrieving homes from the database"));

        // Verify that executeQuery() was called
        verify(preparedStatement).executeQuery();
    }

    @Test
    void testNumOfHome_SQLException() throws SQLException{
        // Given
        Map<String, Object> searchParams = new HashMap<>();

        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

        // When / Then
        SQLException exception = assertThrows(SQLException.class, () -> {
            homeDAO.numOfHome(searchParams);
        });

        assertEquals("Database error", exception.getMessage());
    }

    @Test
    void testNumOfHome_IOException() throws SQLException, IOException, ClassNotFoundException {
        // Given
        Map<String, Object> searchParams = new HashMap<>();

        when(DBContext.getConnection()).thenThrow(new IOException("IO error"));

        // When / Then
        IOException exception = assertThrows(IOException.class, () -> {
            homeDAO.numOfHome(searchParams);
        });

        assertEquals("IO error", exception.getMessage());
    }

    @Test
    void testNumOfHome_ClassNotFoundException() throws SQLException, IOException, ClassNotFoundException {
        // Given
        Map<String, Object> searchParams = new HashMap<>();

        when(DBContext.getConnection()).thenThrow(new ClassNotFoundException("JDBC Driver not found"));

        // When / Then
        ClassNotFoundException exception = assertThrows(ClassNotFoundException.class, () -> {
            homeDAO.numOfHome(searchParams);
        });

        assertEquals("JDBC Driver not found", exception.getMessage());
    }

    @Test
    void testGetMaxArea_ReturnsMaxArea() throws Exception {
        // Arrange
        BigDecimal expectedMinArea = new BigDecimal("50.0");

        // Mock result set to return the expected value
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getBigDecimal("area")).thenReturn(expectedMinArea);

        // Act
        BigDecimal actualMinArea = homeDAO.getMaxArea();

        // Assert
        assertEquals(expectedMinArea, actualMinArea);

        // Verify interactions
        verify(preparedStatement).executeQuery();
        verify(resultSet).next();
        verify(resultSet).getBigDecimal("area");
    }

    @Test
    void testGetMaxArea_ReturnZero_WhenNoResult() throws Exception {
        BigDecimal expectedMinArea = new BigDecimal("0");

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        BigDecimal actualMinArea = homeDAO.getMaxArea();
        assertEquals(expectedMinArea, actualMinArea);
        verify(preparedStatement, times(1)).executeQuery();
    }

    @Test
    void testGetMaxArea_ThrowsGeneralException_WhenSQLException() throws Exception {
        // Arrange
        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

        // Act & Assert
        Exception exception = assertThrows(GeneralException.class, () -> homeDAO.getMaxArea());
        assertEquals("Error get max area in the database: Database error", exception.getMessage());
    }

    @Test
    void testGetMinBed_ReturnsMinBed() throws Exception {
        // Arrange
        int expectedMinBed = 2;

        // Mock result set to return the expected value
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("numOfBedroom")).thenReturn(expectedMinBed);

        // Act
        int actualMinBed = homeDAO.getMinBed();

        // Assert
        assertEquals(expectedMinBed, actualMinBed);

        // Verify interactions
        verify(preparedStatement).executeQuery();
        verify(resultSet).next();
        verify(resultSet).getInt("numOfBedroom");
    }

    @Test
    void testGetMinBed_ReturnZero_WhenNoResult() throws Exception {
        int expectedMinBed = 0;

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        int actualMinBed = homeDAO.getMinBed();
        assertEquals(expectedMinBed, actualMinBed);
        verify(preparedStatement, times(1)).executeQuery();
    }

    @Test
    void testGetMinBed_ThrowsGeneralException_WhenSQLException() throws Exception {
        // Arrange
        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

        // Act & Assert
        Exception exception = assertThrows(GeneralException.class, () -> homeDAO.getMinBed());
        assertEquals("Error get min bed in the database: Database error", exception.getMessage());
    }

    @Test
    void testGetMaxBed_ReturnsMaxBed() throws Exception {
        // Arrange
        int expectedMinBed = 2;

        // Mock result set to return the expected value
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("numOfBedroom")).thenReturn(expectedMinBed);

        // Act
        int actualMinBed = homeDAO.getMaxBed();

        // Assert
        assertEquals(expectedMinBed, actualMinBed);

        // Verify interactions
        verify(preparedStatement).executeQuery();
        verify(resultSet).next();
        verify(resultSet).getInt("numOfBedroom");
    }

    @Test
    void testGetMaxBed_ReturnZero_WhenNoResult() throws Exception {
        int expectedMinBed = 0;

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        int actualMinBed = homeDAO.getMaxBed();
        assertEquals(expectedMinBed, actualMinBed);
        verify(preparedStatement, times(1)).executeQuery();
    }

    @Test
    void testGetMaxBed_ThrowsGeneralException_WhenSQLException() throws Exception {
        // Arrange
        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

        // Act & Assert
        Exception exception = assertThrows(GeneralException.class, () -> homeDAO.getMaxBed());
        assertEquals("Error get max bed in the database: Database error", exception.getMessage());
    }

    @Test
    void testGetMinBath_ReturnsMinBath() throws Exception {
        // Arrange
        int expectedMinBath = 2;

        // Mock result set to return the expected value
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("numOfBath")).thenReturn(expectedMinBath);

        // Act
        int actualMinBed = homeDAO.getMinBath();

        // Assert
        assertEquals(expectedMinBath, actualMinBed);

        // Verify interactions
        verify(preparedStatement).executeQuery();
        verify(resultSet).next();
        verify(resultSet).getInt("numOfBath");
    }

    @Test
    void testGetMinBath_ReturnZero_WhenNoResult() throws Exception {
        int expectedMinBath = 0;

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        int actualMinBed = homeDAO.getMinBath();
        assertEquals(expectedMinBath, actualMinBed);
        verify(preparedStatement, times(1)).executeQuery();
    }

    @Test
    void testGetMinBath_ThrowsGeneralException_WhenSQLException() throws Exception {
        // Arrange
        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

        // Act & Assert
        Exception exception = assertThrows(GeneralException.class, () -> homeDAO.getMinBath());
        assertEquals("Error get min bath in the database: Database error", exception.getMessage());
    }

    @Test
    void testGetMaxBath_ReturnsMaxBath() throws Exception {
        // Arrange
        int expectedMaxBath = 2;

        // Mock result set to return the expected value
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("numOfBath")).thenReturn(expectedMaxBath);

        // Act
        int actualMinBed = homeDAO.getMaxBath();

        // Assert
        assertEquals(expectedMaxBath, actualMinBed);

        // Verify interactions
        verify(preparedStatement).executeQuery();
        verify(resultSet).next();
        verify(resultSet).getInt("numOfBath");
    }

    @Test
    void testGetMaxBath_ReturnZero_WhenNoResult() throws Exception {
        int expectedMaxBath = 0;

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        int actualMinBed = homeDAO.getMaxBath();
        assertEquals(expectedMaxBath, actualMinBed);
        verify(preparedStatement, times(1)).executeQuery();
    }

    @Test
    void testGetMaxBath_ThrowsGeneralException_WhenSQLException() throws Exception {
        // Arrange
        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

        // Act & Assert
        Exception exception = assertThrows(GeneralException.class, () -> homeDAO.getMaxBath());
        assertEquals("Error get max bath in the database: Database error", exception.getMessage());
    }
    @Test
    void testGetSearchedHomes_noResults() throws Exception {
        // Giả lập prepareStatement và executeQuery
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        // Giả lập resultSet không có bản ghi nào
        when(resultSet.next()).thenReturn(false);

        // Mock các tham số tìm kiếm
        Map<String, Object> searchParams = new HashMap<>();
        searchParams.put("keyword", "NonExistent");

        // Gọi phương thức cần kiểm tra
        List<Home> homes = homeDAO.getSearchedHomes(searchParams);

        // Kiểm tra danh sách homes phải rỗng
        assertTrue(homes.isEmpty());

        // Xác minh rằng executeQuery() đã được gọi
        verify(preparedStatement, times(1)).executeQuery();

        // Xác minh rằng next() chỉ được gọi đúng một lần
        verify(resultSet, times(1)).next();
    }

    @Test
    void testGetSearchedHomes_withResults() throws Exception {
        // Giả lập prepareStatement và executeQuery
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        // Giả lập resultSet trả về dữ liệu
        when(resultSet.next()).thenReturn(true, false);  // Chỉ có 1 bản ghi
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("name")).thenReturn("Sample Home");
        when(resultSet.getString("address")).thenReturn("123 Main St");
        when(resultSet.getBigDecimal("area")).thenReturn(BigDecimal.valueOf(100.5));
        when(resultSet.getTimestamp("createdDate")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));
        when(resultSet.getInt("priceId")).thenReturn(1001);
        when(resultSet.getInt("price")).thenReturn(1500);

        // Mock các tham số tìm kiếm
        Map<String, Object> searchParams = new HashMap<>();
        searchParams.put("keyword", "Sample");
        searchParams.put("minPrice", 1000);
        searchParams.put("maxPrice", 2000);

        // Gọi phương thức cần kiểm tra
        List<Home> homes = homeDAO.getSearchedHomes(searchParams);

        // Kiểm tra danh sách homes có kết quả
        assertNotNull(homes);
        assertEquals(1, homes.size());

        // Kiểm tra chi tiết của Home được trả về
        Home home = homes.get(0);
        assertEquals(1, home.getId());
        assertEquals("Sample Home", home.getName());
        assertEquals("123 Main St", home.getAddress());
        assertEquals(BigDecimal.valueOf(100.5), home.getArea());
        assertNotNull(home.getCreatedDate());
        assertEquals(1001, home.getPriceId());
        assertEquals(1500, home.getPrice());

        // Xác minh rằng executeQuery() đã được gọi
        verify(preparedStatement, times(1)).executeQuery();

        // Xác minh rằng next() đã được gọi đúng hai lần (một lần true, một lần false)
        verify(resultSet, times(2)).next();
    }

    // Test với SQLException
    @Test
    void testGetSearchedHomes_SQLException() throws SQLException{
        // Given
        Map<String, Object> searchParams = new HashMap<>();

        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

        // When / Then
        assertThrows(SQLException.class, () -> {
            homeDAO.getSearchedHomes(searchParams);
        });
    }

    // Test với ClassNotFoundException
    @Test
    void testGetSearchedHomes_ClassNotFoundException() throws SQLException, IOException, ClassNotFoundException {
        // Giả lập ClassNotFoundException khi gọi DBContext.getConnection
        when(DBContext.getConnection()).thenThrow(new ClassNotFoundException("JDBC Driver not found"));

        // When / Then
        assertThrows(ClassNotFoundException.class, () -> {
            homeDAO.getSearchedHomes(new HashMap<>());
        });
    }

    // 1. Test happy path - có hình ảnh trả về
    @Test
    void testFetchFirstImage_HappyPath() throws SQLException{
        // Given
        int homeId = 1;
        String expectedImgUrl = "http://example.com/image1.jpg";

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("imgUrl")).thenReturn(expectedImgUrl);

        // When
        String result = homeDAO.fetchFirstImage(homeId);

        // Then
        assertNotNull(result);
        assertEquals(expectedImgUrl, result);

        // Verify SQL params were set correctly
        verify(preparedStatement).setInt(1, homeId);
    }

    // 2. Test khi không tìm thấy hình ảnh
    @Test
    void testFetchFirstImage_NoImageFound() throws SQLException{
        // Given
        int homeId = 1;

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);  // Không có kết quả

        // When
        String result = homeDAO.fetchFirstImage(homeId);

        // Then
        assertNull(result);  // Không có ảnh nào được tìm thấy
    }

    // 3. Test với SQLException
    @Test
    void testFetchFirstImage_SQLException() throws SQLException{
        // Given
        int homeId = 1;

        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

        // When / Then
        GeneralException exception = assertThrows(GeneralException.class, () -> {
            homeDAO.fetchFirstImage(homeId);
        });
        assertEquals("Error finding image in the database: Database error", exception.getMessage());
    }

    // 4. Test với ClassNotFoundException
    @Test
    void testFetchFirstImage_ClassNotFoundException() throws SQLException, IOException, ClassNotFoundException {
        // Given
        int homeId = 1;

        when(DBContext.getConnection()).thenThrow(new ClassNotFoundException("JDBC Driver not found"));

        // When / Then
        GeneralException exception = assertThrows(GeneralException.class, () -> {
            homeDAO.fetchFirstImage(homeId);
        });
        assertEquals("Error finding image in the database: JDBC Driver not found", exception.getMessage());
    }

    @Test
    void testNumOfHome_NoSearchParams() throws Exception {
        // Given
        Map<String, Object> searchParams = new HashMap<>();

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(5);

        // When
        int result = homeDAO.numOfHome(searchParams);

        // Then
        assertEquals(5, result);

        verify(preparedStatement, times(1)).executeQuery();
    }

    @Test
    void testNumOfHome_WithSearchParams() throws Exception {
        // Given
        Map<String, Object> searchParams = new HashMap<>();
        searchParams.put("keyword", "cozy");
        searchParams.put("minPrice", 100);
        searchParams.put("maxPrice", 500);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(3);

        // When
        int result = homeDAO.numOfHome(searchParams);

        // Then
        assertEquals(3, result);
        verify(preparedStatement, times(1)).setString(1, "%cozy%");
        verify(preparedStatement, times(1)).setInt(3, 100);
        verify(preparedStatement, times(1)).setInt(4, 500);
        verify(preparedStatement, times(1)).executeQuery();
    }
}
