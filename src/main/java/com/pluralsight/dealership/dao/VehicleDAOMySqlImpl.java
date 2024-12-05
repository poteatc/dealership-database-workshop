package com.pluralsight.dealership.dao;

import com.pluralsight.dealership.model.Vehicle;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehicleDAOMySqlImpl implements VehicleDao {
    private final DataSource dataSource;

    public VehicleDAOMySqlImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

//    @Override
//    public Vehicle findVehicleByVin() {
//        return null;
//    }

    @Override
    public List<Vehicle> getAllVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();

        String query = "select * from vehicles";

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String vin = rs.getString("VIN");
                int year = rs.getInt("year");
                String make = rs.getString("make");
                String model = rs.getString("model");
                String vehicleType = rs.getString("vehicle_type");
                String color = rs.getString("color");
                int odometer = rs.getInt("mileage");
                double price = rs.getDouble("price");
                boolean sold = rs.getBoolean("sold");

                Vehicle v = new Vehicle(vin, year, make, model, vehicleType, color, odometer, price, sold);
                vehicles.add(v);
            }
            return vehicles;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Vehicle> getVehiclesInPriceRange(double min, double max) {
        List<Vehicle> vehicles = new ArrayList<>();

        String query = """
        select *
        from vehicles
        where price between ? and ?
        """;

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDouble(1, min);
            preparedStatement.setDouble(2, max);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String vin = rs.getString("VIN");
                int year = rs.getInt("year");
                String make = rs.getString("make");
                String model = rs.getString("model");
                String vehicleType = rs.getString("vehicle_type");
                String color = rs.getString("color");
                int odometer = rs.getInt("mileage");
                double price = rs.getDouble("price");
                boolean sold = rs.getBoolean("sold");

                Vehicle v = new Vehicle(vin, year, make, model, vehicleType, color, odometer, price, sold);
                vehicles.add(v);
            }
            return vehicles;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Vehicle> getVehiclesByMakeAndModel(String make, String model) {
        List<Vehicle> vehicles = new ArrayList<>();

        String query = """
        select *
        from vehicles
        where price between ? and ?
        """;

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String vin = rs.getString("VIN");
                int year = rs.getInt("year");
                String vmake = rs.getString("make");
                String vmodel = rs.getString("model");
                String vehicleType = rs.getString("vehicle_type");
                String color = rs.getString("color");
                int odometer = rs.getInt("mileage");
                double price = rs.getDouble("price");
                boolean sold = rs.getBoolean("sold");

                Vehicle v = new Vehicle(vin, year, vmake, vmodel, vehicleType, color, odometer, price, sold);
                vehicles.add(v);
            }
            return vehicles;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Vehicle> getVehiclesByYearRange(String make, String model) {
        return List.of();
    }

    @Override
    public List<Vehicle> getVehiclesByColor(String make, String model) {
        return List.of();
    }

    @Override
    public List<Vehicle> getVehiclesByMileageRange(String make, String model) {
        return List.of();
    }

    @Override
    public List<Vehicle> getVehiclesByType(String make, String model) {
        return List.of();
    }
}
