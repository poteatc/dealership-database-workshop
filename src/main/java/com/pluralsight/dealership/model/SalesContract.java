package com.pluralsight.dealership.model;


// CSV File format
// [SALE, date, name, email, vin, year, make, model, type, color, odometer, price,
//  salesTaxAmount, recordingFee, processingFee, totalPrice, isFinancing, monthlyPayment]
public class SalesContract extends Contract {

    private final double salesTaxAmount = 0.05;
    private final double recordingFee = 100.0;
    private double processingFee;
    private boolean isFinancing;

    public SalesContract(String date, String customerName, String customerEmail, Vehicle vehicleSold,
                         double processingFee, boolean isFinancing) {
        super(date, customerName, customerEmail, vehicleSold);
        this.processingFee = processingFee;
        this.isFinancing = isFinancing;
    }

    public double getSalesTaxAmount() {
        return salesTaxAmount;
    }

    public double getRecordingFee() {
        return recordingFee;
    }

    public double getProcessingFee() {
        return processingFee;
    }

    public void setProcessingFee(int processingFee) {
        this.processingFee = processingFee;
    }

    public boolean isFinancing() {
        return isFinancing;
    }

    public void setFinancing(boolean financing) {
        this.isFinancing = financing;
    }

    @Override
    double getTotalPrice() {
        Vehicle v = getVehicleSold();
        double total = v.getPrice() + v.getPrice() * salesTaxAmount + recordingFee + processingFee;
        return total;
    }

    @Override
    double getMonthlyPayment() {
        Vehicle v = getVehicleSold();
        double monthlyLoanRate = v.getPrice() > 10000 ? .0425 : .0525;
        // TODO Fix calculation
        return -1;
    }

    @Override
    String toStringRepresentation() {
        return String.format("SALE|%s|%s|%s|%s|%.2f|%.2f|%.2f|%.2f|%s|%.2f"
        , getDate(), getCustomerName(), getCustomerEmail(), getVehicleSold().toCSVFormat()
        , (getVehicleSold().getPrice() * salesTaxAmount), recordingFee, processingFee, getTotalPrice()
                , isFinancing ? "YES" : "NO", getMonthlyPayment());
    }
}
