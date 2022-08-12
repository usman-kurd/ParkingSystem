package com.example.parkingsystem.localDatabase;
import com.example.parkingsystem.Objects.Registration;
import com.example.parkingsystem.Objects.parking;

import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.UUID;

import static com.example.parkingsystem.Notification.notification.sendEmail;

public class Database {

    Connection con = null;

    public Database() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://127.0.0.1/parkingsystem";
            con = DriverManager.getConnection(url, "root", "root");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("couldn't connect to database");
        }
    }
    //    public int RegisterInfo(Registration new_entry) throws NullPointerException {
//        try {
//            String uuid = UUID.randomUUID().toString();
//            String query1 = "INSERT INTO registration_info(first_name,last_name,email,contact_number,garage_number,vehicle_number,id)VALUES(?,?,?,?,?,?,?)";
//
//            PreparedStatement st = con.prepareStatement(query1);
//            st.setString(1, new_entry.first_name);
//            st.setString(2, new_entry.last_name);
//            st.setString(3, new_entry.email);
//            st.setString(4, new_entry.contact_number);
//            st.setInt(5, new_entry.garage_number);
//            st.setString(6, new_entry.vehicle_number);
//            st.setObject(7, uuid.toString());
//
//            if ((check_registration(new_entry.vehicle_number)) == 3) {
//                return 3; // invalid
//            } else if ((check_registration(new_entry.vehicle_number) == 2)) {
//                return 1; // already registered
//            } else {
//                int i = st.executeUpdate();
//                if (i == 1) {
//                    System.out.println("successfully entered in reservation");
//                    // send notification to the user
//
//                    try
//                    {
//                        String query = "Select email, id from registration_info where vehicle_number = ?";
//                        PreparedStatement stat = con.prepareStatement(query);
//                        stat.setString(1, new_entry.vehicle_number);
//                        ResultSet rs =stat.executeQuery();
//                        while(rs.next())
//                        {
//                            String id = rs.getString("id");
//                            String email = rs.getString("email");
//                            sendEmail(email,"Registered","u have successfully registrered , your id = "+id);
//                            return 2; // success
//                        }
//                    }
//                    catch(Exception ex)
//                    {
//                        ex.printStackTrace();
//                        return 5 ;//
//                    }
//
//
//                    return 6;
//
//                } else {
//                    System.out.println("couldn't entered data");
//                    return 3; // in-valid data
//                }
//            }
//
//        } catch (Exception ex)
//        {
//            ex.printStackTrace();
//
//            System.out.println("length "+ new_entry.contact_number +" = "+ new_entry.contact_number.length());
//            return 4; // exception
//        }
//    }
//    public int ParkingInfo(parking new_status) throws NullPointerException, SQLException {
//
////        SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
//        //       DateTimeFormatter formatter2 = new DateTimeFormat("HH:mm:ss");
////        System.out.println();
//
//
//        int check1 = check_registration(new_status.vehicle_number);
//
//        if( check1 == 1)
//        {
//            return 8; // first do registration then u can park
//        }
////        else if( check1 == 3)
////        {
////            return 9; // invalid data
////        }
//        else
//        {
//
//            try {
//
//                String query2 = "INSERT INTO parking_info(vehicle_number, park_status,date,time)VALUES(?,?,?,?)";
//
//                PreparedStatement st2 = con.prepareStatement(query2);
//                st2.setString(1, new_status.vehicle_number);
//                st2.setString(2, new_status.park_status);
//                st2.setString(3,java.time.LocalDate.now().toString());
//                st2.setString(4,java.time.LocalTime.now().toString());
//
//                int check = check_parking(new_status.vehicle_number);
//
//                if(check == 1 && new_status.park_status.toLowerCase().equals("in"))
//                {
//                    return 1 ; // you are already parked in
//                }
//                else if(check == 2 && new_status.park_status.toLowerCase().equals("out"))
//                {
//                    return 2; // already parked out
//                }
//                else if(check == 3 || (check == 1 && new_status.park_status.toLowerCase().equals("out") )||(check == 2 && new_status.park_status.toLowerCase().equals("in")) )
//                {
//                    int i = st2.executeUpdate();
//                    if (i == 1) {
//                        if(new_status.park_status.toLowerCase().equals("in"))
//                        {
//                            System.out.println("you have successfully parked in");
//                            String query = "Select email, id from registration_info where vehicle_number = ?";
//                            PreparedStatement stat = con.prepareStatement(query);
//                            stat.setString(1, new_status.vehicle_number);
//                            ResultSet rs =stat.executeQuery();
//                            while(rs.next())
//                            {
//                                String id = rs.getString("id");
//                                String email = rs.getString("email");
//                                sendEmail(email,"Parking Status","u have successfully parked in , your id = "+id);
//                                return 3; // success
//                            }
//                            System.out.println("there , 13");
//                            return 13;
//                        }
//                        else
//                        {
//                            System.out.println("you have successfully parked out");
//                            String query = "Select email, id from registration_info where vehicle_number = ?";
//                            PreparedStatement stat = con.prepareStatement(query);
//                            stat.setString(1, new_status.vehicle_number);
//                            ResultSet rs =stat.executeQuery();
//                            while(rs.next())
//                            {
//                                String id = rs.getString("id");
//                                String email = rs.getString("email");
//                                sendEmail(email,"Parking Status","u have successfully parked out , your id = "+id);
//                                return 4; // success
//                            }
//                            System.out.println("there , 14");
//                            return 14;
//                        }
//
//                    } else {
//                        System.out.println("couldn't park in the car");
//                        return 5;
//                    }
//                }
//                else
//                {
//                    return 6; // exception occur in check_parking
//                }
//
//            }
//            catch (Exception ex)
//            {
//                ex.printStackTrace();
//                return 7;
//            }
//        }
//
//    }
    public int RegisterInfo(Registration new_entry) throws NullPointerException, SQLException,  UnsupportedEncodingException, javax.mail.MessagingException {
//    try {
        String uuid = UUID.randomUUID().toString();
        String query1 = "INSERT INTO registration_info(first_name,last_name,email,contact_number,garage_number,vehicle_number,id)VALUES(?,?,?,?,?,?,?)";

        PreparedStatement st = con.prepareStatement(query1);
        st.setString(1, new_entry.first_name);
        st.setString(2, new_entry.last_name);
        st.setString(3, new_entry.email);
        st.setString(4, new_entry.contact_number);
        st.setInt(5, new_entry.garage_number);
        st.setString(6, new_entry.vehicle_number);
        st.setObject(7, uuid.toString());

        int check = check_registration(new_entry.vehicle_number);
        if (check == 2)
        {
            return 1; // already registered
        }
        else
        {
            st.executeUpdate();
            //int i = st.executeUpdate();
            //if (i == 1)
            //{
            System.out.println("successfully entered in reservation");
            // send notification to the user
//                try
//                {
            String query = "Select email, id from registration_info where vehicle_number = ?";
            PreparedStatement stat = con.prepareStatement(query);
            stat.setString(1, new_entry.vehicle_number);
            ResultSet rs =stat.executeQuery();
            while(rs.next())
            {
                String id = rs.getString("id");
                String email = rs.getString("email");
                sendEmail(email,"Registered","u have successfully registrered , your id = "+id);
                return 2; // success
            }
//                }
//                catch(Exception ex)
//                {
//                    ex.printStackTrace();
//                    return 5 ;//
//                }

            // return 6;

//            } else {
//                System.out.println("couldn't entered data");
//                return 3; // in-valid data
//            }
        }

//    }
//    catch (Exception ex)
//    {
//        ex.printStackTrace();
//
//        System.out.println("length "+ new_entry.contact_number +" = "+ new_entry.contact_number.length());
//        return 4; // exception
//    }
        return 0;
    }

    public int ParkingInfo(parking new_status) throws NullPointerException, SQLException, UnsupportedEncodingException, javax.mail.MessagingException {

        int check1 = check_registration(new_status.vehicle_number);

        if( check1 == 1)
        {
            return 8; // first do registration then u can park
        }
        else
        {
//        try
//        {

            String query2 = "INSERT INTO parking_info(vehicle_number, park_status,date,time)VALUES(?,?,?,?)";

            PreparedStatement st2 = con.prepareStatement(query2);
            st2.setString(1, new_status.vehicle_number);
            st2.setString(2, new_status.park_status);
            st2.setString(3,java.time.LocalDate.now().toString());
            st2.setString(4,java.time.LocalTime.now().toString());

            int check = check_parking(new_status.vehicle_number);

            if(check == 1 && new_status.park_status.toLowerCase().equals("in"))
            {
                return 1 ; // you are already parked in
            }
            else if(check == 2 && new_status.park_status.toLowerCase().equals("out"))
            {
                return 2; // already parked out
            }
//            else if(check == 3 || (check == 1 && new_status.park_status.toLowerCase().equals("out") )||(check == 2 && new_status.park_status.toLowerCase().equals("in")) )
//            {
            else
            {
                st2.executeUpdate();
//                int i = st2.executeUpdate();
//                if (i == 1) {
                if(new_status.park_status.toLowerCase().equals("in"))
                {
                    System.out.println("you have successfully parked in");
                    String query = "Select email, id from registration_info where vehicle_number = ?";
                    PreparedStatement stat = con.prepareStatement(query);
                    stat.setString(1, new_status.vehicle_number);
                    ResultSet rs =stat.executeQuery();
                    while(rs.next())
                    {
                        String id = rs.getString("id");
                        String email = rs.getString("email");
                        sendEmail(email,"Parking Status","u have successfully parked in , your id = "+id);
                        return 3; // success
                    }
//                        System.out.println("there , 13");
                    return 14;
                }
                else
                {
                    System.out.println("you have successfully parked out");
                    String query = "Select email, id from registration_info where vehicle_number = ?";
                    PreparedStatement stat = con.prepareStatement(query);
                    stat.setString(1, new_status.vehicle_number);
                    ResultSet rs =stat.executeQuery();
                    while(rs.next())
                    {
                        String id = rs.getString("id");
                        String email = rs.getString("email");
                        sendEmail(email,"Parking Status","u have successfully parked out , your id = "+id);
                        return 4; // success
                    }
//                        System.out.println("there , 14");
                    return 14;
                }

//                }
//                else {
//                    System.out.println("couldn't park in the car");
//                    return 5;
//                }
            }
//            else
//            {
//                return 6; // exception occur in check_parking
//            }

//        }
//        catch (Exception ex)
//        {
//            ex.printStackTrace();
//            return 7;
//        }
        }

    }

    public int check_parking(String vehicle_number) throws SQLException
    {
//        try {
        String query = "Select * from parking_info where vehicle_number = ?";
        PreparedStatement st = con.prepareStatement(query);
        st.setString(1, vehicle_number);
        ResultSet rs = st.executeQuery();
        if (rs.next() != false) // check previous parking status
        {
            System.out.println(rs.last());
            System.out.println(rs.getString("time"));
            if (rs.getString("park_status").toLowerCase().equals("in")) {
                System.out.println("here 8080");
                return 1; // already parked in
            } else //if (rs.getString("park_status").toLowerCase() =="out")
            {
                System.out.println("here 6");
                return 2; // already parked out
            }
        }
        else
        {
            System.out.println("here 1010");
            return 3;    // no in/out new entry allowed
        }
//        }
//        catch (Exception ex)
//        {
//            return 4;
//        }
    }

    public int check_registration(String vehicle_number) throws SQLException
    {
//        try {
        String check_query1 = "Select * from registration_info where vehicle_number = ?";
        PreparedStatement st3 = con.prepareStatement(check_query1);
        st3.setString(1, vehicle_number);
        ResultSet rs = st3.executeQuery();

        if (rs.next() != false)
        {
            System.out.println("here");
            return 2; // already registered
        }
        else
        {
            System.out.println("reached");
            return 1; // registration allowed or not registered
        }

//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return 3; // invalid data
//        }
    }

    public ArrayList<String> checkAt1am()
    {
        ArrayList<String> array1 = new ArrayList<String>();
        ArrayList<String> array2 = new ArrayList<String>();
        try
        {
            String query = "Select * from registration_info";
            PreparedStatement st = con.prepareStatement(query);
            ResultSet rs1 = st.executeQuery();

            while(rs1.next())
            {
                array1.add(rs1.getString("vehicle_number"));
            }

            for(String s : array1)
            {
                System.out.println("vehicles get from db : "+ s);
            }
            System.out.println("\n");

            for (String o : array1) {
                String query2 = "Select * from parking_info where vehicle_number=?";
                PreparedStatement st2 = con.prepareStatement(query2);
                st2.setString(1, o);
                ResultSet rs = st2.executeQuery();
                if (!rs.next()) // no entry in parking table yet
                {


                    System.out.println(o);
                    String  new_Query = "Select * from registration_info where vehicle_number =?";
                    PreparedStatement st3 = con.prepareStatement(new_Query);
                    st3.setString(1,o);
                    ResultSet rs3 = st3.executeQuery();
                    while(rs3.next())
                    {
                        array2.add(rs3.getString("email"));
                    }


                }
                else
                {
                    rs.last();
                    String status = rs.getString("park_status");
                    System.out.println("here2  "+ status);
                    if (!status.equals("in"))
                    {
                        String query5 = "Select * from registration_info where vehicle_number = ?";
                        PreparedStatement st5 = con.prepareStatement(query5);
                        st5.setString(1,o);
                        ResultSet rs5 = st5.executeQuery();
                        while(rs5.next())
                        {
                            System.out.println("out from parking here3  "+rs5.getString("email"));
                            array2.add(rs5.getString("email"));
                        }

                        System.out.println(o.toString());
                    }
                }
            }
            System.out.println("doing to return");
            return array2;
            //System.out.println("returned from here");
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            return array2;
        }
    }

    public ArrayList<parking> check_history(String vehicle_number)
    {
        ArrayList<parking> array = new ArrayList<parking>();
        try
        {
            String query = "Select * from parking_info where vehicle_number = ?";
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1,vehicle_number);

            ResultSet rs = st.executeQuery();
            while(rs.next())
            {
                String vehicle_no = rs.getString("vehicle_number");
                String status = rs.getString("park_status");
                String date = rs.getString("date");
                String time = rs.getString("time");

                parking p = new parking(vehicle_no,status);
                p.setDate(date);
                p.setTime(time);
                array.add(p);
            }
            return array;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }
}

