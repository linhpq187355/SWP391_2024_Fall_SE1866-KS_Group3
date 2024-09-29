package com.homesharing.dao.impl;
import com.homesharing.conf.DBContext;
import com.homesharing.dao.HomeDetailDAO;
import com.homesharing.model.Home;
import  com.homesharing.model.Price;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HomeDetailDAOImpl implements HomeDetailDAO {

    @Override
    public Home getHomeById(int homeId) {
        // SQL query to fetch home details by ID
        String sql = "SELECT \n" +
                "    h.id AS homeId, \n" +
                "    h.name AS homeName, \n" +
                "    h.address, \n" +
                "    h.longitude, \n" +
                "    h.latitude, \n" +
                "    h.orientation, \n" +
                "    h.area, \n" +
                "    h.leaseDuration, \n" +
                "    h.moveInDate, \n" +
                "    h.numOfBedroom, \n" +
                "    h.numOfBath, \n" +
                "    h.homeDescription, \n" +
                "    h.tenantDescription, \n" +
                "    ht.name AS homeTypeName, \n" +
                "    u.firstName AS ownerFirstName, \n" +
                "    u.lastName AS ownerLastName, \n" +
                "    u.phoneNumber AS ownerPhone, \n" +
                "    u.email AS ownerEmail,\n" +
                "    pr.price, \n" +
                "    hi.imgUrl, \n" +
                "    r.star AS reviewStar, \n" +
                "    r.comments AS reviewComments,\n" +
                "    a.name AS amenityName,\n" +
                "    f.name AS fireEquipmentName\n" +
                "FROM Homes h\n" +
                "JOIN HomeTypes ht ON h.homeTypeId = ht.id\n" +
                "JOIN [HSS Users] u ON h.createdBy = u.id\n" +
                "LEFT JOIN Prices pr ON h.id = pr.Homesid\n" +
                "LEFT JOIN HomeImages hi ON h.id = hi.Homesid\n" +
                "LEFT JOIN Reviews r ON h.id = r.homeId\n" +
                "LEFT JOIN Amenities_Homes ah ON h.id = ah.homesId\n" +
                "LEFT JOIN Amenities a ON ah.amenitiesId = a.id\n" +
                "LEFT JOIN FireEquipments_Homes fh ON h.id = fh.homesId\n" +
                "LEFT JOIN FireEquipments f ON fh.fireEquipmentsId = f.id\n" +
                "WHERE h.id = ?;";

        Home home = null;

        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Set the ID parameter in the query
            preparedStatement.setInt(1, homeId);

            // Execute the query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Create a new Home object and populate it with data from the ResultSet
                    home = new Home();
                    home.setId(resultSet.getInt("homeId"));
                    home.setName(resultSet.getString("homeName"));
                    home.setAddress(resultSet.getString("address"));
                    home.setLongitude(resultSet.getBigDecimal("longitude"));
                    home.setLatitude(resultSet.getBigDecimal("latitude"));
                    home.setOrientation(resultSet.getString("orientation"));
                    home.setArea(resultSet.getBigDecimal("area"));
                    home.setLeaseDuration(resultSet.getInt("leaseDuration"));
                    home.setMoveInDate(resultSet.getTimestamp("moveInDate").toLocalDateTime());
                    home.setNumOfBedroom(resultSet.getInt("numOfBedroom"));
                    home.setNumOfBath(resultSet.getInt("numOfBath"));
                    home.setHomeDescription(resultSet.getString("homeDescription"));
                    home.setTenantDescription(resultSet.getString("tenantDescription"));
                }
            }

        } catch (SQLException | IOException | ClassNotFoundException e) {
            // Handle any exceptions that occur during database access
            throw new RuntimeException("Error retrieving home details: " + e.getMessage(), e);
        }

        // Return the Home object, or null if no home was found
        return home;
    }
}
