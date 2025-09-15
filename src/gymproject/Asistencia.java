/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gymproject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author jonat
 */
public class Asistencia {
    private String dia;
    private int hora;
    private Cliente cliente;

    public Asistencia(String dia, int hora,Cliente cliente) {
        this.dia = dia;
        this.hora = hora;
        this.cliente=cliente;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public String getDia() {
        return dia;
    }

    public int getHora() {
        return hora;
    }
    
    
    

    
}
