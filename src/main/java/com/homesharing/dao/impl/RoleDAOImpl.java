package com.homesharing.dao.impl;

import com.homesharing.conf.DBContext;
import com.homesharing.dao.RoleDAO;
import com.homesharing.model.Home;
import com.homesharing.model.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RoleDAOImpl implements RoleDAO {

    @Override
    public List<Role> getAllRoles() {
        List<Role> roles = new ArrayList<Role>();
        String sql = "SELECT [id]\n" +
                "      ,[name]\n" +
                "  FROM [dbo].[Roles]";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Role role = new Role();
                role.setId(resultSet.getInt("id"));
                role.setName(resultSet.getString("name"));
                roles.add(role);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return roles;
    }
}
