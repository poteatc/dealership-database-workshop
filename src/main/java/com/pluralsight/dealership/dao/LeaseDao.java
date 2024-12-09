package com.pluralsight.dealership.dao;

import com.pluralsight.dealership.model.LeaseContract;
import com.pluralsight.dealership.model.Vehicle;

import java.time.LocalDate;
import java.util.List;

public interface LeaseDao {
    void addLeaseContract(String vin, LocalDate date, String name, String email, double price, double monthlyPayment, int leaseTerm);

    double calculateMonthlyPayment(double price, int leaseTerm);
    List<LeaseContract> getAllLeaseContracts();
}
