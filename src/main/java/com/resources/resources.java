package com.resources;
import com.example.parkingsystem.Objects.Registration;
import com.example.parkingsystem.Objects.parking;
import com.example.parkingsystem.localDatabase.Database;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import java.sql.SQLException;

import static com.example.parkingsystem.Controller.Controller.*;

@Path("/end-point")
public class resources {

//    {
//        "first_name":"obaid",
//            "last_name":"khan",
//            "email":"batool12547@gmail.com",
//            "contact_number":"23456787654",
//            "garage_number":1,
//            "vehicle_number":"jga-5877"
//    }
    @POST
    @Path("/registration")
    public Response Registration(String payload) throws Exception {
        try {
            Registration new_entry = new Gson().fromJson(payload, Registration.class);

            int result = doRegistration(new_entry);

            if (result == 1) {
                return Response.status(400).entity("in valid input").build();
            } else if (result == 2) {
                return Response.ok("already registered").build();
            } else if (result == 3) {
                return Response.ok("successfully registered").build();
            } else {
                return Response.status(400).entity("exception, couldn't enter register you please try again").build();
            }

        } catch (Exception ex) {
            return Response.status(400).entity(ex.toString()).build();
        }
    }

    @POST
    @Path("parking")
    public Response Parking(String payload) throws Exception {
        try {
            parking new_status = new Gson().fromJson(payload, parking.class);
            int result = doParking(new_status);

            if (result == 1) {
                return Response.status(400).entity("invalid data").build();
            } else if (result == 2) {
                return Response.ok().status(200).entity("you have already parked in").build();
            } else if (result == 3) {
                return Response.ok().status(200).entity("you have already parked out").build();
            } else if (result == 4) {
                return Response.ok().status(200).entity("you have successfully parked in").build();
            } else if (result == 5) {
                return Response.ok().status(200).entity("you have successfully parked out").build();
            } else if (result == 6) {
                return Response.status(400).entity("invalid").build();
            } else if (result == 8) {
                return Response.status(400).entity("first register your car then do parking").build();
            } else {
                return Response.status(400).entity(result + "couldn't park the  act try again please").build();
            }
        } catch (Exception ex) {
            return Response.status(400).entity(ex.toString()).build();
        }
    }

    @GET
    @Path("park_history")
    public Response History(@QueryParam("vehicle-name") String name) throws SQLException {
        try
        {
            Database db = new Database();
            int check = db.check_registration(name);
            if( check == 1)
            {
                return Response.status(200).entity("this car is not registered yet").build();
            }
            else
            {
                String output = carHistory(name);
                System.out.println(output);
                if(output != "")
                {
                    return Response.ok().status(400).entity(output).build();
                }
                else
                {
                    return Response.ok().status(400).entity("not parked yet, hence no parking history").build();
                }

            }

        }
        catch(Exception ex)
        {
            return Response.status(200).entity(ex.toString()).build();
        }
    }
}

