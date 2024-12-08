package com.pluralsight;

import com.pluralsight.dealership.dao.DealershipDao;
import com.pluralsight.dealership.dao.DealershipDaoMySqlImpl;
import com.pluralsight.dealership.dao.VehicleDAOMySqlImpl;
import com.pluralsight.dealership.model.Dealership;
import com.pluralsight.dealership.model.Vehicle;
import com.pluralsight.dealership.view.UserInterface;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.util.List;

public class Main {
    public static void main(String[] args) {
       // UserInterface ui = new UserInterface();
       // ui.display();
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost/dealership");
        dataSource.setUsername(args[0]);
        dataSource.setPassword(args[1]);

        UserInterface ui = new UserInterface(dataSource);
        ui.display();

//        VehicleDAOMySqlImpl vehicleManager = new VehicleDAOMySqlImpl(dataSource);
//        //DealershipDAOMySqlImpl dealershipDAOMySql = new Dealer
//        DealershipDaoMySqlImpl dealershipManager = new DealershipDaoMySqlImpl(dataSource);
//
//        List<Vehicle> vehicles = vehicleManager.getAllVehicles();
//
//        //vehicles.forEach(System.out::println);
//
//        vehicles = vehicleManager.getVehiclesInPriceRange(10000, 20000);
//        vehicles = dealershipManager.findAllVehiclesByDealership(1);
//        List<Dealership> dealerships = dealershipManager.getAllDealerships();
//        vehicles.forEach(System.out::println);
//        dealerships.forEach(System.out::println);
    }
}