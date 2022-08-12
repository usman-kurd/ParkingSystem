package com.example.parkingsystem.Controller;

import com.example.parkingsystem.Objects.Registration;
import com.example.parkingsystem.Objects.parking;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import static com.example.parkingsystem.Controller.Controller.doParking;
import static com.example.parkingsystem.Controller.Controller.doRegistration;
import static com.example.parkingsystem.Controller.Controller.carHistory;
import static org.junit.jupiter.api.Assertions.*;
class ControllerTest {


    @Test
    void doregistration1() throws SQLException, MessagingException, UnsupportedEncodingException, JsonProcessingException {
        // invalid
        Registration test_entry = new Registration("qirat","zahra","batool12547@gmail.com","3334666146",2,"POV-9876");
        assertEquals(1,doRegistration(test_entry));
    }
    @Test
    void doregistration2() throws SQLException, MessagingException, UnsupportedEncodingException, JsonProcessingException {
        // already registered
        Registration test_entry = new Registration("qirat","zahra","batool12547@gmail.com","33346661468",2,"GTL-5877");
        assertEquals(2,doRegistration(test_entry));
    }
    @Test
    void doregistration3() throws SQLException, MessagingException, UnsupportedEncodingException, JsonProcessingException {
        // successfully registered
        Registration test_entry = new Registration("qirat","zahra","batool12547@gmail.com","33346661468",2,"XYZ-9876");
        assertEquals(3,doRegistration(test_entry));
    }
    @Test
    void doParking1() throws SQLException, MessagingException, UnsupportedEncodingException, JsonProcessingException {
        // invalid
        parking test_entry = new parking("","in");
        assertEquals(1,doParking(test_entry));
    }
    @Test
    void doParking2() throws SQLException, MessagingException, UnsupportedEncodingException, JsonProcessingException {
        // already parked in
        parking test_entry = new parking("LJK-0110","in");
        assertEquals(2,doParking(test_entry));
    }
    @Test
    void doParking3() throws SQLException, MessagingException, UnsupportedEncodingException, JsonProcessingException {
        // already parked out
        parking test_entry = new parking("GTL-5877","out");
        assertEquals(3,doParking(test_entry));
    }
    @Test
    void doParking4() throws SQLException, MessagingException, UnsupportedEncodingException, JsonProcessingException {
        // successfully parked in
        parking test_entry = new parking("GHJ-9876","in");
        assertEquals(4,doParking(test_entry));
    }
    @Test
    void doParking5() throws SQLException, MessagingException, UnsupportedEncodingException, JsonProcessingException {
        // successfully parked out
        parking test_entry = new parking("GHJ-9876","out");
        assertEquals(5,doParking(test_entry));
    }
    @Test
    void doParking8() throws SQLException, MessagingException, UnsupportedEncodingException, JsonProcessingException {
        // first park yourself
        parking test_entry = new parking("ASD-456","out");
        assertEquals(8,doParking(test_entry));
    }

    @Test
    void carHistory1() throws SQLException {
        String expected = "ZXC-5877\t2022-08-12\t16:06:03.021\tin\n" +
                "ZXC-5877\t2022-08-12\t16:06:16.029\tout\n";

        String actual = carHistory("ZXC-5877");
        assertEquals(expected,actual);
    }
}