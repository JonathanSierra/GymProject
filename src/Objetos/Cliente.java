/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Objetos;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author xdand
 */
public class Cliente {
    private int id;
    private String nombre;
    private String direccion;
    private int cedula;
    private int telefono;
    private String membresia;
    private int valorMembresia;
    private String estadoMembresia;
    private String fecha;
    private String estadodePago;


    public Cliente(int id, int cedula, String nombre, String direccion, int telefono, String membresia, int valorMembresia,String estadoMembresia,  String estadodePago) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.cedula = cedula;
        this.telefono = telefono;
        this.membresia = membresia;
        this.estadoMembresia = estadoMembresia;
        this.valorMembresia = valorMembresia;
     
        this.estadodePago = estadodePago;
        fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                .format(Calendar.getInstance().getTime());
       
    }
    
    public int getId() {
        return id;
    }

    public String getFecha() {
        return fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public int getCedula() {
        return cedula;
    }

    public int getTelefono() {
        return telefono;
    }

    public String getMembresia() {
        return membresia;
    }
    
    public String getEstadoMembresia(){
        return estadoMembresia;
    }

    public int getValorMembresia() {
        return valorMembresia;
    }

  

    public String getEstadodePago() {
        return estadodePago;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public void setMembresia(String membresia) {
        this.membresia = membresia;
    }

    public void setValorMembresia(int valorMembresia) {
        this.valorMembresia = valorMembresia;
    }

    public void setEstadoMembresia(String estadoMembresia) {
        this.estadoMembresia = estadoMembresia;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setEstadodePago(String estadodePago) {
        this.estadodePago = estadodePago;
    }

    
    
    
    
}
