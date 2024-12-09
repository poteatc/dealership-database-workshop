package com.pluralsight;

import com.pluralsight.dealership.view.UserInterface;
import org.apache.commons.dbcp2.BasicDataSource;

public class Main {
    public static void main(String[] args) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost/dealership");
        dataSource.setUsername(args[0]);
        dataSource.setPassword(args[1]);

        UserInterface ui = new UserInterface(dataSource);
        ui.display();
    }
}