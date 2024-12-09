package com.pluralsight.dealership.dao;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class VehicleDaoMySqlImplTest {

    BasicDataSource ds = new BasicDataSource();

    @BeforeEach
    void setUp() {
        ds.setUrl("jdbc:mysql://localhost/dealership");
        ds.setUsername("caleb");
        ds.setPassword("Amw22198.");
    }

    @AfterEach
    void tearDown() {
        try {
            ds.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void test_getAllVehicles() {
        VehicleDaoMySqlImpl vehicleDAOMySql = new VehicleDaoMySqlImpl(ds);
        int count = vehicleDAOMySql.getAllVehicles().size();
        try (Connection connection = ds.getConnection()) {
            String query = "select COUNT(*) from vehicles";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            int numRows = -1;
            while (rs.next()) {
                numRows = rs.getInt(1);
            }
            assertEquals(count, numRows);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getVehiclesInPriceRange() {
    }

    @Test
    void getVehiclesByMakeAndModel() {
    }

    @Test
    void getVehiclesByYearRange() {
    }

    @Test
    void getVehiclesByColor() {
    }

    @Test
    void getVehiclesByMileageRange() {
    }

    @Test
    void getVehiclesByType() {
    }
}