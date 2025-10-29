/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adsocar.domain.repositories;

/**
 *
 * @author danielpacheco
 */

import adsocar.domain.entities.Usuario;
import java.util.List;
import java.util.Optional;

public interface IUsuarioRepository {
    void guardar(Usuario usuario);
    Optional<Usuario> obtenerPorId(int id);
    Optional<Usuario> obtenerPorNombreUsuario(String nombreUsuario);
    List<Usuario> obtenerTodos();
    void actualizar(Usuario usuario);
    void eliminar(int id);
}