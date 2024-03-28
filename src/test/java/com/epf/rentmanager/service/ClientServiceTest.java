package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
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
public class ClientServiceTest {
    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientDao clientDao;

    @Test
    public void create_client_without_name(){
        Client client = new Client(0L, "","test","", LocalDate.of(2001,1,1));
        assertThrows(ServiceException.class, () ->clientService.create(client));
    }

    @Test
    public void create_client_without_surname(){
        Client client = new Client(0L, "test","","", LocalDate.of(2001,1,1));
        assertThrows(ServiceException.class, () ->clientService.create(client));
    }

    @Test
    public void delete_client_that_not_exist(){
        long idClient = 666666666L;
        assertDoesNotThrow(() -> clientService.delete(idClient));
    }

    @Test
    public void show_all_entry(){
        Client client = new Client(0L, "test","","", LocalDate.of(2001,1,1));
        assertThrows(ServiceException.class,() ->clientService.create(client));
    }

    @Test
    public void find_client_by_id() throws ServiceException, DaoException {
        long id = 2L;
        Client expectedClient = new Client(id, "John", "Doe", "john@example.com", LocalDate.of(2001, 1, 1));
        when(clientDao.findById(id)).thenReturn(expectedClient);
        Client actualClient = clientService.findById(id);
        assertEquals(expectedClient, actualClient);
    }

}
