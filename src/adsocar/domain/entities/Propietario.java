/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adsocar.domain.entities;

/**
 *
 * @author danielpacheco
 */

public class Propietario {

    private int id;
    private String nombreCompleto;
    private String identificacion; // ID, Cédula
    private String numeroTelefonico;
    private String direccion;

    public Propietario() {
    }

    public Propietario(int id, String nombreCompleto, String identificacion, String numeroTelefonico, String direccion) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.identificacion = identificacion;
        this.numeroTelefonico = numeroTelefonico;
        this.direccion = direccion;
    }

    // --- Getters y Setters ---
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNumeroTelefonico() {
        return numeroTelefonico;
    }

    public void setNumeroTelefonico(String numeroTelefonico) {
        this.numeroTelefonico = numeroTelefonico;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}