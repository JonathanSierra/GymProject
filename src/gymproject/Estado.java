/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gymproject;

/**
 *
 * @author xdand
 */
public class Estado {
    private String estado;
    private Cliente cliente;

    public Estado(String estado, Cliente cliente) {
        this.estado = estado;
        this.cliente = cliente;
    }

    public String getEstado() {
        return estado;
    }

    public Cliente getCliente() {
        return cliente;
    }
    

    
}
