package adsocar;

// Imports de nuestras capas
import adsocar.domain.entities.Propietario;
import adsocar.domain.entities.Usuario;
import adsocar.domain.enums.RolUsuario;
import adsocar.domain.repositories.IPropietarioRepository;
import adsocar.domain.repositories.IUsuarioRepository;
import adsocar.infrastructure.database.SqliteConnectionManager;
import adsocar.infrastructure.repositories.PropietarioRepositoryImpl;
import adsocar.infrastructure.repositories.UsuarioRepositoryImpl;

// Imports de Java
import java.util.List;
import java.util.Optional;

/**
 *
 * @author danielpacheco
 */
public class AdsoCar {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        System.out.println("--- INICIANDO PRUEBA DE ADSOGAR ---");

        // 1. Inicializar la base de datos (Crea las tablas si no existen)
        System.out.println("\n[PASO 1: Inicializando Base de Datos...]");
        SqliteConnectionManager.inicializarBaseDeDatos();
        System.out.println("Base de Datos lista.");

        // --- PRUEBA CON PROPIETARIOS ---
        
        System.out.println("\n[PASO 2: Probando Repositorio de Propietarios...]");
        // Instanciamos la implementación de la infraestructura
        IPropietarioRepository propietarioRepo = new PropietarioRepositoryImpl();
        
        // 2. Crear un nuevo propietario
        Propietario nuevoProp = new Propietario();
        nuevoProp.setNombreCompleto("Isacc Bosiio");
        nuevoProp.setIdentificacion("123456789");
        nuevoProp.setNumeroTelefonico("3001234567");
        nuevoProp.setDireccion("Calle Falsa 123");
        
        // 3. Guardar el propietario
        System.out.println("Guardando a: " + nuevoProp.getNombreCompleto());
        propietarioRepo.guardar(nuevoProp);
        System.out.println("Propietario guardado.");

        // 4. Obtener todos los propietarios y mostrarlos
        System.out.println("\nListando todos los propietarios en la BD:");
        List<Propietario> propietarios = propietarioRepo.obtenerTodos();
        if (propietarios.isEmpty()) {
            System.out.println("No se encontraron propietarios.");
        } else {
            for (Propietario p : propietarios) {
                System.out.println("- ID: " + p.getId() + ", Nombre: " + p.getNombreCompleto() + ", ID: " + p.getIdentificacion());
            }
        }
        
        // --- PRUEBA CON USUARIOS ---
        
        System.out.println("\n[PASO 3: Probando Repositorio de Usuarios...]");
        IUsuarioRepository usuarioRepo = new UsuarioRepositoryImpl();

        // 5. Crear un nuevo usuario (Cliente)
        Usuario nuevoCliente = new Usuario();
        nuevoCliente.setNombreUsuario("Saaamuel_Jaraba");
        // En una app real, esto sería un HASH (ej. BCrypt)
        nuevoCliente.setContrasenaHash("micontraseña123"); 
        nuevoCliente.setRol(RolUsuario.CLIENTE);
        nuevoCliente.setNombreCompleto("Saaamuel Jaraba");
        nuevoCliente.setEmail("samuel@correo.com");
        nuevoCliente.setTelefono("30198765432");
        nuevoCliente.setDireccion("Av. Siempre Viva 7424");

        // 6. Guardar el usuario
        System.out.println("\nGuardando a: " + nuevoCliente.getNombreUsuario());
        usuarioRepo.guardar(nuevoCliente);
        System.out.println("Usuario guardado.");

        // 7. Buscar al usuario por nombre de usuario
        System.out.println("\nBuscando al usuario 'Saaamuel Jaraba':");
        Optional<Usuario> usuarioEncontrado = usuarioRepo.obtenerPorNombreUsuario("Saaamuel_Jaraba");
        
        if (usuarioEncontrado.isPresent()) {
            Usuario u = usuarioEncontrado.get();
            System.out.println("¡Encontrado! -> " + u.getNombreCompleto() + " (Rol: " + u.getRol() + ")");
        } else {
            System.out.println("No se encontró al usuario 'Saamuel '.");
        }

        System.out.println("\n--- PRUEBA FINALIZADA ---");
        
        // 8. (Opcional) Cerrar la conexión si sabes que la app termina aquí
        // SqliteConnectionManager.closeConnection(); 
        // No la cerramos para que puedas seguir haciendo pruebas si quieres.
    }
    
}