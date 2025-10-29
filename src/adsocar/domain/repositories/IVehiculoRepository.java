/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adsocar.domain.repositories;

/**
 *
 * @author danielpacheco
 */

import adsocar.domain.entities.Vehiculo;
import java.util.List;
import java.util.Optional;

public interface IVehiculoRepository {
    void guardar(Vehiculo vehiculo);
    Optional<Vehiculo> obtenerPorId(int id);
    List<Vehiculo> obtenerTodos();
    void actualizar(Vehiculo vehiculo);
    void eliminar(int id);
}