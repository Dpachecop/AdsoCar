/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adsocar.domain.entities;

import java.util.List;

/**
 *
 * @author danielpacheco
 */


public class Vehiculo {

    private int id;
    private String marca;
    private String modelo;
    private int anio;
    private double precio;
    private int kilometraje;
    private int propietarioId; // FK a Propietario
    
    // Almacenaremos solo las rutas a los archivos
    private List<String> rutasImagenes;
    private List<String> rutasVideos;

    public Vehiculo() {
    }

    public Vehiculo(int id, String marca, String modelo, int anio, double precio, int kilometraje, int propietarioId, List<String> rutasImagenes, List<String> rutasVideos) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.precio = precio;
        this.kilometraje = kilometraje;
        this.propietarioId = propietarioId;
        this.rutasImagenes = rutasImagenes;
        this.rutasVideos = rutasVideos;
    }

    // --- Getters y Setters ---
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getKilometraje() {
        return kilometraje;
    }

    public void setKilometraje(int kilometraje) {
        this.kilometraje = kilometraje;
    }

    public int getPropietarioId() {
        return propietarioId;
    }

    public void setPropietarioId(int propietarioId) {
        this.propietarioId = propietarioId;
    }

    public List<String> getRutasImagenes() {
        return rutasImagenes;
    }

    public void setRutasImagenes(List<String> rutasImagenes) {
        this.rutasImagenes = rutasImagenes;
    }

    public List<String> getRutasVideos() {
        return rutasVideos;
    }

    public void setRutasVideos(List<String> rutasVideos) {
        this.rutasVideos = rutasVideos;
    }
}