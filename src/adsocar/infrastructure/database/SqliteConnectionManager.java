/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adsocar.infrastructure.database;

/**
 *
 * @author danielpacheco
 */



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SqliteConnectionManager {

    // El archivo de la BD se llamará "adsocar.db"
    private static final String DB_URL = "jdbc:sqlite:adsocar.db";

    private static Connection connection = null;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection(DB_URL);
                System.out.println("Conexión a SQLite (adsocar.db) establecida.");
            } catch (ClassNotFoundException e) {
                System.err.println("Error al cargar el driver JDBC de SQLite: " + e.getMessage());
                throw new SQLException("Driver no encontrado", e);
            }
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Conexión a SQLite cerrada.");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }

    /**
     * Método para crear las tablas iniciales si no existen.
     */
    public static void inicializarBaseDeDatos() {
        String sqlUsuarios = "CREATE TABLE IF NOT EXISTS usuarios ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nombreUsuario TEXT NOT NULL UNIQUE,"
                + "contrasenaHash TEXT NOT NULL,"
                + "rol TEXT NOT NULL,"
                + "nombreCompleto TEXT,"
                + "telefono TEXT,"
                + "email TEXT,"
                + "direccion TEXT"
                + ");";

        String sqlPropietarios = "CREATE TABLE IF NOT EXISTS propietarios ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nombreCompleto TEXT NOT NULL,"
                + "identificacion TEXT NOT NULL UNIQUE,"
                + "numeroTelefonico TEXT,"
                + "direccion TEXT"
                + ");";

        String sqlVehiculos = "CREATE TABLE IF NOT EXISTS vehiculos ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "marca TEXT NOT NULL,"
                + "modelo TEXT NOT NULL,"
                + "anio INTEGER NOT NULL,"
                + "precio REAL NOT NULL,"
                + "kilometraje INTEGER NOT NULL,"
                + "propietarioId INTEGER NOT NULL,"
                + "rutasImagenes TEXT," 
                + "rutasVideos TEXT,"   
                + "FOREIGN KEY (propietarioId) REFERENCES propietarios (id)"
                + ");";
        
        String sqlCitas = "CREATE TABLE IF NOT EXISTS citas ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "vehiculoId INTEGER NOT NULL,"
                + "clienteId INTEGER NOT NULL,"
                + "fechaSolicitud TEXT NOT NULL," 
                + "fechaCita TEXT," // Null al inicio
                + "estado TEXT NOT NULL," 
                + "FOREIGN KEY (vehiculoId) REFERENCES vehiculos (id),"
                + "FOREIGN KEY (clienteId) REFERENCES usuarios (id)"
                + ");";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            
            stmt.execute(sqlUsuarios);
            stmt.execute(sqlPropietarios);
            stmt.execute(sqlVehiculos);
            stmt.execute(sqlCitas);
            
            System.out.println("Tablas de la base de datos (adsocar.db) verificadas/creadas.");
            
        } catch (SQLException e) {
            System.err.println("Error al inicializar la base de datos: " + e.getMessage());
        }
    }
}