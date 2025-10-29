/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adsocar.infrastructure.repositories;

/**
 *
 * @author danielpacheco
 */


import adsocar.domain.entities.Usuario;
import adsocar.domain.enums.RolUsuario;
import adsocar.domain.repositories.IUsuarioRepository;
import adsocar.infrastructure.database.SqliteConnectionManager;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioRepositoryImpl implements IUsuarioRepository {

    @Override
    public void guardar(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombreUsuario, contrasenaHash, rol, nombreCompleto, telefono, email, direccion) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = SqliteConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, usuario.getNombreUsuario());
            pstmt.setString(2, usuario.getContrasenaHash());
            // Guardamos el Enum como su nombre en String
            pstmt.setString(3, usuario.getRol().name()); 
            pstmt.setString(4, usuario.getNombreCompleto());
            pstmt.setString(5, usuario.getTelefono());
            pstmt.setString(6, usuario.getEmail());
            pstmt.setString(7, usuario.getDireccion());
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error al guardar el usuario: " + e.getMessage());
        }
    }

    @Override
    public Optional<Usuario> obtenerPorId(int id) {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        
        try (Connection conn = SqliteConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapearDesdeResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener usuario por ID: " + e.getMessage());
        }
        
        return Optional.empty();
    }

    @Override
    public Optional<Usuario> obtenerPorNombreUsuario(String nombreUsuario) {
        String sql = "SELECT * FROM usuarios WHERE nombreUsuario = ?";
        
        try (Connection conn = SqliteConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, nombreUsuario);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapearDesdeResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener usuario por nombreUsuario: " + e.getMessage());
        }
        
        return Optional.empty();
    }

    @Override
    public List<Usuario> obtenerTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        
        try (Connection conn = SqliteConnectionManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                usuarios.add(mapearDesdeResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los usuarios: " + e.getMessage());
        }
        
        return usuarios;
    }

    @Override
    public void actualizar(Usuario usuario) {
        String sql = "UPDATE usuarios SET nombreUsuario = ?, contrasenaHash = ?, rol = ?, "
                   + "nombreCompleto = ?, telefono = ?, email = ?, direccion = ? WHERE id = ?";
        
        try (Connection conn = SqliteConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, usuario.getNombreUsuario());
            pstmt.setString(2, usuario.getContrasenaHash());
            pstmt.setString(3, usuario.getRol().name());
            pstmt.setString(4, usuario.getNombreCompleto());
            pstmt.setString(5, usuario.getTelefono());
            pstmt.setString(6, usuario.getEmail());
            pstmt.setString(7, usuario.getDireccion());
            pstmt.setInt(8, usuario.getId());
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar el usuario: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        
        try (Connection conn = SqliteConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar el usuario: " + e.getMessage());
        }
    }

    private Usuario mapearDesdeResultSet(ResultSet rs) throws SQLException {
        return new Usuario(
            rs.getInt("id"),
            rs.getString("nombreUsuario"),
            rs.getString("contrasenaHash"),
            // Convertimos el String de la BD de nuevo a Enum
            RolUsuario.valueOf(rs.getString("rol")), 
            rs.getString("nombreCompleto"),
            rs.getString("telefono"),
            rs.getString("email"),
            rs.getString("direccion")
        );
    }
}