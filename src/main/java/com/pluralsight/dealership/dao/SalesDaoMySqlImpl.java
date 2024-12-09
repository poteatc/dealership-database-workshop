package com.pluralsight.dealership.dao;

import com.pluralsight.dealership.model.SalesContract;
import com.pluralsight.dealership.model.Vehicle;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SalesDaoMySqlImpl implements SalesDao {
    private final DataSource dataSource;
    private final VehicleDaoMySqlImpl vehicleDaoMySql;

    public SalesDaoMySqlImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        this.vehicleDaoMySql = new VehicleDaoMySqlImpl(dataSource);
    }

    @Override
    public void addSalesContract(String vin, LocalDate date, String name, String email, double price, double amount, double taxes, double fees, double balanceDue) {
        String query = """
                    insert into sales_contracts (VIN, contract_date, customer_name, customer_email, total_price, amount_paid, taxes, fees, balance_due)
                    values (?,?,?,?,?,?,?,?,?)
                """;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, vin);
            preparedStatement.setDate(2, Date.valueOf(date));
            preparedStatement.setString(3, name);
            preparedStatement.setString(4, email);
            preparedStatement.setDouble(5, price);
            preparedStatement.setDouble(6, amount);
            preparedStatement.setDouble(7, taxes);
            preparedStatement.setDouble(8, fees);
            preparedStatement.setDouble(9, balanceDue);
            int rows = preparedStatement.executeUpdate();
            // confirm the update
            System.out.println("Vehicle sold!!!");
            vehicleDaoMySql.updateVehicleSoldStatus(vin);


        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public double calculateTaxes(double price) {
        return price * taxRate;
    }

    @Override
    public double calculateFees(double price) {
        return price * feeRate;
    }

    @Override
    public double calculateBalanceDue(double price, double taxes, double fees, double amountPaid) {
        return (price + taxes + fees) - amountPaid;
    }

    @Override
    public Vehicle getVehicleByVin(String vin) {
        String query = """
                    select * from vehicles
                    where VIN = ?
                """;

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, vin);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String VIN = rs.getString("VIN");
                int year = rs.getInt("year");
                String make = rs.getString("make");
                String model = rs.getString("model");
                String vehicleType = rs.getString("vehicle_type");
                String color = rs.getString("color");
                int odometer = rs.getInt("mileage");
                double price = rs.getDouble("price");
                boolean sold = rs.getBoolean("sold");

                return new Vehicle(VIN, year, make, model, vehicleType, color, odometer, price, sold);
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

    @Override
    public List<Vehicle> getVehiclesSold() {
        List<Vehicle> soldVehicles = new ArrayList<>();
        String query = """
                    select * from vehicles
                    where sold = 1
                """;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String VIN = rs.getString("VIN");
                int year = rs.getInt("year");
                String make = rs.getString("make");
                String model = rs.getString("model");
                String vehicleType = rs.getString("vehicle_type");
                String color = rs.getString("color");
                int odometer = rs.getInt("mileage");
                double price = rs.getDouble("price");
                boolean sold = rs.getBoolean("sold");
                Vehicle vehicle = new Vehicle(VIN, year, make, model, vehicleType, color, odometer, price, sold);
                soldVehicles.add(vehicle);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return soldVehicles;
    }

    @Override
    public List<SalesContract> getAllSalesContracts() {
        List<SalesContract> salesContracts = new ArrayList<>();

        String query = """
                    select * from sales_contracts
                """;

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String VIN = rs.getString("VIN");
                LocalDate date = rs.getDate("contract_date").toLocalDate();
                String customerName = rs.getString("customer_name");
                String customerEmail = rs.getString("customer_email");
                double totalPrice = rs.getDouble("total_price");
                double amountPaid = rs.getDouble("amount_paid");
                double taxes = rs.getDouble("taxes");
                double fees = rs.getDouble("fees");
                double balanceDue = rs.getDouble("balance_due");
                SalesContract salesContract = new SalesContract(VIN, date, customerName,customerEmail, totalPrice,
                        amountPaid, taxes, fees, balanceDue);
                salesContracts.add(salesContract);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return salesContracts;

    }
}
