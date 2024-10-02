package com.homesharing.dao;

import com.homesharing.conf.DBContext;
import com.homesharing.dao.impl.PreferenceDAOImpl;
import com.homesharing.dao.impl.UserDAOImpl;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.Preference;
import org.eclipse.tags.shaded.org.apache.xpath.res.XPATHErrorResources_de;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PreferenceDAOImplTest {
    private PreferenceDAOImpl preferenceDao;

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
        preferenceDao = new PreferenceDAOImpl();
    }

    @AfterEach
    public void tearDown() {
        // Close static mock after each test
        mockedDBContext.close();
    }

    @Test
    public void testGetPreference_Success() throws SQLException {
        int userId = 1;

        // Mock ResultSet để trả về các giá trị giả lập cho Preference
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next()).thenReturn(true); // Trả về true để giả lập rằng có một dòng trong kết quả
        when(resultSet.getInt("cleanliness")).thenReturn(5);
        when(resultSet.getInt("drinking")).thenReturn(3);
        when(resultSet.getInt("smoking")).thenReturn(4);
        when(resultSet.getInt("interaction")).thenReturn(2);
        when(resultSet.getInt("cooking")).thenReturn(5);
        when(resultSet.getInt("pet")).thenReturn(1);

        // Thiết lập mock cho PreparedStatement và Connection
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);

        // Gọi phương thức cần kiểm tra
        Preference preference = preferenceDao.getPreference(userId);

        // Kiểm tra kết quả
        assertNotNull(preference);
        assertEquals(5, preference.getCleanliness());
        assertEquals(3, preference.getDrinking());
        assertEquals(4, preference.getSmoking());
        assertEquals(2, preference.getInteraction());
        assertEquals(5, preference.getCooking());
        assertEquals(1, preference.getPet());

        // Xác minh rằng các phương thức được gọi đúng cách
        Mockito.verify(preparedStatement).setInt(1, userId);
        Mockito.verify(preparedStatement).executeQuery();
        Mockito.verify(resultSet).next();
    }

    @Test
    public void testGetPreference_NoPreferenceFound() throws SQLException {
        int userId = 1;

        // Mock ResultSet để trả về false, giả lập rằng không có dòng nào trong kết quả
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next()).thenReturn(false); // Trả về false

        // Thiết lập mock cho PreparedStatement và Connection
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);

        // Gọi phương thức cần kiểm tra
        Preference preference = preferenceDao.getPreference(userId);

        // Kiểm tra kết quả phải là null vì không tìm thấy kết quả
        assertNull(preference);

        // Xác minh rằng các phương thức được gọi đúng cách
        Mockito.verify(preparedStatement).setInt(1, userId);
        Mockito.verify(preparedStatement).executeQuery();
        Mockito.verify(resultSet).next();
    }

    @Test
    public void testGetPreference_SQLException() throws SQLException {
        int userId = 1;

        // Thiết lập hành vi của PreparedStatement để ném ra SQLException
        when(preparedStatement.executeQuery()).thenThrow(new SQLException("Database error"));
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);

        // Kiểm tra xem ngoại lệ RuntimeException có được ném ra không
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            preferenceDao.getPreference(userId);
        });

        // Kiểm tra thông điệp ngoại lệ
        assertTrue(exception.getMessage().contains("Error user existence in the database"));

        // Xác minh rằng các phương thức được gọi đúng cách
        Mockito.verify(preparedStatement).setInt(1, userId);
        Mockito.verify(preparedStatement).executeQuery();
    }

    @Test
    public void testUpdatePreference_Success() throws SQLException {
        Preference preference = new Preference();
        preference.setCleanliness(5);
        preference.setSmoking(3);
        preference.setDrinking(2);
        preference.setInteraction(4);
        preference.setCooking(1);
        preference.setPet(0);
        preference.setUserId(1);

        // Giả lập việc executeUpdate trả về 1 dòng được cập nhật
        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);

        // Gọi phương thức cần kiểm tra
        int rowsUpdated = preferenceDao.updatePreference(preference);

        // Kiểm tra kết quả, rowsUpdated phải là 1
        assertEquals(1, rowsUpdated);

        // Xác minh rằng các phương thức đã được gọi đúng với các tham số tương ứng
        Mockito.verify(preparedStatement).setInt(1, preference.getCleanliness());
        Mockito.verify(preparedStatement).setInt(2, preference.getSmoking());
        Mockito.verify(preparedStatement).setInt(3, preference.getDrinking());
        Mockito.verify(preparedStatement).setInt(4, preference.getInteraction());
        Mockito.verify(preparedStatement).setInt(5, preference.getCooking());
        Mockito.verify(preparedStatement).setInt(6, preference.getPet());
        Mockito.verify(preparedStatement).setInt(7, preference.getUserId());
        Mockito.verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testUpdatePreference_NoRowsUpdated() throws SQLException {
        Preference preference = new Preference();
        preference.setCleanliness(5);
        preference.setSmoking(3);
        preference.setDrinking(2);
        preference.setInteraction(4);
        preference.setCooking(1);
        preference.setPet(0);
        preference.setUserId(1);

        // Giả lập việc executeUpdate trả về 0 dòng được cập nhật
        when(preparedStatement.executeUpdate()).thenReturn(0);
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);

        // Gọi phương thức cần kiểm tra
        int rowsUpdated = preferenceDao.updatePreference(preference);

        // Kiểm tra kết quả, rowsUpdated phải là 0
        assertEquals(0, rowsUpdated);

        // Xác minh rằng các phương thức đã được gọi đúng với các tham số tương ứng
        Mockito.verify(preparedStatement).setInt(1, preference.getCleanliness());
        Mockito.verify(preparedStatement).setInt(2, preference.getSmoking());
        Mockito.verify(preparedStatement).setInt(3, preference.getDrinking());
        Mockito.verify(preparedStatement).setInt(4, preference.getInteraction());
        Mockito.verify(preparedStatement).setInt(5, preference.getCooking());
        Mockito.verify(preparedStatement).setInt(6, preference.getPet());
        Mockito.verify(preparedStatement).setInt(7, preference.getUserId());
        Mockito.verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testUpdatePreference_SQLException() throws SQLException {
        Preference preference = new Preference();
        preference.setCleanliness(5);
        preference.setSmoking(3);
        preference.setDrinking(2);
        preference.setInteraction(4);
        preference.setCooking(1);
        preference.setPet(0);
        preference.setUserId(1);

        // Giả lập SQLException
        when(preparedStatement.executeUpdate()).thenThrow(new SQLException("Database error"));
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);

        // Kiểm tra ngoại lệ GeneralException được ném ra khi có lỗi SQL
        GeneralException exception = assertThrows(GeneralException.class, () -> {
            preferenceDao.updatePreference(preference);
        });

        // Kiểm tra thông điệp ngoại lệ
        assertTrue(exception.getMessage().contains("Error update user profile"));

        // Xác minh rằng các phương thức đã được gọi đúng với các tham số tương ứng
        Mockito.verify(preparedStatement).setInt(1, preference.getCleanliness());
        Mockito.verify(preparedStatement).setInt(2, preference.getSmoking());
        Mockito.verify(preparedStatement).setInt(3, preference.getDrinking());
        Mockito.verify(preparedStatement).setInt(4, preference.getInteraction());
        Mockito.verify(preparedStatement).setInt(5, preference.getCooking());
        Mockito.verify(preparedStatement).setInt(6, preference.getPet());
        Mockito.verify(preparedStatement).setInt(7, preference.getUserId());
        Mockito.verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testInsertPreference_Success() throws SQLException {
        Preference preference = new Preference();
        preference.setCleanliness(5);
        preference.setSmoking(3);
        preference.setDrinking(2);
        preference.setInteraction(4);
        preference.setCooking(1);
        preference.setPet(0);
        preference.setUserId(1);

        // Giả lập việc executeUpdate trả về 1 dòng được thêm vào
        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(connection.prepareStatement(any(String.class), eq(PreparedStatement.RETURN_GENERATED_KEYS)))
                .thenReturn(preparedStatement);

        // Gọi phương thức cần kiểm tra
        int affectedRows = preferenceDao.insertPreference(preference);

        // Kiểm tra kết quả, affectedRows phải là 1
        assertEquals(1, affectedRows);

        // Xác minh rằng các phương thức đã được gọi đúng với các tham số tương ứng
        Mockito.verify(preparedStatement).setInt(1, preference.getCleanliness());
        Mockito.verify(preparedStatement).setInt(2, preference.getSmoking());
        Mockito.verify(preparedStatement).setInt(3, preference.getDrinking());
        Mockito.verify(preparedStatement).setInt(4, preference.getInteraction());
        Mockito.verify(preparedStatement).setInt(5, preference.getCooking());
        Mockito.verify(preparedStatement).setInt(6, preference.getPet());
        Mockito.verify(preparedStatement).setInt(7, preference.getUserId());
        Mockito.verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testInsertPreference_NoRowsInserted() throws SQLException {
        Preference preference = new Preference();
        preference.setCleanliness(5);
        preference.setSmoking(3);
        preference.setDrinking(2);
        preference.setInteraction(4);
        preference.setCooking(1);
        preference.setPet(0);
        preference.setUserId(1);

        // Giả lập việc executeUpdate trả về 0 dòng được thêm vào
        when(preparedStatement.executeUpdate()).thenReturn(0);
        when(connection.prepareStatement(any(String.class), eq(PreparedStatement.RETURN_GENERATED_KEYS)))
                .thenReturn(preparedStatement);

        // Gọi phương thức cần kiểm tra
        int affectedRows = preferenceDao.insertPreference(preference);

        // Kiểm tra kết quả, affectedRows phải là 0
        assertEquals(0, affectedRows);

        // Xác minh rằng các phương thức đã được gọi đúng với các tham số tương ứng
        Mockito.verify(preparedStatement).setInt(1, preference.getCleanliness());
        Mockito.verify(preparedStatement).setInt(2, preference.getSmoking());
        Mockito.verify(preparedStatement).setInt(3, preference.getDrinking());
        Mockito.verify(preparedStatement).setInt(4, preference.getInteraction());
        Mockito.verify(preparedStatement).setInt(5, preference.getCooking());
        Mockito.verify(preparedStatement).setInt(6, preference.getPet());
        Mockito.verify(preparedStatement).setInt(7, preference.getUserId());
        Mockito.verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testInsertPreference_SQLException() throws SQLException {
        Preference preference = new Preference();
        preference.setCleanliness(5);
        preference.setSmoking(3);
        preference.setDrinking(2);
        preference.setInteraction(4);
        preference.setCooking(1);
        preference.setPet(0);
        preference.setUserId(1);

        // Giả lập SQLException
        when(preparedStatement.executeUpdate()).thenThrow(new SQLException("Database error"));
        when(connection.prepareStatement(any(String.class), eq(PreparedStatement.RETURN_GENERATED_KEYS)))
                .thenReturn(preparedStatement);

        // Kiểm tra ngoại lệ RuntimeException được ném ra khi có lỗi SQL
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            preferenceDao.insertPreference(preference);
        });

        // Kiểm tra thông điệp ngoại lệ
        assertTrue(exception.getMessage().contains("Error saving preferences to the database"));

        // Xác minh rằng các phương thức đã được gọi đúng với các tham số tương ứng
        Mockito.verify(preparedStatement).setInt(1, preference.getCleanliness());
        Mockito.verify(preparedStatement).setInt(2, preference.getSmoking());
        Mockito.verify(preparedStatement).setInt(3, preference.getDrinking());
        Mockito.verify(preparedStatement).setInt(4, preference.getInteraction());
        Mockito.verify(preparedStatement).setInt(5, preference.getCooking());
        Mockito.verify(preparedStatement).setInt(6, preference.getPet());
        Mockito.verify(preparedStatement).setInt(7, preference.getUserId());
        Mockito.verify(preparedStatement).executeUpdate();
    }
}
