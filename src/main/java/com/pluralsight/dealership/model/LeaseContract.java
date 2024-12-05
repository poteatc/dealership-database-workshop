package com.pluralsight.dealership.model;

// CSV File format
// [LEASE, date, name, email, vin, year, make, model, type, color, odometer, price,
//  expectedEndingValue, leaseFee, totalPrice, monthlyPayment]
public class LeaseContract extends Contract {

    private final double expectedEndingValuePercent = 0.5;
    private final double leaseFeePercent = .07;

    public LeaseContract(String date, String customerName, String customerEmail, Vehicle vehicleSold) {
        super(date, customerName, customerEmail, vehicleSold);
    }

    public double getExpectedEndingValuePercent() {
        return expectedEndingValuePercent;
    }

    public double getLeaseFeePercent() {
        return leaseFeePercent;
    }

    @Override
    double getTotalPrice() {
        return 0;
    }

    @Override
    double getMonthlyPayment() {
        // TODO Fix calculation
        double monthlyPayment = getVehicleSold().getPrice() + leaseFeePercent * getVehicleSold().getPrice();
        return monthlyPayment;
    }

    @Override
    String toStringRepresentation() {
        return "TODO: Make lease contract string";
//        return String.format("LEASE|%s|%s|%s|%s|%.2f|%.2f|%.2f|%.2f|%s|%.2f"
//                , getDate(), getCustomerName(), getCustomerEmail(), getVehicleSold().toCSVFormat()
//                , (getVehicleSold().getPrice() * expectedEndingValuePercent), (getVehicleSold().getPrice() * leaseFeePercent)
//                , (leaseFeePercent + getVehicleSold().getPrice() * expectedEndingValuePercent), getTotalPrice(), getMonthlyPayment());
    }
}
