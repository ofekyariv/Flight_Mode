package com.example.flight_mode;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private String email;
    private String password;

    public User (String name, String email, String password){
        this.name=name;
        this.email=email;
        this.password=password;
    }
    public String getName(){return name;}
    public void setName(String name){this.name=name;}

    public String getEmail(){return email; }
    public void setEmail(String email){this.email=email;}

    public String getPassword(){return password; }
    public void setPassword(String pass){this.password=password;}

    @Override
    public String toString() {
        return "user{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}