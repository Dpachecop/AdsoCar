/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adsocar.domain.entities;

import adsocar.domain.enums.EstadoCita;
import java.time.LocalDateTime;

/**
 *
 * @author danielpacheco
 */

public class Cita {

    private int id;
    private int vehiculoId; // FK a Vehiculo
    private int clienteId;  // FK a Usuario
    private LocalDateTime fechaSolicitud;
    private LocalDateTime fechaCita; // Null al inicio, la pone el Admin
    private EstadoCita estado;

    public Cita() {
    }

    public Cita(int id, int vehiculoId, int clienteId, LocalDateTime fechaSolicitud, LocalDateTime fechaCita, EstadoCita estado) {
        this.id = id;
        this.vehiculoId = vehiculoId;
        this.clienteId = clienteId;
        this.fechaSolicitud = fechaSolicitud;
        this.fechaCita = fechaCita;
        this.estado = estado;
    }

    // --- Getters y Setters ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVehiculoId() {
        return vehiculoId;
    }

    public void setVehiculoId(int vehiculoId) {
        this.vehiculoId = vehiculoId;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public LocalDateTime getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(LocalDateTime fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public LocalDateTime getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(LocalDateTime fechaCita) {
        this.fechaCita = fechaCita;
    }

    public EstadoCita getEstado() {
        return estado;
    }

    public void setEstado(EstadoCita estado) {
        this.estado = estado;
    }
}