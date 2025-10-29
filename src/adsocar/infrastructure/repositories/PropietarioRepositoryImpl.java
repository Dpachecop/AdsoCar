/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adsocar.infrastructure.repositories;

/**
 *
 * @author danielpacheco
 */


import adsocar.domain.entities.Propietario; // Imports actualizados
import adsocar.domain.repositories.IPropietarioRepository;
import adsocar.infrastructure.database.SqliteConnectionManager;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PropietarioRepositoryImpl implements IPropietarioRepository {

    @Override
    public void guardar(Propietario propietario) {
        String sql = "INSERT INTO propietarios (nombreCompleto, identificacion, numeroTelefonico, direccion) VALUES (?, ?, ?, ?)";

        try (Connection conn = SqliteConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, propietario.getNombreCompleto());
            pstmt.setString(2, propietario.getIdentificacion());
            pstmt.setString(3, propietario.getNumeroTelefonico());
            pstmt.setString(4, propietario.getDireccion());
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error al guardar el propietario: " + e.getMessage());
        }
    }

    @Override
    public Optional<Propietario> obtenerPorId(int id) {
        String sql = "SELECT * FROM propietarios WHERE id = ?";
        
        try (Connection conn = SqliteConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Propietario propietario = mapearDesdeResultSet(rs);
                return Optional.of(propietario);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener propietario por ID: " + e.getMessage());
        }
        
        return Optional.empty();
    }

    @Override
    public List<Propietario> obtenerTodos() {
        List<Propietario> propietarios = new ArrayList<>();
        String sql = "SELECT * FROM propietarios";
        
        try (Connection conn = SqliteConnectionManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                propietarios.add(mapearDesdeResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los propietarios: " + e.getMessage());
        }
        
        return propietarios;
    }

    @Override
    public void actualizar(Propietario propietario) {
        String sql = "UPDATE propietarios SET nombreCompleto = ?, identificacion = ?, "
                   + "numeroTelefonico = ?, direccion = ? WHERE id = ?";
        
        try (Connection conn = SqliteConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, propietario.getNombreCompleto());
            pstmt.setString(2, propietario.getIdentificacion());
            pstmt.setString(3, propietario.getNumeroTelefonico());
            pstmt.setString(4, propietario.getDireccion());
            pstmt.setInt(5, propietario.getId());
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar el propietario: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM propietarios WHERE id = ?";
        
        try (Connection conn = SqliteConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar el propietario: " + e.getMessage());
        }
    }

    private Propietario mapearDesdeResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String nombre = rs.getString("nombreCompleto");
        String identificacion = rs.getString("identificacion");
        String telefono = rs.getString("numeroTelefonico");
        String direccion = rs.getString("direccion");
        
        return new Propietario(id, nombre, identificacion, telefono, direccion);
    }
}