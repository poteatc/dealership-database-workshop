package com.pluralsight.dealership.dao;

import com.pluralsight.dealership.model.Vehicle;

import java.util.List;

public interface VehicleDao {
    Vehicle findVehicleByVin();
    List<Vehicle> getAllVehicles();
}
