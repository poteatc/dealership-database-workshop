package com.pluralsight.dealership.dao;

import com.pluralsight.dealership.model.LeaseContract;
import com.pluralsight.dealership.model.SalesContract;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LeaseDaoSqlImpl implements LeaseDao {
    private DataSource dataSource;

    public LeaseDaoSqlImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void addLeaseContract(String vin, LocalDate date, String name, String email, double price, double monthlyPayment, int leaseTerm) {
        String query = """
                    insert into lease_contracts (VIN, contract_date, customer_name, customer_email, total_price, monthly_payment, lease_term)
                    values (?,?,?,?,?,?,?,?,?)
                """;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, vin);
            preparedStatement.setDate(2, Date.valueOf(date));
            preparedStatement.setString(3, name);
            preparedStatement.setString(4, email);
            preparedStatement.setDouble(5, price);
            preparedStatement.setDouble(6, monthlyPayment);
            preparedStatement.setInt(7, leaseTerm);
            int rows = preparedStatement.executeUpdate();
            // confirm the update
            System.out.println("Vehicle leased!!!");
            //vehicleDaoMySql.updateVehicleSoldStatus(vin);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public double calculateMonthlyPayment(double price, int leaseTerm) {
        return price / leaseTerm;
    }

    @Override
    public List<LeaseContract> getAllLeaseContracts() {
        List<LeaseContract> leaseContracts = new ArrayList<>();

        String query = """
                    select * from lease_contracts
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
                double monthlyPayment = rs.getDouble("monthly_payment");
                int leaseTerm = rs.getInt("lease_term");
                LeaseContract leaseContract = new LeaseContract(VIN, date, customerName,customerEmail, totalPrice,
                        monthlyPayment, leaseTerm);
                leaseContracts.add(leaseContract);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return leaseContracts;
    }
}
