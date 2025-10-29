/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adsocar.domain.repositories;

/**
 *
 * @author danielpacheco
 */

import adsocar.domain.entities.Propietario;
import java.util.List;
import java.util.Optional;

public interface IPropietarioRepository {
    void guardar(Propietario propietario);
    Optional<Propietario> obtenerPorId(int id);
    List<Propietario> obtenerTodos();
    void actualizar(Propietario propietario);
    void eliminar(int id);
}