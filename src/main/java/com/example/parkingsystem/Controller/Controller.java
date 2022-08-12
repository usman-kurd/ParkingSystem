package com.example.parkingsystem.Controller;
import com.example.parkingsystem.Objects.Registration;
import com.example.parkingsystem.Objects.parking;
import com.example.parkingsystem.Validation.validation;
import com.example.parkingsystem.localDatabase.Database;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import javax.mail.MessagingException;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.example.parkingsystem.Queue.sqsQueue.*;

public class Controller
{

//    public static void main(String[] args)  throws JsonProcessingException
//    {
//        Registration new_entry = new Registration("namef","namel","email","12345678",1,"qwe-gfd");
//        ObjectMapper mapper = new ObjectMapper();
//        //Converting the Object to JSONString
//        String jsonString = mapper.writeValueAsString(new_entry);
//        System.out.println();
//        try
//        {
//            SendRegistrationMessage(jsonString);
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//        //Registration s = ReadRegistrationMessage("regis_queue");
//    }
//    public boolean regis2(Registration new_entry) throws JsonProcessingException {
//        ObjectMapper mapper = new ObjectMapper();
//        //Converting the Object to JSONString
//        String jsonString = mapper.writeValueAsString(new_entry);
//        System.out.println();
//
//        return true;
//        //Registration s = ReadRegistrationMessage("regis_queue");
//
//    }

//    public static int doParking(parking new_status) throws SQLException {
//        try
//        {
//        validation v = new validation();
//        if(!v.checkParking(new_status))
//        {
//            System.out.print("has returned here in controller");
//            return 1; //  invalid data"
//        }
//        else
//        {
//            //System.out.println(new_status.toString());
//            Database db = new Database();
//            int b = db.ParkingInfo(new_status);
//
//            if (b == 1)
//            {
//                return 2;      // already parked in
//            }
//            else if (b == 2)
//            {
//                return 3;       // already parked out
//            }
//            else if (b == 3)
//            {
//                return 4;        //successfully parked in
//            }
//            else if (b == 4)
//            {
//                return 5;         //successfully parked out
//            }
//                else if (b == 5)
//                {
//                    return 6;         //Response.status(400).entity("invalid").build();
//                }
//                else if (b == 6)
//                {
//                    System.out.println("error in parking status");
//                    return 7;          //Response.status(400).entity("error in parking status").build();
//                }
//            else    // if (b == 8)
//            {
//                return 8 ;           //Response.status(400).entity("first u need to register your car then u can park").build();
//            }
//                else if (b == 9)
//                {
//                    System.out.println("invalid data");
//                    return 6; //Response.status(400).entity("invalid data").build();
//                }
//                else
//                {
//                    System.out.println("error in parking info");
//                    return 6;// Response.status(400).entity("error in parking info").build();
//                }
//        }
//        }
//        catch(Exception ex)
//        {
//            //ex.printStackTrace();
//            System.out.println("exception");
//            return 7; // exception
//        }
//    }

    public static int doRegistration(Registration new_entry) throws SQLException, MessagingException, UnsupportedEncodingException, JsonProcessingException {

        validation v = new validation();
        if(!v.checkRegistration(new_entry))
        {
            return 1; //  invalid info
        }
        else
        {
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(new_entry);
            SendMessage("regis-queue",jsonString);
            int size = getQueueSize("regis-queue");
            System.out.println(size);
            for(int i=0; i< size; i++)
            {
                Registration s =(Registration) ReadMessage("regis-queue");
                Database db = new Database();
                int b = 0;
                try
                {
//                    b = db.RegisterInfo(new_entry);
                    b = db.RegisterInfo(s);
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
                if (b == 1)
                {
                    deleteMessages("regis-queue",s.receipt_handle);
                    return 2;         //   already registered
                }
                else  // if (b == 2)
                {
                    deleteMessages("regis-queue",s.receipt_handle);
                    return 3;        //    successfully registered

                }
            }
            return 0; ///// handle it
        }
    }

    public static int doParking(parking new_status) throws SQLException, MessagingException, UnsupportedEncodingException, JsonProcessingException {
        validation v = new validation();
        if(!v.checkParking(new_status))
        {
            System.out.print("has returned here in controller");
            return 1; //  invalid data"
        }
        else
        {
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(new_status);
            SendMessage("park-queue",jsonString);
            int size = getQueueSize("park-queue");
            System.out.println(size);

            for(int i=0; i< size; i++)
            {
                parking p = (parking) ReadMessage("park-queue");
                Database db = new Database();
                int b = 0;
                try
                {
                    b = db.ParkingInfo(p);
                }
                catch (Exception e)
                {
                    throw new RuntimeException(e);
                }

                if (b == 1)
                {
                    deleteMessages("park-queue",p.receiptHandle);
                    return 2;      // already parked in
                }
                else if (b == 2)
                {
                    deleteMessages("park-queue",p.receiptHandle);
                    return 3;       // already parked out
                }
                else if (b == 3)
                {
                    deleteMessages("park-queue",p.receiptHandle);
                    return 4;       //successfully parked in
                }
                else if (b == 4)
                {
                    deleteMessages("park-queue",p.receiptHandle);
                    return 5;        //successfully parked out
                }
                else    // if (b == 8)
                {
                    deleteMessages("park-queue",p.receiptHandle);
                    return 8 ;       //Response.status(400).entity("first u need to register your car then u can park").build();
                }

            }
        }
        return 10;
    }

    public static String carHistory(String car_name) throws SQLException
    {
        Database db = new Database();
        ArrayList<parking> array = db.check_history(car_name);
        String out= "";
        for (parking p : array)
        {
            out= out + p.getVehicle_number()+ "\t"+ p.getDate()+ "\t"+p.getTime()+ "\t"+p.getPark_status() +"\n";
        }
        return out;
    }
}