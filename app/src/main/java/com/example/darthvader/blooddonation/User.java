package com.example.darthvader.blooddonation;


public class User {
    String username;
    long phNo;
    String email;
    String bloodGroup;
    String city;
    String state;
    int pincode;
    User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public User() {
    }

    public String getUsername() {
        return username;
    }

    public long getPhNo() {
        return phNo;
    }

    public String getEmail() {
        return email;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public int getPincode() {
        return pincode;
    }

    public User(String username, long phNo, String email, String bloodGroup, String city, String state, int pincode) {
        this.username = username;
        this.phNo = phNo;
        this.email = email;
        this.bloodGroup = bloodGroup;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
    }
}
