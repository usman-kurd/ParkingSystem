package com.example.parkingsystem.Validation;

import com.example.parkingsystem.Objects.Registration;
import com.example.parkingsystem.Objects.parking;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class validation {


    public boolean checkRegistration(Registration obj)
    {
        //Regular Expression
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";
        //Compile regular expression to get the pattern
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(obj.email);



        if(obj.first_name == null || obj.last_name == null || obj.vehicle_number == null || obj.contact_number == null || obj.email ==  null || obj.garage_number == 0)
        {
            return false; // invalid data
        }
        else if(obj.first_name.equals("") || obj.last_name.equals("") || obj.vehicle_number.equals("") || obj.contact_number.equals("") || obj.email.equals(""))
        {
            return false; // invalid data
        }
        else if((obj.vehicle_number.length() > 8 )||(obj.contact_number.length() != 11))
        {
            System.out.println(obj.contact_number.length());
            return false; // invalid data
        }
        else if(!matcher.matches())
        {
            return false ; // invalid email
        }
        else
        {
            return true;
        }
    }

    public boolean checkParking(parking obj) throws NullPointerException
    {
        try {
            boolean b = false;
            if ((obj.park_status.toLowerCase().equals("in") || obj.park_status.toLowerCase().equals("out")) && (!obj.vehicle_number.equals("")) && ((obj.vehicle_number != null)) && ((obj.park_status != null)) && (obj.vehicle_number.length() <= 8)) {
                System.out.println("didn't recognized");
                b = true;
            }
            return b;
        }
        catch(Exception ex)
        {
            //System.out.printf("exception in validation");
            // ex.printStackTrace();
            return false;
        }
    }
}

