package com.homesharing.dao;

import com.homesharing.conf.DBContext;
import com.homesharing.dao.impl.DistrictDAOImpl;
import com.homesharing.model.District;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class DistrictDAOImplTest {
    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @Mock
    private ResultSet mockResultSet;

    private DistrictDAOImpl districtDAO;

    @BeforeEach
    void setUp() throws SQLException, IOException, ClassNotFoundException {
        MockitoAnnotations.openMocks(this);
        mockConnection = mock(Connection.class);
        districtDAO = new DistrictDAOImpl();

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
    }

    @Test
    void testGetAllDistricts() throws SQLException {
        // Arrange
        when(mockResultSet.next()).thenReturn(true, true, true);
        when(mockResultSet.getInt("id")).thenReturn(1, 2);
        when(mockResultSet.getString("name")).thenReturn("Quận Ba Đình", "Quận Hoàn Kiếm");
        when(mockResultSet.getString("status")).thenReturn("active", "active");

        // Act
        List<District> districts = districtDAO.getAllDistricts();

        // Assert
        assertEquals(709, districts.size());
        assertEquals(1, districts.get(0).getId());
        assertEquals("Quận Ba Đình", districts.get(0).getName());
        assertEquals("active", districts.get(0).getStatus());
        assertEquals(2, districts.get(1).getId());
        assertEquals("Quận Hoàn Kiếm", districts.get(1).getName());
        assertEquals("active", districts.get(1).getStatus());
    }

    @Test
    void testGetDistrictById() throws SQLException {
        // Arrange
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(24);
        when(mockResultSet.getString("name")).thenReturn("Huyện Thạch Thất");
        when(mockResultSet.getString("status")).thenReturn("active");

        // Act
        District district = districtDAO.getDistrictById(24);

        // Assert
        assertNotNull(district);
        assertEquals(24, district.getId());
        assertEquals("Huyện Thạch Thất", district.getName());
        assertEquals("active", district.getStatus());
    }

    @Test
    void testGetDistrictByProvinceId() throws SQLException {
        // Arrange
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt("id")).thenReturn(1, 2);
        when(mockResultSet.getString("name")).thenReturn("Quận Ba Đình", "Quận Hoàn Kiếm");

        // Act
        List<District> districts = districtDAO.getDistrictByProvinceId(1);

        // Assert
        assertEquals(30, districts.size());
        assertEquals(1, districts.get(0).getId());
        assertEquals("Quận Ba Đình", districts.get(0).getName());
        assertEquals(2, districts.get(1).getId());
        assertEquals("Quận Hoàn Kiếm", districts.get(1).getName());
    }

    @Test
    void testGetAllDistricts_EmptyResultSet() throws SQLException {
        // Arrange
        when(mockResultSet.next()).thenReturn(false);

        // Act
        List<District> districts = districtDAO.getAllDistricts();

        // Assert
        assertNotNull(districts);
        assertEquals(709, districts.size());
    }

    @Test
    void testGetDistrictByProvinceId_InvalidProvinceId() throws SQLException {
        // Arrange
        when(mockResultSet.next()).thenReturn(false);

        // Act
        List<District> districts = districtDAO.getDistrictByProvinceId(-1);

        // Assert
        assertNotNull(districts);
        assertEquals(0, districts.size());
    }
}