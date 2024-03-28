package com.epf.rentmanager.dao;

import com.epf.rentmanager.exception.DaoException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.testng.Assert.assertThrows;


public class ClientDaoTest {
    // Pas de base de données de mock

    /*
    @Autowired
    private ClientDao clientDao;

    @Test
    public void delete_a_client_that_do_not_exist(){
        long idClient = 1999999L;
        assertThrows(DaoException.class, () -> clientDao.delete(idClient));
    }*/
}
