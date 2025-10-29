/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adsocar.domain.repositories;

/**
 *
 * @author danielpacheco
 */

import adsocar.domain.entities.Cita;
import adsocar.domain.enums.EstadoCita;
import java.util.List;
import java.util.Optional;

public interface ICitaRepository {
    void guardar(Cita cita);
    Optional<Cita> obtenerPorId(int id);
    List<Cita> obtenerTodas();
    List<Cita> obtenerPorClienteId(int clienteId);
    List<Cita> obtenerPorEstado(EstadoCita estado);
    void actualizar(Cita cita);
}