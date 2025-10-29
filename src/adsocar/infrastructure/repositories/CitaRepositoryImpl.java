/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adsocar.infrastructure.repositories;

/**
 *
 * @author danielpacheco
 */


import adsocar.domain.entities.Cita;
import adsocar.domain.enums.EstadoCita;
import adsocar.domain.repositories.ICitaRepository;
import adsocar.infrastructure.database.SqliteConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CitaRepositoryImpl implements ICitaRepository {

    @Override
    public void guardar(Cita cita) {
        String sql = "INSERT INTO citas (vehiculoId, clienteId, fechaSolicitud, fechaCita, estado) "
                   + "VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = SqliteConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, cita.getVehiculoId());
            pstmt.setInt(2, cita.getClienteId());
            
            // Guardamos LocalDateTime como un String en formato ISO
            pstmt.setString(3, cita.getFechaSolicitud().toString());
            
            // Manejamos el valor nulo de fechaCita
            String fechaCitaStr = (cita.getFechaCita() == null) ? null : cita.getFechaCita().toString();
            pstmt.setString(4, fechaCitaStr);
            
            pstmt.setString(5, cita.getEstado().name());
            
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error al guardar la cita: " + e.getMessage());
        }
    }

    @Override
    public Optional<Cita> obtenerPorId(int id) {
        String sql = "SELECT * FROM citas WHERE id = ?";
        
        try (Connection conn = SqliteConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapearDesdeResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener cita por ID: " + e.getMessage());
        }
        
        return Optional.empty();
    }

    @Override
    public List<Cita> obtenerTodas() {
        List<Cita> citas = new ArrayList<>();
        String sql = "SELECT * FROM citas";
        
        try (Connection conn = SqliteConnectionManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                citas.add(mapearDesdeResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener todas las citas: " + e.getMessage());
        }
        
        return citas;
    }

    @Override
    public List<Cita> obtenerPorClienteId(int clienteId) {
        List<Cita> citas = new ArrayList<>();
        String sql = "SELECT * FROM citas WHERE clienteId = ?";
        
        try (Connection conn = SqliteConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, clienteId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                citas.add(mapearDesdeResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener citas por clienteId: " + e.getMessage());
        }
        
        return citas;
    }

    @Override
    public List<Cita> obtenerPorEstado(EstadoCita estado) {
        List<Cita> citas = new ArrayList<>();
        String sql = "SELECT * FROM citas WHERE estado = ?";
        
        try (Connection conn = SqliteConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, estado.name());
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                citas.add(mapearDesdeResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener citas por estado: " + e.getMessage());
        }
        
        return citas;
    }

    @Override
    public void actualizar(Cita cita) {
        String sql = "UPDATE citas SET vehiculoId = ?, clienteId = ?, fechaSolicitud = ?, "
                   + "fechaCita = ?, estado = ? WHERE id = ?";
        
        try (Connection conn = SqliteConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, cita.getVehiculoId());
            pstmt.setInt(2, cita.getClienteId());
            pstmt.setString(3, cita.getFechaSolicitud().toString());
            
            String fechaCitaStr = (cita.getFechaCita() == null) ? null : cita.getFechaCita().toString();
            pstmt.setString(4, fechaCitaStr);
            
            pstmt.setString(5, cita.getEstado().name());
            pstmt.setInt(6, cita.getId());
            
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar la cita: " + e.getMessage());
        }
    }
    
    private Cita mapearDesdeResultSet(ResultSet rs) throws SQLException {
        // Convertimos el String (ISO) de la BD de nuevo a LocalDateTime
        LocalDateTime fechaSol = LocalDateTime.parse(rs.getString("fechaSolicitud"));
        
        String fechaCitaDb = rs.getString("fechaCita");
        LocalDateTime fechaCita = (fechaCitaDb == null) ? null : LocalDateTime.parse(fechaCitaDb);
        
        return new Cita(
            rs.getInt("id"),
            rs.getInt("vehiculoId"),
            rs.getInt("clienteId"),
            fechaSol,
            fechaCita,
            EstadoCita.valueOf(rs.getString("estado"))
        );
    }
}