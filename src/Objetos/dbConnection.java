/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Objetos;

import java.sql.*;

/**
 *
 * @author jonat
 */
public class dbConnection {
    static String url = "jdbc:mysql://localhost:3306/Gimnasio";
    static String user = "root";
    static String pass = "andrestuki32";
    
    public dbConnection() {
    }
    
    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return user;
    }

    public String getPassword() {
        return pass;
    }
}

