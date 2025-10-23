/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Objetos;

/**
 *
 * @author xdand
 */
public class Membresia {
    private int idMembresia;
    private String membresia;
    private int PrecioMembresia;
    private int membresiaDuracion;

    public Membresia(int idMembresia, String membresia, int PrecioMembresia, int membresiaDuracion) {
        this.idMembresia = idMembresia;
        this.membresia = membresia;
        this.PrecioMembresia = PrecioMembresia;
        this.membresiaDuracion = membresiaDuracion;
    }

    public int getIdMembresia() {
        return idMembresia;
    }

    public String getMembresia() {
        return membresia;
    }

    public int getPrecioMembresia() {
        return PrecioMembresia;
    }

    public int getMembresiaDuracion() {
        return membresiaDuracion;
    }
    
    

    

   

   
    
    
}
