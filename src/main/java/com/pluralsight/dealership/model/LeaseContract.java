package com.pluralsight.dealership.model;

import java.time.LocalDate;

// CSV File format
// [LEASE, date, name, email, vin, year, make, model, type, color, odometer, price,
//  expectedEndingValue, leaseFee, totalPrice, monthlyPayment]
public class LeaseContract {
    private final String vin;
    private final LocalDate contractDate;
    private final String customerName;
    private final String customerEmail;
    private final double totalPrice;
    private final double monthlyPayment;
    private final int leaseTerm;

    public LeaseContract(String vin, LocalDate contractDate, String customerName, String customerEmail,
                         double totalPrice, double monthlyPayment, int leaseTerm) {
        this.vin = vin;
        this.contractDate = contractDate;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.totalPrice = totalPrice;
        this.monthlyPayment = monthlyPayment;
        this.leaseTerm = leaseTerm;
    }

    public String getVin() {
        return vin;
    }

    public LocalDate getContractDate() {
        return contractDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public double getMonthlyPayment() {
        return monthlyPayment;
    }

    public int getLeaseTerm() {
        return leaseTerm;
    }

    @Override
    public String toString() {
        return "LeaseContract{" +
                "vin='" + vin + '\'' +
                ", contractDate=" + contractDate +
                ", customerName='" + customerName + '\'' +
                ", customerEmail='" + customerEmail + '\'' +
                ", totalPrice=" + totalPrice +
                ", monthlyPayment=" + monthlyPayment +
                ", leaseTerm=" + leaseTerm +
                '}';
    }
}
