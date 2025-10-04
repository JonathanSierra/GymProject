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
    static String url = "jdbc:mysql://localhost:3306/autolavado_db";
    static String user = "root";
    static String pass = "uck7jivl";
    
    public static Connection conectar(){
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(url, user, pass);
            System.out.println("Conexion a la base de datos exitosa!");
        }catch(SQLException e){
            System.out.println("Error de conexion con la base de datos");
            e.printStackTrace();
        }
        return conn;
    }
}
