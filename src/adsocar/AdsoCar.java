package adsocar;

// ... (deja los imports de infrastructure y presentation)
import adsocar.infrastructure.database.SqliteConnectionManager;
import adsocar.presentation.login.InitialScreen; // Importa tu pantalla inicial

/**
 *
 * @author danielpacheco
 */
public class AdsoCar {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        System.out.println("--- INICIANDO ADSOCAR ---");

        // 1. Inicializar la base de datos (Crea las tablas si no existen)
        // ESTO ES FUNDAMENTAL y debe ir primero.
        System.out.println("\n[PASO 1: Inicializando Base de Datos...]");
        SqliteConnectionManager.inicializarBaseDeDatos();
        System.out.println("Base de Datos lista.");

        // 2. BORRA todo el código de prueba (crear Propietario, crear Usuario, etc.)
        // que tenías aquí.
        
        // 3. Iniciar la aplicación gráfica (GUI)
        // Usamos EventQueue.invokeLater para asegurar que la GUI se ejecute
        // en el hilo correcto de Swing.
        java.awt.EventQueue.invokeLater(() -> {
            new InitialScreen().setVisible(true);
        });
        
        System.out.println("\n--- APLICACIÓN INICIADA ---");
    }
    
}