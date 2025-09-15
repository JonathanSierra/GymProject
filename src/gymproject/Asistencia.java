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
    private int MiembroId;
    private String fecha;
    private String asistio;

    public Asistencia(int MiembroId, String asistio) {
        this.MiembroId = MiembroId;
        this.asistio = asistio;
        this.fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
    }

    public int getMiembroId() {
        return MiembroId;
    }

    public String getFecha() {
        return fecha;
    }

    public String getAsistio() {
        return asistio;
    }
}
