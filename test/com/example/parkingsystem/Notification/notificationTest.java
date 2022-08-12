package com.example.parkingsystem.Notification;

import org.junit.jupiter.api.Test;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.*;
import static com.example.parkingsystem.Notification.notification.sendEmail;

class notificationTest {
    @Test
    void testMail() throws MessagingException, UnsupportedEncodingException {
        assertTrue(sendEmail("batool12547@gmail.com","testing","hey there, \n my name is batool"));
    }

    @Test
    void testMail2() throws MessagingException, UnsupportedEncodingException {
        assertTrue(sendEmail("batool12547@gmail.com","","hey there, \n no subject test"));
    }
}