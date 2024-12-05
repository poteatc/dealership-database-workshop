package com.pluralsight.dealership.dao;

import com.pluralsight.dealership.model.Dealership;
import com.pluralsight.dealership.model.Vehicle;

import java.util.List;

public interface DealershipDao {
    Dealership findDealershipById();
    List<Dealership> getAllDealerships();
}
