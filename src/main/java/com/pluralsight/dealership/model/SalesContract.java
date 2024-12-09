package com.pluralsight.dealership.model;

import java.time.LocalDate;

public class SalesContract {
    private final String vin;
    private final LocalDate contractDate;
    private final String customerName;
    private final String customerEmail;
    private final double totalPrice;
    private final double amountPaid;
    private final double taxes;
    private final double fees;
    private final double balanceDue;

    public SalesContract(String vin, LocalDate contractDate, String customerName, String customerEmail,
                         double totalPrice, double amountPaid, double taxes, double fees, double balanceDue) {
        this.vin = vin;
        this.contractDate = contractDate;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.totalPrice = totalPrice;
        this.amountPaid = amountPaid;
        this.taxes = taxes;
        this.fees = fees;
        this.balanceDue = balanceDue;
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

    public double getAmountPaid() {
        return amountPaid;
    }

    public double getTaxes() {
        return taxes;
    }

    public double getFees() {
        return fees;
    }

    public double getBalanceDue() {
        return balanceDue;
    }

    // TODO : reformat to string
    @Override
    public String toString() {
        return "SalesContract{" +
                "vin='" + vin + '\'' +
                ", contractDate=" + contractDate +
                ", customerName='" + customerName + '\'' +
                ", customerEmail='" + customerEmail + '\'' +
                ", totalPrice=" + totalPrice +
                ", amountPaid=" + amountPaid +
                ", taxes=" + taxes +
                ", fees=" + fees +
                ", balanceDue=" + balanceDue +
                '}';
    }
}
