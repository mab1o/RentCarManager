package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.LocalDate;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertThrows;

@RunWith(MockitoJUnitRunner.class)
public class VehicleServiceTest {
    @InjectMocks
    private VehicleService vehicleService;

    @Mock
    private VehicleDao vehicleDao;

    @Test
    public void create_a_vehicle(){

    }

    public void delete_a_vehicle_not_existing(){

    }

    public void delete_a_vehicle_existing(){

    }

    public void find_a_not_existing_vehicle(){

    }

    public void find_a_exist_vehicle(){}

}
