package com.pluralsight.dealership.dao;

import com.pluralsight.dealership.model.Dealership;

import javax.sql.DataSource;
import java.util.List;

public class DealershipDaoMySqlImpl implements DealershipDao {
    private DataSource dataSource;

    public DealershipDaoMySqlImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Dealership findDealershipById() {
        return null;
    }

    @Override
    public List<Dealership> getAllDealerships() {
        return List.of();
    }
}
