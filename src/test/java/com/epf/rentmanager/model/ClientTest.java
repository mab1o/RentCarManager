package com.epf.rentmanager.model;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class ClientTest {
    @Test
    public void is_create_corectly(){
        Client client = new Client(1, "Nom", "Prenom", "email", LocalDate.of(2020,1,1));
        assertEquals(1,client.getId());
    }
}
