package com.example.parkingsystem.Objects;

public class Registration {
//             first_name": "ali",
//            "last_name": "hamza",
//            "email": "test@test.com",
//            "contact_number": "",

    public String first_name;
    public String  last_name;
    public String email;
    public String contact_number;
    public int garage_number;
    public String vehicle_number;



    public String receipt_handle;

    public Registration(String first_name, String last_name, String email, String contact_number, int garage_number, String vehicle_number) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.contact_number = contact_number;
        this.garage_number = garage_number;
        this.vehicle_number = vehicle_number;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    public int getGarage_number() {
        return garage_number;
    }

    public void setGarage_number(int garage_number) {
        this.garage_number = garage_number;
    }

    public String getVehicle_number() {
        return vehicle_number;
    }

    public void setVehicle_number(String vehicle_number) {
        this.vehicle_number = vehicle_number;
    }


    public String getReceipt_handle() {
        return receipt_handle;
    }

    public void setReceipt_handle(String receipt_handle) {
        this.receipt_handle = receipt_handle;
    }

    @Override
    public String toString() {
        return "Registration{" +
                "first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email + '\'' +
                ", contact_number='" + contact_number + '\'' +
                ", garage_number=" + garage_number +
                ", vehicle_number='" + vehicle_number + '\'' +
                '}';
    }
}
