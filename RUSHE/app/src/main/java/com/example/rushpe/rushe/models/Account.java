package com.example.rushpe.rushe.models;

/**
 * Written by: Alejandro Aguilar
 *
 * Account class to store user information and pass on into database
 */

public class Account {

//    Properties
    String username;
    String email;
    String name;
    String password;

    /**
     *
     * @param username        user's username
     * @param email     user's email
     * @param username  user's name
     * @param password  user's password
     */
    public Account(String username, String email, String name, String password) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    /**
     *
     * @return the user's id number
     */
    public String getUsername() { return username; }

    /**
     *
     * @return the user's name
     */
    public String getName() { return name; }

    /**
     *
     * @return the user's email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @return the user's password
     */
    public String getPassword() {
        return password;
    }

}
