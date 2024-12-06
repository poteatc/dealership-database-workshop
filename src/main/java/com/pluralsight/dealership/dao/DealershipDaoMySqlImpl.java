package com.pluralsight.dealership.dao;

import com.pluralsight.dealership.model.Dealership;
import com.pluralsight.dealership.model.Vehicle;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DealershipDaoMySqlImpl implements DealershipDao {
    private DataSource dataSource;

    public DealershipDaoMySqlImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Dealership findDealershipById(int id) {
        return null;
    }

    @Override
    public List<Dealership> getAllDealerships() {
        List<Dealership> dealerships = new ArrayList<>();

        String query = """
                SELECT * FROM dealerships
                """;

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int dealership_id = rs.getInt("dealership_id");
                String name = rs.getString("name");
                String address = rs.getString("address");
                String phone = rs.getString("phone");

                Dealership v = new Dealership(dealership_id, name, address, phone);
                dealerships.add(v);
            }
            return dealerships;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Vehicle> findAllVehiclesByDealership(int id) {
        List<Vehicle> vehicles = new ArrayList<>();

        String query = """
                SELECT dealership_id, inventory.VIN, vehicles.year year, vehicles.make make, vehicles.model model 
                FROM inventory
                JOIN vehicles ON inventory.VIN = vehicles.VIN
                WHERE dealership_id = ?;
                """;

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String vin = rs.getString("VIN");
                int year = rs.getInt("year");
                String make = rs.getString("make");
                String model = rs.getString("model");
                String vehicleType = "";
                String color = "";
                int odometer = 0;
                double price = 0.0;
                boolean sold = false;

                Vehicle v = new Vehicle(vin, year, make, model, vehicleType, color, odometer, price, sold);
                vehicles.add(v);
            }
            return vehicles;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
