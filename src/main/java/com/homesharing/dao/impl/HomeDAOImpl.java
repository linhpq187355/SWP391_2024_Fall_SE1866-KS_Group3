/*
 * Copyright(C) 2024, Roomify Inc.
 * Roomify: Roommate Matching and Home Sharing Service
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-10-01      1.0              Pham Quang Linh     First Implement
 * 2024-10-10      2.0              Pham Quang Linh     Second Implement
 * 2024-10-10      1.0                 ManhNC            update some method
 * 2024-10-20      1.0                 ManhNC         Implement search house method
 * 2024-10-30      1.0                 ThangLT          Change sql query in getSearchedHome to fix duplicate issues
 * 2024-10-30      1.0              Pham Quang Linh     Add method
 */
package com.homesharing.dao.impl;

import com.homesharing.conf.DBContext;
import com.homesharing.dao.HomeDAO;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.Appointment;
import com.homesharing.model.Home;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of the HomeDAO interface for interacting with the Homes database.
 * This class provides methods for CRUD operations and querying homes.
 */
public class HomeDAOImpl extends DBContext implements HomeDAO {
    // Logger for logging errors or information
    private static final Logger logger = Logger.getLogger(HomeDAOImpl.class.getName());
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PreferenceDAOImpl.class);

    /**
     * Update home info
     *
     * @param home
     * @return home id indicate the task is success or not
     */
    public int updateHome(Home home) {
        if (home == null || home.getId() <= 0) {
            throw new IllegalArgumentException("Invalid homeId: " + home.getId());
        }

        List<String> updateColumns = new ArrayList<>();
        List<Object> updateValues = new ArrayList<>();

        if (home.getName() != null) {
            updateColumns.add("name = ?");
            updateValues.add(home.getName());
        }
        if (home.getAddress() != null) {
            updateColumns.add("address = ?");
            updateValues.add(home.getAddress());
        }
        if (home.getLongitude() != null && home.getLatitude() != null) {
            updateColumns.add("latitude = ?");
            updateColumns.add("longitude = ?");
            updateValues.add(home.getLatitude());
            updateValues.add(home.getLongitude());
        }
        if (home.getOrientation() != null) {
            updateColumns.add("orientation = ?");
            updateValues.add(home.getOrientation());
        }
        if (home.getArea() != null) {
            updateColumns.add("area = ?");
            updateValues.add(home.getArea());
        }
        if (home.getLeaseDuration() > 0) {
            updateColumns.add("leaseDuration = ?");
            updateValues.add(home.getLeaseDuration());
        }
        if (home.getMoveInDate() != null) {
            updateColumns.add("moveInDate = ?");
            updateValues.add(home.getMoveInDate());
        }
        if (home.getNumOfBedroom() > 0) {
            updateColumns.add("numOfBedroom = ?");
            updateValues.add(home.getNumOfBedroom());
        }
        if (home.getNumOfBath() > 0) {
            updateColumns.add("numOfBath = ?");
            updateValues.add(home.getNumOfBath());
        }
        if (home.getHomeDescription() != null) {
            updateColumns.add("homeDescription = ?");
            updateValues.add(home.getHomeDescription());
        }
        if (home.getTenantDescription() != null) {
            updateColumns.add("tenantDescription = ?");
            updateValues.add(home.getTenantDescription());
        }
        if (home.getModifiedDate() != null) {
            updateColumns.add("modifiedDate = ?");
            updateValues.add(java.sql.Timestamp.valueOf(home.getModifiedDate()));
        }

        if (updateColumns.isEmpty()) {
            throw new IllegalArgumentException("No fields to update for homeId: " + home.getId());
        }

        String updateColumnsStr = String.join(", ", updateColumns);
        String sql = "UPDATE Homes SET " + updateColumnsStr + " WHERE id = ?";

        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            int paramIndex = 1;
            for (Object value : updateValues) {
                preparedStatement.setObject(paramIndex++, value);
            }
            preparedStatement.setInt(paramIndex, home.getId());

            int affectedRows = preparedStatement.executeUpdate();
            return home.getId();

        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new IllegalArgumentException("Error updating home to the database: " + e.getMessage(), e);
        }
    }


    /**
     * Change home status
     *
     * @param homeId
     * @param status
     * @return home id indicate the success of the task or not
     */
    @Override
    public int changeStatus(int homeId, String status) {
        String[] allowedStatuses = {"active", "inactive", "pending", "sold"};
        if (!Arrays.asList(allowedStatuses).contains(status)) {
            throw new IllegalArgumentException("Invalid status. Status must be one of: " + Arrays.toString(allowedStatuses));
        }
        String sql = "UPDATE [dbo].[Homes] SET [status] = ? WHERE id = ?";

        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            int paramIndex = 1;
            preparedStatement.setString(paramIndex, status);
            preparedStatement.setInt(++paramIndex, homeId);

            // Execute the update statement and capture affected rows
            int affectedRows = preparedStatement.executeUpdate();

            return affectedRows;

        } catch (SQLException | IOException | ClassNotFoundException e) {
            // Re-throw as runtime exception to be handled by the service layer
            throw new IllegalArgumentException("Error updating home to the database: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Home> getAllHomes() {
        List<Home> homes = new ArrayList<>();
        String sql = "SELECT [id], [name], [address], [longitude], [latitude], [orientation], [area], [leaseDuration], [moveInDate], [numOfBedroom], [numOfBath], [createdDate], [modifiedDate], [homeDescription], [tenantDescription], [status],[wardsId], [homeTypeId], [createdBy] FROM [dbo].[Homes]";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Home home = new Home();
                home.setId(resultSet.getInt("id"));
                home.setName(resultSet.getString("name"));
                home.setAddress(resultSet.getString("address"));
                home.setLongitude(resultSet.getBigDecimal("longitude"));
                home.setLatitude(resultSet.getBigDecimal("latitude"));
                home.setOrientation(resultSet.getString("orientation"));
                home.setArea(resultSet.getBigDecimal("area"));
                home.setLeaseDuration(resultSet.getInt("leaseDuration"));
                home.setMoveInDate(resultSet.getDate("moveInDate").toLocalDate());
                home.setNumOfBedroom(resultSet.getInt("numOfBedroom"));
                home.setNumOfBath(resultSet.getInt("numOfBath"));
                home.setCreatedDate(resultSet.getTimestamp("createdDate").toLocalDateTime());
                home.setModifiedDate(resultSet.getTimestamp("modifiedDate") != null ? resultSet.getTimestamp("modifiedDate").toLocalDateTime() : null);
                home.setHomeDescription(resultSet.getString("homeDescription"));
                home.setTenantDescription(resultSet.getString("tenantDescription"));
                home.setStatus(resultSet.getString("status"));
                home.setWardId(resultSet.getInt("wardsId"));
                home.setHomeTypeId(resultSet.getInt("homeTypeId"));
                home.setCreatedBy(resultSet.getInt("createdBy"));

                homes.add(home);
            }

        } catch (SQLException | IOException | ClassNotFoundException e) {
            // Throwing exception to the upper layer to handle it properly
            throw new GeneralException("Error retrieving homes from the database: " + e.getMessage(), e);
        } finally {
            // Closing resources in reverse order of opening
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
        return homes;
    }

    /**
     * Retrieves homes based on search parameters.
     *
     * @param searchParams A map containing search criteria.
     * @return A list of homes matching the criteria.
     * @throws SQLException           If a database access error occurs.
     * @throws IOException            If an I/O error occurs.
     * @throws ClassNotFoundException If the JDBC driver class cannot be found.
     */
    @Override
    public List<Home> getSearchedHomes(Map<String, Object> searchParams) throws SQLException {
        List<Home> homeList = new ArrayList<>();

        StringBuilder sql = new StringBuilder(
                "WITH LatestPrices AS (" +
                        "    SELECT id AS priceId, price, Homesid, " +
                        "           ROW_NUMBER() OVER(PARTITION BY Homesid ORDER BY id DESC) AS row_num " +
                        "    FROM Prices" +
                        ") " +
                        "SELECT DISTINCT h.*, pri.priceId, pri.price " +
                        "FROM Homes h " +
                        "JOIN Wards w ON h.wardsId = w.id " +
                        "JOIN Districts d ON w.Districtsid = d.id " +
                        "JOIN Provinces p ON d.provincesId = p.id " +
                        "LEFT JOIN LatestPrices pri ON h.id = pri.Homesid AND pri.row_num = 1 " +
                        "WHERE 1=1"
        );

        // Condition for keyword
        if (searchParams.containsKey("keyword")) {
            sql.append(" AND (h.name LIKE ? OR h.homeDescription LIKE ?)");
        }

        // Condition for homeType
        if (searchParams.containsKey("homeType")) {
            sql.append(" AND h.homeTypeId = ?");
        }

        // Condition for province, district, ward
        if (searchParams.containsKey("province")) {
            sql.append(" AND p.id = ?");
        }
        if (searchParams.containsKey("district")) {
            sql.append(" AND d.id = ?");
        }
        if (searchParams.containsKey("ward")) {
            sql.append(" AND w.id = ?");
        }

        // Condition for priceRange
        if (searchParams.containsKey("minPrice") && searchParams.containsKey("maxPrice")) {
            sql.append(" AND pri.price BETWEEN ? AND ?");
        }

        // Condition for areaRange
        if (searchParams.containsKey("minArea") && searchParams.containsKey("maxArea")) {
            sql.append(" AND h.area BETWEEN ? AND ?");
        }

        // Condition for bathRange
        if (searchParams.containsKey("minBath") && searchParams.containsKey("maxBath")) {
            sql.append(" AND h.numOfBath BETWEEN ? AND ?");
        }

        // Condition for bedRange
        if (searchParams.containsKey("minBed") && searchParams.containsKey("maxBed")) {
            sql.append(" AND h.numOfBedroom BETWEEN ? AND ?");
        }

        // Condition for amenities (using COUNT to check if all are available)
        if (searchParams.containsKey("amenities")) {
            String[] amenities = (String[]) searchParams.get("amenities");
            sql.append(" AND (SELECT COUNT(1) FROM Amenities_Homes ah JOIN Amenities a ON ah.amenitiesId = a.id");
            sql.append(" WHERE ah.homesId = h.id AND a.id IN (");
            sql.append(String.join(",", Collections.nCopies(amenities.length, "?")));
            sql.append(")) = ").append(amenities.length);
        }

        // Condition for fireEquipments (using COUNT to check if all are available)
        if (searchParams.containsKey("fireEquipments")) {
            String[] fireEquipments = (String[]) searchParams.get("fireEquipments");
            sql.append(" AND (SELECT COUNT(1) FROM FireEquipments_Homes feh JOIN FireEquipments fe ON feh.fireEquipmentsId = fe.id");
            sql.append(" WHERE feh.homesId = h.id AND fe.id IN (");
            sql.append(String.join(",", Collections.nCopies(fireEquipments.length, "?")));
            sql.append(")) = ").append(fireEquipments.length);
        }

        // Condition for createdBy
        if (searchParams.containsKey("createdBy")) {
            sql.append(" AND h.createdBy = ?");
        }

        if (searchParams.containsKey("status")) {
            sql.append(" AND h.status = ?");
        }

        if (searchParams.containsKey("orderby")) {
            String orderby = (String) searchParams.get("orderby");
            String order = (String) searchParams.get("order");

            if ("property_date".equals(orderby)) {
                sql.append(" ORDER BY h.createdDate ");
            } else if ("property_price".equals(orderby)) {
                sql.append(" ORDER BY pri.price ");
            }

            // Add the order
            sql.append(order);
        } else {
            sql.append(" ORDER BY h.createdDate ");
        }

        // Add pagination conditions
        if (searchParams.containsKey("per_page")) {
            sql.append(" OFFSET ? ROWS FETCH NEXT ? ROWS ONLY ");
        }

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            connection = DBContext.getConnection();
            ps = connection.prepareStatement(sql.toString());
            int paramIndex = 1;

            if (searchParams.containsKey("keyword")) {
                ps.setString(paramIndex++, "%" + searchParams.get("keyword") + "%");
                ps.setString(paramIndex++, "%" + searchParams.get("keyword") + "%");
            }
            if (searchParams.containsKey("homeType")) {
                int homeTypeId = Integer.parseInt(searchParams.get("homeType").toString());
                ps.setInt(paramIndex++, homeTypeId);
            }
            if (searchParams.containsKey("province")) {
                int provinceId = Integer.parseInt(searchParams.get("province").toString());
                ps.setInt(paramIndex++, provinceId);
            }
            if (searchParams.containsKey("district")) {
                int districtId = Integer.parseInt(searchParams.get("district").toString());
                ps.setInt(paramIndex++, districtId);
            }
            if (searchParams.containsKey("ward")) {
                int wardId = Integer.parseInt(searchParams.get("ward").toString());
                ps.setInt(paramIndex++, wardId);
            }
            if (searchParams.containsKey("minPrice") && searchParams.containsKey("maxPrice")) {
                int minPrice = Integer.parseInt(searchParams.get("minPrice").toString());
                int maxPrice = Integer.parseInt(searchParams.get("maxPrice").toString());
                ps.setInt(paramIndex++, minPrice);
                ps.setInt(paramIndex++, maxPrice);
            }
            if (searchParams.containsKey("minArea") && searchParams.containsKey("maxArea")) {
                BigDecimal minArea = BigDecimal.ZERO; // Giá trị mặc định
                BigDecimal maxArea = new BigDecimal(600); // Giá trị mặc định

                if (searchParams.get("minArea") != null) {
                    minArea = new BigDecimal(searchParams.get("minArea").toString());
                }

                if (searchParams.get("maxArea") != null) {
                    maxArea = new BigDecimal(searchParams.get("maxArea").toString());
                }

                ps.setBigDecimal(paramIndex++, minArea);
                ps.setBigDecimal(paramIndex++, maxArea);
            }

            if (searchParams.containsKey("minBath") && searchParams.containsKey("maxBath")) {
                int minBath = Integer.parseInt(searchParams.get("minBath").toString());
                int maxBath = Integer.parseInt(searchParams.get("maxBath").toString());
                ps.setInt(paramIndex++, minBath);
                ps.setInt(paramIndex++, maxBath);
            }
            if (searchParams.containsKey("minBed") && searchParams.containsKey("maxBed")) {
                int minBed = Integer.parseInt(searchParams.get("minBed").toString());
                int maxBed = Integer.parseInt(searchParams.get("maxBed").toString());
                ps.setInt(paramIndex++, minBed);
                ps.setInt(paramIndex++, maxBed);
            }
            if (searchParams.containsKey("amenities")) {
                String[] amenities = (String[]) searchParams.get("amenities");
                for (String amenity : amenities) {
                    int numAmen = Integer.parseInt(amenity);
                    ps.setInt(paramIndex++, numAmen);
                }
            }
            if (searchParams.containsKey("fireEquipments")) {
                String[] fireEquipments = (String[]) searchParams.get("fireEquipments");
                for (String fireEquipment : fireEquipments) {
                    int fireEid = Integer.parseInt(fireEquipment);
                    ps.setInt(paramIndex++, fireEid);
                }
            }

            if (searchParams.containsKey("createdBy")) {
                int createdBy = Integer.parseInt(searchParams.get("createdBy").toString());
                ps.setInt(paramIndex++, createdBy);
            }

            if (searchParams.containsKey("status")) {
                String status = searchParams.get("status").toString();
                ps.setString(paramIndex++, status);
            }

            if (searchParams.containsKey("per_page")) {
                int perPage = Integer.parseInt(searchParams.get("per_page").toString());
                int targetPage = searchParams.containsKey("targetPage")
                        ? Integer.parseInt(searchParams.get("targetPage").toString())
                        : 1;

                int offset = (targetPage - 1) * perPage;
                ps.setInt(paramIndex++, offset);
                ps.setInt(paramIndex, perPage);
            }

            resultSet = ps.executeQuery();

            while (resultSet.next()) {
                Home home = new Home();
                home.setId(resultSet.getInt("id"));
                home.setName(resultSet.getString("name"));
                home.setAddress(resultSet.getString("address"));
                home.setArea(resultSet.getBigDecimal("area"));
                home.setHomeDescription(resultSet.getString("homeDescription"));
                home.setTenantDescription(resultSet.getString("tenantDescription"));
                home.setCreatedDate(resultSet.getTimestamp("createdDate").toLocalDateTime());
                home.setNumOfBath(resultSet.getInt("numOfBath"));
                home.setNumOfBedroom(resultSet.getInt("numOfBedroom"));
                home.setCreatedDate(resultSet.getTimestamp("createdDate").toLocalDateTime());
                home.setPriceId(resultSet.getInt("priceId"));
                home.setPrice(resultSet.getInt("price"));
                homeList.add(home);
            }
        } catch (SQLException e) {
            String errorMessage = String.format("SQL error while retrieving searching homes: Reason: %s", e.getMessage());
            throw new GeneralException(errorMessage, e);
        } catch (IOException e) {
            String errorMessage = String.format("I/O error while retrieving searching homes: Reason: %s", e.getMessage());
            throw new GeneralException(errorMessage, e);
        } catch (ClassNotFoundException e) {
            String errorMessage = String.format("Class not found error while retrieving searching homes for: Reason: %s", e.getMessage());
            throw new GeneralException(errorMessage, e);
        } finally {
                if (resultSet != null) resultSet.close();
                if (ps != null) ps.close();
                if (connection != null) closeConnection(connection); // Pass the connection object
        }

        return homeList;
    }

    /**
     * Fetches the first image URL associated with a home.
     *
     * @param homeId The ID of the home.
     * @return The image URL, or null if no image is found.
     * @throws GeneralException If there is a database error.
     */
    @Override
    public String fetchFirstImage(int homeId) throws SQLException {
        String sql = "select top 1 imgUrl" +
                " from HomeImages" +
                " where Homesid = ?;";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, homeId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("imgUrl");
            }

        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error finding image in the database: " + e.getMessage(), e);
        } finally {
            // Closing resources in reverse order of opening
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
        }
        return null;
    }

    @Override
    public List<String> fetchImages(int homeId) {
        String sql = "select imgUrl" +
                " from HomeImages" +
                " where Homesid = ?;";
        List<String> imageUrls = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, homeId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String imgUrl = resultSet.getString("imgUrl");
                if (imgUrl != null) {
                    imageUrls.add(imgUrl);
                }
            }

        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error finding image in the database: " + e.getMessage(), e);
        } finally {
            // Closing resources in reverse order of opening
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

        return imageUrls;
    }
    /**
     * Counts the number of homes matching the provided search criteria.
     *
     * @param searchParams The search parameters.
     * @return The total count of matching homes.
     * @throws SQLException           If a database access error occurs
     * @throws IOException            If an I/O error occurs
     * @throws ClassNotFoundException if JDBC driver is not found.
     */
    @Override
    public int numOfHome(Map<String, Object> searchParams) throws SQLException, IOException, ClassNotFoundException {
        StringBuilder sql = new StringBuilder(
                "SELECT COUNT(DISTINCT h.id) " +
                        "FROM Homes h " +
                        "JOIN Wards w ON h.wardsId = w.id " +
                        "JOIN Districts d ON w.Districtsid = d.id " +
                        "JOIN Provinces p ON d.provincesId = p.id " +
                        "LEFT JOIN Prices pri ON h.id = pri.Homesid " +
                        "WHERE 1=1"
        );

        // Condition for keyword
        if (searchParams.containsKey("keyword")) {
            sql.append(" AND (h.name LIKE ? OR h.homeDescription LIKE ?)");
        }

        // Condition for homeType
        if (searchParams.containsKey("homeType")) {
            sql.append(" AND h.homeTypeId = ?");
        }

        // Condition for province, district, ward
        if (searchParams.containsKey("province")) {
            sql.append(" AND p.id = ?");
        }
        if (searchParams.containsKey("district")) {
            sql.append(" AND d.id = ?");
        }
        if (searchParams.containsKey("ward")) {
            sql.append(" AND w.id = ?");
        }

        // Condition for priceRange
        if (searchParams.containsKey("minPrice") && searchParams.containsKey("maxPrice")) {
            sql.append(" AND pri.price BETWEEN ? AND ?");
        }

        // Condition for areaRange
        if (searchParams.containsKey("minArea") && searchParams.containsKey("maxArea")) {
            sql.append(" AND h.area BETWEEN ? AND ?");
        }

        // Condition for bathRange
        if (searchParams.containsKey("minBath") && searchParams.containsKey("maxBath")) {
            sql.append(" AND h.numOfBath BETWEEN ? AND ?");
        }

        // Condition for bedRange
        if (searchParams.containsKey("minBed") && searchParams.containsKey("maxBed")) {
            sql.append(" AND h.numOfBedroom BETWEEN ? AND ?");
        }

        // Condition for amenities (using COUNT to check if all are available)
        if (searchParams.containsKey("amenities")) {
            String[] amenities = (String[]) searchParams.get("amenities");
            sql.append(" AND (SELECT COUNT(1) FROM Amenities_Homes ah JOIN Amenities a ON ah.amenitiesId = a.id");
            sql.append(" WHERE ah.homesId = h.id AND a.id IN (");
            sql.append(String.join(",", Collections.nCopies(amenities.length, "?")));
            sql.append(")) = ").append(amenities.length);
        }

        // Condition for fireEquipments (using COUNT to check if all are available)
        if (searchParams.containsKey("fireEquipments")) {
            String[] fireEquipments = (String[]) searchParams.get("fireEquipments");
            sql.append(" AND (SELECT COUNT(1) FROM FireEquipments_Homes feh JOIN FireEquipments fe ON feh.fireEquipmentsId = fe.id");
            sql.append(" WHERE feh.homesId = h.id AND fe.id IN (");
            sql.append(String.join(",", Collections.nCopies(fireEquipments.length, "?")));
            sql.append(")) = ").append(fireEquipments.length);
        }

        int totalCount = 0;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            connection = DBContext.getConnection();
            ps = connection.prepareStatement(sql.toString());
            int paramIndex = 1;

            if (searchParams.containsKey("keyword")) {
                ps.setString(paramIndex++, "%" + searchParams.get("keyword") + "%");
                ps.setString(paramIndex++, "%" + searchParams.get("keyword") + "%");
            }
            if (searchParams.containsKey("homeType")) {
                int homeTypeId = Integer.parseInt(searchParams.get("homeType").toString());
                ps.setInt(paramIndex++, homeTypeId);
            }
            if (searchParams.containsKey("province")) {
                int provinceId = Integer.parseInt(searchParams.get("province").toString());
                ps.setInt(paramIndex++, provinceId);
            }
            if (searchParams.containsKey("district")) {
                int districtId = Integer.parseInt(searchParams.get("district").toString());
                ps.setInt(paramIndex++, districtId);
            }
            if (searchParams.containsKey("ward")) {
                int wardId = Integer.parseInt(searchParams.get("ward").toString());
                ps.setInt(paramIndex++, wardId);
            }
            if (searchParams.containsKey("minPrice") && searchParams.containsKey("maxPrice")) {
                int minPrice = Integer.parseInt(searchParams.get("minPrice").toString());
                int maxPrice = Integer.parseInt(searchParams.get("maxPrice").toString());
                ps.setInt(paramIndex++, minPrice);
                ps.setInt(paramIndex++, maxPrice);
            }
            if (searchParams.containsKey("minArea") && searchParams.containsKey("maxArea")) {
                BigDecimal minArea = BigDecimal.ZERO;
                BigDecimal maxArea = new BigDecimal(600);

                if (searchParams.get("minArea") != null) {
                    minArea = new BigDecimal(searchParams.get("minArea").toString());
                }

                if (searchParams.get("maxArea") != null) {
                    maxArea = new BigDecimal(searchParams.get("maxArea").toString());
                }

                ps.setBigDecimal(paramIndex++, minArea);
                ps.setBigDecimal(paramIndex++, maxArea);
            }

            if (searchParams.containsKey("minBath") && searchParams.containsKey("maxBath")) {
                int minBath = Integer.parseInt(searchParams.get("minBath").toString());
                int maxBath = Integer.parseInt(searchParams.get("maxBath").toString());
                ps.setInt(paramIndex++, minBath);
                ps.setInt(paramIndex++, maxBath);
            }
            if (searchParams.containsKey("minBed") && searchParams.containsKey("maxBed")) {
                int minBed = Integer.parseInt(searchParams.get("minBed").toString());
                int maxBed = Integer.parseInt(searchParams.get("maxBed").toString());
                ps.setInt(paramIndex++, minBed);
                ps.setInt(paramIndex++, maxBed);
            }
            if (searchParams.containsKey("amenities")) {
                String[] amenities = (String[]) searchParams.get("amenities");
                for (String amenity : amenities) {
                    int numAmen = Integer.parseInt(amenity);
                    ps.setInt(paramIndex++, numAmen);
                }
            }
            if (searchParams.containsKey("fireEquipments")) {
                String[] fireEquipments = (String[]) searchParams.get("fireEquipments");
                for (String fireEquipment : fireEquipments) {
                    int fireEid = Integer.parseInt(fireEquipment);
                    ps.setInt(paramIndex++, fireEid);
                }
            }

            resultSet = ps.executeQuery();
            if (resultSet.next()) {
                totalCount = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            String errorMessage = String.format("SQL error while retrieving searching homes: Reason: %s", e.getMessage());
            throw new GeneralException(errorMessage, e);
        } catch (IOException e) {
            String errorMessage = String.format("I/O error while retrieving searching homes: Reason: %s", e.getMessage());
            throw new GeneralException(errorMessage, e);
        } catch (ClassNotFoundException e) {
            String errorMessage = String.format("Class not found error while retrieving searching homes for: Reason: %s", e.getMessage());
            throw new GeneralException(errorMessage, e);
        } finally {
            if (resultSet != null) resultSet.close();
            if (ps != null) ps.close();
            if (connection != null) connection.close();
        }
            return totalCount;
    }

    /**
     * Retrieves the minimum area of homes from the database.
     *
     * @return The minimum area.
     * @throws GeneralException If a database error occurs.
     */
    @Override
    public BigDecimal getMinArea() throws SQLException {
        String sql = "select top 1 area " +
                " from Homes order by  area asc;";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getBigDecimal("area");
            }

        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error get min area in the database: " + e.getMessage(), e);
        } finally {
            // Closing resources in reverse order of opening
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
        }

        return BigDecimal.valueOf(0);
    }

    /**
     * Retrieves the maximum area of homes from the database.
     *
     * @return The maximum area.
     * @throws GeneralException If a database error occurs.
     */
    @Override
    public BigDecimal getMaxArea() throws SQLException {
        String sql = "select top 1 area " +
                " from Homes order by  area desc;";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getBigDecimal("area");
            }

        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error get max area in the database: " + e.getMessage(), e);
        } finally {
            // Closing resources in reverse order of opening
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
        }

        return BigDecimal.valueOf(0);
    }

    /**
     * Retrieves the minimum number of bedrooms from the database.
     *
     * @return The minimum number of bedrooms.
     * @throws GeneralException if a database error occurs.
     */
    @Override
    public int getMinBed() throws SQLException {
        String sql = "select top 1 numOfBedroom " +
                " from Homes order by  numOfBedroom asc;";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("numOfBedroom");
            }

        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error get min bed in the database: " + e.getMessage(), e);
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return 0;
    }

    /**
     * Retrieves the maximum number of bedrooms from the database.
     *
     * @return The maximum number of bedrooms.
     * @throws GeneralException if a database error occurs.
     */
    @Override
    public int getMaxBed() throws SQLException {
        String sql = "select top 1 numOfBedroom " +
                " from Homes order by  numOfBedroom desc;";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("numOfBedroom");
            }

        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error get max bed in the database: " + e.getMessage(), e);
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return 0;
    }

    /**
     * Retrieves the minimum number of bathrooms from the database.
     *
     * @return The minimum number of bathrooms.
     * @throws GeneralException if a database error occurs.
     */
    @Override
    public int getMinBath() throws SQLException {
        String sql = "select top 1 numOfBath " +
                " from Homes order by  numOfBath asc;";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("numOfBath");
            }

        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error get min bath in the database: " + e.getMessage(), e);
        } finally {
            // Closing resources in reverse order of opening
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return 0;
    }

    /**
     * Retrieves the maximum number of bathrooms from the database.
     *
     * @return The maximum number of bathrooms.
     * @throws GeneralException if a database error occurs.
     */
    @Override
    public int getMaxBath() throws SQLException {
        String sql = "select top 1 numOfBath " +
                " from Homes order by  numOfBath desc;";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("numOfBath");
            }

        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error get max bath in the database: " + e.getMessage(), e);
        } finally {
            // Closing resources in reverse order of opening
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return 0;
    }

    @Override
    public Home getHomeById(int id) {
        String sql = "SELECT * FROM [dbo].[Homes] WHERE [id]=?";

        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Home home = new Home();
                    home.setId(resultSet.getInt("id"));
                    home.setName(resultSet.getString("name"));
                    home.setAddress(resultSet.getString("address"));
                    home.setLongitude(resultSet.getBigDecimal("longitude"));
                    home.setLatitude(resultSet.getBigDecimal("latitude"));
                    home.setOrientation(resultSet.getString("orientation"));
                    home.setArea(resultSet.getBigDecimal("area"));
                    home.setLeaseDuration(resultSet.getInt("leaseDuration"));
                    home.setMoveInDate(resultSet.getDate("moveInDate").toLocalDate());
                    home.setNumOfBedroom(resultSet.getInt("numOfBedroom"));
                    home.setNumOfBath(resultSet.getInt("numOfBath"));
                    home.setCreatedDate(resultSet.getTimestamp("createdDate").toLocalDateTime());
                    home.setModifiedDate(resultSet.getTimestamp("modifiedDate") != null ? resultSet.getTimestamp("modifiedDate").toLocalDateTime() : null);
                    home.setHomeDescription(resultSet.getString("homeDescription"));
                    home.setTenantDescription(resultSet.getString("tenantDescription"));
                    home.setWardId(resultSet.getInt("wardsId"));
                    home.setHomeTypeId(resultSet.getInt("homeTypeId"));
                    home.setCreatedBy(resultSet.getInt("createdBy"));
                    return home;
                }
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            // Re-throw as runtime exception to be handled by the service layer
            throw new IllegalArgumentException("Error fetching home from the database: " + e.getMessage(), e);
        }
        return null;
    }

    /**
     * Retrieves the total number of homes available in the database.
     *
     * @return the total number of homes, or 0 if none are found.
     * @throws RuntimeException if there is an error during the database operation.
     */
    @Override
    public int getNumberHomes() {
        String sql = "select count(id) total\n" +
                "from Homes";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            // If a result is found, return the total number
            if (resultSet.next()) {
                return resultSet.getInt("total");
            }

        } catch (SQLException | IOException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Error retrieving total homes from the database", e);
            throw new RuntimeException("Error retrieving total homes from the database", e);
        } finally {
            // Closing resources in reverse order of opening
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

        return 0;
    }

    /**
     * Retrieves homes matching the specified host IDs.
     *
     * @param matchingHost an array of host IDs to match.
     * @return List of matching Home objects or null if the input is invalid.
     */
    @Override
    public List<Home> getMatchingHomes(int[] matchingHost) {
        if (matchingHost == null || matchingHost.length == 0) {
            LOGGER.warn("Preference is null. No updates will be made.");
            return null;
        }

        StringBuilder sql = new StringBuilder("select * \n" +
                "from Homes\n" +
                "where createdBy in (");
        for (int i = 0; i < matchingHost.length; i++) {
            sql.append("?");
            if (i < matchingHost.length - 1) {
                sql.append(", ");
            }
        }
        sql.append(")");
        List<Home> matchingHomes = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql.toString());


            // Set the price ID parameters in the prepared statement
            for (int i = 0; i < matchingHost.length; i++) {
                preparedStatement.setInt(i + 1, matchingHost[i]);
            }

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Home home = new Home();
                home.setId(resultSet.getInt("id"));
                home.setName(resultSet.getString("name"));
                home.setAddress(resultSet.getString("address"));
                home.setArea(resultSet.getBigDecimal("area"));
                home.setLeaseDuration(resultSet.getInt("leaseDuration"));
                home.setMoveInDate(resultSet.getDate("moveInDate").toLocalDate());
                matchingHomes.add(home);
            }
        } catch (SQLException e) {
            logger.warning("SQL error occurred while retrieving prices from the database: {}" + e.getMessage());
            throw new RuntimeException("Error retrieving homes from the database: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.warning("Unexpected error occurred: {}" + e.getMessage());
            throw new RuntimeException("Error retrieving homes from the database: " + e.getMessage(), e);
        } finally {
            // Closing resources in reverse order of opening
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
        return matchingHomes;
    }

    /**
     * Retrieves a list of homes based on the specified appointments.
     *
     * @param appointments a list of Appointment objects containing home IDs.
     * @return a list of Home objects matching the appointment IDs, or null if the input is invalid.
     * @throws RuntimeException if there is an error during the database operation.
     */
    @Override
    public List<Home> getHomeByAppointment(List<Appointment> appointments) {
        if(appointments == null || appointments.isEmpty()){
            LOGGER.warn("Appointment is null. No updates will be made.");
            return null;
        }
        List<Home> homeList = new ArrayList<>();
        StringBuilder sql = new StringBuilder("select id,name,address \n" +
                "from Homes\n" +
                "where id in (");
        for (int i = 0; i < appointments.size(); i++) {
            sql.append("?");
            if (i < appointments.size() - 1) {
                sql.append(", ");
            }
        }
        sql.append(")");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql.toString());


            // Set the price ID parameters in the prepared statement
            for (int i = 0; i < appointments.size(); i++) {
                preparedStatement.setInt(i + 1, appointments.get(i).getHomeId());
            }

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Home home = new Home();
                home.setId(resultSet.getInt("id"));
                home.setName(resultSet.getString("name"));
                home.setAddress(resultSet.getString("address"));
                homeList.add(home);
            }
        } catch (SQLException e) {
            logger.warning("SQL error occurred while retrieving home from the database: {}"+ e.getMessage());
            throw new RuntimeException("Error retrieving homes from the database: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.warning("Unexpected error occurred: {}"+ e.getMessage());
            throw new RuntimeException("Error retrieving homes from the database: " + e.getMessage(), e);
        } finally {
            // Closing resources in reverse order of opening
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
        return homeList;
    }


    @Override
    public int saveHome(Home home) {
        String sql = "INSERT INTO Homes (name, address, longitude, latitude, orientation, area, leaseDuration, moveInDate, numOfBedroom, numOfBath, createdDate, modifiedDate, homeDescription, tenantDescription, wardsId, homeTypeId, createdBy) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        // Using try-with-resources to manage the database connection and resources
        try (Connection connection = DBContext.getConnection();
             // PreparedStatement with RETURN_GENERATED_KEYS to capture the inserted Home ID
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // Setting parameters for the PreparedStatement using the Home object
            preparedStatement.setString(1, home.getName());
            preparedStatement.setString(2, home.getAddress());
            preparedStatement.setBigDecimal(3, home.getLongitude());
            preparedStatement.setBigDecimal(4, home.getLatitude());
            preparedStatement.setString(5, home.getOrientation());
            preparedStatement.setBigDecimal(6, home.getArea());
            preparedStatement.setInt(7, home.getLeaseDuration());
            preparedStatement.setDate(8, java.sql.Date.valueOf(home.getMoveInDate()));
            preparedStatement.setInt(9, home.getNumOfBedroom());
            preparedStatement.setInt(10, home.getNumOfBath());
            preparedStatement.setTimestamp(11, java.sql.Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setTimestamp(12, home.getModifiedDate() != null ? java.sql.Timestamp.valueOf(LocalDateTime.now()) : null);
            preparedStatement.setString(13, home.getHomeDescription());
            preparedStatement.setString(14, home.getTenantDescription());
            preparedStatement.setInt(15, home.getWardId());
            preparedStatement.setInt(16, home.getHomeTypeId());
            preparedStatement.setInt(17, home.getCreatedBy());

            // Execute the insert statement and capture affected rows
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                // Retrieve the generated Home ID
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);  // Return the generated Home ID
                    } else {
                        throw new SQLException("Creating home failed, no ID obtained.");
                    }
                }
            } else {
                throw new SQLException("Creating home failed, no rows affected.");
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            // Re-throw as runtime exception to be handled by the service layer
            throw new IllegalArgumentException("Error saving home to the database: " + e.getMessage(), e);
        }
    }

    /**
     * Retrieve a list of the 12 newest homes from the database.
     *
     * @return List of the 12 newest homes.
     */
    @Override
    public List<Home> getNewHomes() {
        // SQL query to fetch 12 newest homes with price information
        String sql = """
                SELECT TOP 12 h.id,h.name, h.address, h.area, h.createdDate, tb1.id AS priceId
                FROM Homes h
                LEFT JOIN (
                    SELECT Homesid, price, createdDate, id
                    FROM Prices
                    WHERE createdDate IN (
                        SELECT MAX(p.createdDate) 
                        FROM Prices p
                        GROUP BY p.Homesid
                    )
                ) tb1 ON tb1.Homesid = h.id
                ORDER BY h.createdDate DESC
                """;

        // List to store fetched Home objects
        List<Home> homeList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        // Try-with-resources to automatically close resources
        try{
            connection = DBContext.getConnection(); // Get database connection
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            // Process each result row and map it to a Home object
            while (resultSet.next()) {
                Home home = new Home();
                home.setId(resultSet.getInt("id"));
                home.setAddress(resultSet.getString("address"));
                home.setArea(resultSet.getBigDecimal("area"));
                home.setCreatedDate(resultSet.getTimestamp("createdDate").toLocalDateTime());
                home.setName(resultSet.getString("name"));
                home.setPriceId(resultSet.getInt("priceId"));

                homeList.add(home); // Add the Home object to the list
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            // Log the exception and throw a custom exception
            logger.severe("Error retrieving homes from the database: " + e.getMessage());
            throw new GeneralException("Error retrieving homes from the database", e);
        } finally {
            // Ensure resources are closed in the finally block
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) closeConnection(connection); // close the connection using DBContext's method
            } catch (SQLException e) {
                logger.warning("Failed to close resources: " + e.getMessage());
            }
        }

        return homeList; // Return the list of homes
    }

    /**
     * Get home object via author id
     *
     * @param userId
     * @return
     */
    @Override
    public List<Home> getHomesByUserId(int userId) {
        List<Home> homes = new ArrayList<>();
        String sql = "SELECT * FROM [dbo].[Homes] WHERE [createdBy]=?";

        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Home home = new Home();
                    home.setId(resultSet.getInt("id"));
                    home.setName(resultSet.getString("name"));
                    home.setAddress(resultSet.getString("address"));
                    home.setLongitude(resultSet.getBigDecimal("longitude"));
                    home.setLatitude(resultSet.getBigDecimal("latitude"));
                    home.setOrientation(resultSet.getString("orientation"));
                    home.setArea(resultSet.getBigDecimal("area"));
                    home.setLeaseDuration(resultSet.getInt("leaseDuration"));
                    home.setMoveInDate(resultSet.getDate("moveInDate").toLocalDate());
                    home.setNumOfBedroom(resultSet.getInt("numOfBedroom"));
                    home.setNumOfBath(resultSet.getInt("numOfBath"));
                    home.setCreatedDate(resultSet.getTimestamp("createdDate").toLocalDateTime());
                    home.setModifiedDate(resultSet.getTimestamp("modifiedDate") != null ? resultSet.getTimestamp("modifiedDate").toLocalDateTime() : null);
                    home.setHomeDescription(resultSet.getString("homeDescription"));
                    home.setTenantDescription(resultSet.getString("tenantDescription"));
                    home.setWardId(resultSet.getInt("wardsId"));
                    home.setHomeTypeId(resultSet.getInt("homeTypeId"));
                    home.setCreatedBy(resultSet.getInt("createdBy"));
                    homes.add(home);
                }
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            // Re-throw as runtime exception to be handled by the service layer
            throw new IllegalArgumentException("Error fetch home from the database: " + e.getMessage(), e);
        }
        return homes;
    }

}
