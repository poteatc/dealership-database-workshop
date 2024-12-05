package com.pluralsight;

import com.pluralsight.dealership.dao.VehicleDAOMySqlImpl;
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

        VehicleDAOMySqlImpl dataManager = new VehicleDAOMySqlImpl(dataSource);
        //DealershipDAOMySqlImpl dealershipDAOMySql = new Dealer

        List<Vehicle> vehicles = dataManager.getAllVehicles();

        //vehicles.forEach(System.out::println);

        vehicles = dataManager.getVehiclesInPriceRange(10000, 20000);
        vehicles.forEach(System.out::println);
    }
}