/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adsocar.infrastructure.repositories;

/**
 *
 * @author danielpacheco
 */

import adsocar.domain.entities.Vehiculo;
import adsocar.domain.repositories.IVehiculoRepository;
import adsocar.infrastructure.database.SqliteConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class VehiculoRepositoryImpl implements IVehiculoRepository {

    // Usaremos un delimitador simple (;) para guardar las listas de rutas
    private static final String DELIMITER = ";";

    @Override
    public void guardar(Vehiculo vehiculo) {
        String sql = "INSERT INTO vehiculos (marca, modelo, anio, precio, kilometraje, propietarioId, rutasImagenes, rutasVideos) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = SqliteConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, vehiculo.getMarca());
            pstmt.setString(2, vehiculo.getModelo());
            pstmt.setInt(3, vehiculo.getAnio());
            pstmt.setDouble(4, vehiculo.getPrecio());
            pstmt.setInt(5, vehiculo.getKilometraje());
            pstmt.setInt(6, vehiculo.getPropietarioId());
            
            // Convertimos la Lista de rutas a un solo String
            String rutasImg = (vehiculo.getRutasImagenes() == null) ? "" : String.join(DELIMITER, vehiculo.getRutasImagenes());
            String rutasVid = (vehiculo.getRutasVideos() == null) ? "" : String.join(DELIMITER, vehiculo.getRutasVideos());
            
            pstmt.setString(7, rutasImg);
            pstmt.setString(8, rutasVid);
            
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error al guardar el vehiculo: " + e.getMessage());
        }
    }

    @Override
    public Optional<Vehiculo> obtenerPorId(int id) {
        String sql = "SELECT * FROM vehiculos WHERE id = ?";
        
        try (Connection conn = SqliteConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapearDesdeResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener vehiculo por ID: " + e.getMessage());
        }
        
        return Optional.empty();
    }

    @Override
    public List<Vehiculo> obtenerTodos() {
        List<Vehiculo> vehiculos = new ArrayList<>();
        String sql = "SELECT * FROM vehiculos";
        
        try (Connection conn = SqliteConnectionManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                vehiculos.add(mapearDesdeResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los vehiculos: " + e.getMessage());
        }
        
        return vehiculos;
    }

    @Override
    public void actualizar(Vehiculo vehiculo) {
        String sql = "UPDATE vehiculos SET marca = ?, modelo = ?, anio = ?, precio = ?, "
                   + "kilometraje = ?, propietarioId = ?, rutasImagenes = ?, rutasVideos = ? "
                   + "WHERE id = ?";
        
        try (Connection conn = SqliteConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, vehiculo.getMarca());
            pstmt.setString(2, vehiculo.getModelo());
            pstmt.setInt(3, vehiculo.getAnio());
            pstmt.setDouble(4, vehiculo.getPrecio());
            pstmt.setInt(5, vehiculo.getKilometraje());
            pstmt.setInt(6, vehiculo.getPropietarioId());
            
            String rutasImg = (vehiculo.getRutasImagenes() == null) ? "" : String.join(DELIMITER, vehiculo.getRutasImagenes());
            String rutasVid = (vehiculo.getRutasVideos() == null) ? "" : String.join(DELIMITER, vehiculo.getRutasVideos());
            
            pstmt.setString(7, rutasImg);
            pstmt.setString(8, rutasVid);
            pstmt.setInt(9, vehiculo.getId());
            
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar el vehiculo: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM vehiculos WHERE id = ?";
        
        try (Connection conn = SqliteConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar el vehiculo: " + e.getMessage());
        }
    }
    
    private Vehiculo mapearDesdeResultSet(ResultSet rs) throws SQLException {
        // Convertimos el String de la BD de nuevo a Lista
        String rutasImgDb = rs.getString("rutasImagenes");
        String rutasVidDb = rs.getString("rutasVideos");
        
        List<String> rutasImg = (rutasImgDb == null || rutasImgDb.isEmpty()) 
                                ? new ArrayList<>() 
                                : Arrays.asList(rutasImgDb.split(DELIMITER));
                                
        List<String> rutasVid = (rutasVidDb == null || rutasVidDb.isEmpty())
                                ? new ArrayList<>()
                                : Arrays.asList(rutasVidDb.split(DELIMITER));

        return new Vehiculo(
            rs.getInt("id"),
            rs.getString("marca"),
            rs.getString("modelo"),
            rs.getInt("anio"),
            rs.getDouble("precio"),
            rs.getInt("kilometraje"),
            rs.getInt("propietarioId"),
            rutasImg,
            rutasVid
        );
    }
}