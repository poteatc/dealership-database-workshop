package com.pluralsight.dealership.dao;

import com.pluralsight.dealership.model.Dealership;
import com.pluralsight.dealership.model.Vehicle;

import java.util.List;

public interface DealershipDao {
    Dealership findDealershipById(int id);
    List<Dealership> getAllDealerships();
    List<Vehicle> findAllVehiclesByDealership(int id);
}
