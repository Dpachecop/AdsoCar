/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package adsocar.presentation.admin;
import adsocar.presentation.admin.AddVehiculo;
import adsocar.presentation.admin.AdminScreen;// Para el botón Salir
import adsocar.presentation.admin.MenuAdminGestion;
import adsocar.domain.entities.Vehiculo; // <-- AÑADIDO
import adsocar.domain.repositories.IVehiculoRepository; // <-- AÑADIDO
import adsocar.infrastructure.repositories.VehiculoRepositoryImpl; // <-- AÑADIDO
import java.awt.BorderLayout; // <-- AÑADIDO
import java.awt.Color; // <-- AÑADIDO
import java.awt.Dimension; // <-- AÑADIDO
import java.awt.Font; // <-- AÑADIDO
import java.awt.Image; // <-- AÑADIDO
import java.util.List; // <-- AÑADIDO
import javax.swing.Box; // <-- AÑADIDO
import javax.swing.BoxLayout; // <-- AÑADIDO
import javax.swing.ImageIcon; // <-- AÑADIDO
import javax.swing.JButton; // <-- AÑADIDO
import javax.swing.JLabel; // <-- AÑADIDO
import javax.swing.JOptionPane; // <-- AÑADIDO
import javax.swing.JPanel; // <-- AÑADIDO
import javax.swing.border.EmptyBorder;
/**
 *
 * @author USER
 */
public class AdminScreenVehiculos extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(AdminScreenVehiculos.class.getName());
private IVehiculoRepository vehiculoRepo;
    /**
     * Creates new form AdminScreenVehiculos
     */
    public AdminScreenVehiculos() {
        initComponents();
        
        // 1. Inicializar Repositorio
        this.vehiculoRepo = new VehiculoRepositoryImpl();

        // 2. Configurar el panel contenedor
        // Usamos BoxLayout en eje Y para apilar las tarjetas verticalmente
        panelContenidos.setLayout(new BoxLayout(panelContenidos, BoxLayout.Y_AXIS));
        
        // 3. Cargar los vehículos de la BD
        cargarVehiculos();
        
        // --- ACCIONES DE BOTONES (Como antes) ---
        
        btnAñadirVehiculos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAñadirVehiculosActionPerformed(evt);
            }
        });
        
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        
        btnUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuariosActionPerformed(evt);
            }
        });
        
        // Estilos para los botones
        btnAñadirVehiculos.setOpaque(true);
        btnAñadirVehiculos.setContentAreaFilled(true);
        btnAñadirVehiculos.setBorderPainted(false);
        btnAñadirVehiculos.setFocusPainted(false);
        
        btnSalir.setOpaque(true);
        btnSalir.setContentAreaFilled(true);
        btnSalir.setBorderPainted(false);
        btnSalir.setFocusPainted(false);
        
        btnUsuarios.setOpaque(true);
        btnUsuarios.setContentAreaFilled(true);
        btnUsuarios.setBorderPainted(false);
        btnUsuarios.setFocusPainted(false);

        // --- FIN DE CÓDIGO MODIFICADO ---
    }
    
    private JPanel crearTarjetaVehiculo(Vehiculo vehiculo) {
        // --- 1. El Panel Principal de la Tarjeta ---
        JPanel tarjeta = new JPanel(new BorderLayout(15, 15));
        tarjeta.setBorder(new EmptyBorder(10, 10, 10, 10));
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setMaximumSize(new Dimension(Integer.MAX_VALUE, 140)); // Altura fija
        tarjeta.setMinimumSize(new Dimension(600, 140));
        tarjeta.setPreferredSize(new Dimension(600, 140));
        
        // --- 2. Panel de Imagen (Izquierda) ---
        JLabel lblImagen = new JLabel();
        lblImagen.setPreferredSize(new Dimension(160, 100));
        
        // Cargamos la primera imagen del vehículo
        String rutaImagen = "adsocar/assets/carrito_placeholder.png"; // Imagen por defecto
        if (vehiculo.getRutasImagenes() != null && !vehiculo.getRutasImagenes().isEmpty()) {
            rutaImagen = vehiculo.getRutasImagenes().get(0);
        }

        try {
            ImageIcon icon = new ImageIcon(rutaImagen);
            // Redimensionamos la imagen para que quepa
            Image img = icon.getImage().getScaledInstance(160, 100, Image.SCALE_SMOOTH);
            lblImagen.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            logger.log(java.util.logging.Level.WARNING, "No se pudo cargar la imagen: " + rutaImagen, e);
            // Si falla, se queda con la imagen placeholder (o vacío)
            lblImagen.setText("Sin Imagen");
        }
        tarjeta.add(lblImagen, BorderLayout.WEST);

        // --- 3. Panel de Información (Centro) ---
        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
        panelInfo.setBackground(Color.WHITE);
        
        JLabel lblModelo = new JLabel("Modelo: " + vehiculo.getMarca() + " " + vehiculo.getModelo());
        lblModelo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        
        JLabel lblAnio = new JLabel("Año: " + vehiculo.getAnio());
        lblAnio.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        JLabel lblKm = new JLabel("Kilometraje: " + vehiculo.getKilometraje() + " km");
        lblKm.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        JLabel lblPrecio = new JLabel("Precio: $" + String.format("%,.0f", vehiculo.getPrecio()));
        lblPrecio.setFont(new Font("Segoe UI", Font.BOLD, 14));

        panelInfo.add(lblModelo);
        panelInfo.add(Box.createRigidArea(new Dimension(0, 5)));
        panelInfo.add(lblAnio);
        panelInfo.add(lblKm);
        panelInfo.add(Box.createRigidArea(new Dimension(0, 5)));
        panelInfo.add(lblPrecio);
        
        tarjeta.add(panelInfo, BorderLayout.CENTER);

        // --- 4. Panel de Botones (Derecha) ---
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));
        panelBotones.setBackground(Color.WHITE);

        JButton btnEditar = new JButton("EDITAR INFORMACIÓN");
        aplicarEstiloBoton(btnEditar, new Color(0, 102, 204));
        
        JButton btnEliminar = new JButton("ELIMINAR DEL CATÁLOGO");
        aplicarEstiloBoton(btnEliminar, new Color(0, 102, 204));
        
        // Acción del botón Editar
        btnEditar.addActionListener(e -> {
            // Lógica futura para editar
            JOptionPane.showMessageDialog(this, "Funcionalidad 'Editar' para Vehículo ID: " + vehiculo.getId() + " (aún no implementada).");
        });
        
        // Acción del botón Eliminar
        btnEliminar.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, 
                "¿Seguro que quieres eliminar el " + vehiculo.getMarca() + " " + vehiculo.getModelo() + "?", 
                "Confirmar Eliminación", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.WARNING_MESSAGE);
            
            if (confirm == JOptionPane.YES_OPTION) {
                eliminarVehiculo(vehiculo.getId());
            }
        });

        panelBotones.add(btnEditar);
        panelBotones.add(Box.createRigidArea(new Dimension(0, 10)));
        panelBotones.add(btnEliminar);
        
        tarjeta.add(panelBotones, BorderLayout.EAST);

        return tarjeta;
    }
    
    private void aplicarEstiloBoton(JButton boton, Color colorFondo) {
        boton.setBackground(colorFondo);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        boton.setOpaque(true);
        boton.setContentAreaFilled(true);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
    }
    
    /**
     * Lógica para eliminar un vehículo.
     */
    private void eliminarVehiculo(int id) {
        try {
            vehiculoRepo.eliminar(id);
            JOptionPane.showMessageDialog(this, "Vehículo eliminado con éxito.", "Eliminado", JOptionPane.INFORMATION_MESSAGE);
            // Recargamos el catálogo
            cargarVehiculos();
        } catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, "Error al eliminar vehículo", e);
            JOptionPane.showMessageDialog(this, "Error al eliminar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void cargarVehiculos() {
        try {
            List<Vehiculo> vehiculos = vehiculoRepo.obtenerTodos();
            
            // Limpiamos el panel por si acaso
            panelContenidos.removeAll();
            
            if (vehiculos.isEmpty()) {
                JLabel lblVacio = new JLabel("No hay vehículos registrados en el catálogo.");
                lblVacio.setFont(new Font("Arial", Font.ITALIC, 16));
                lblVacio.setBorder(new EmptyBorder(20, 20, 20, 20));
                panelContenidos.add(lblVacio);
            } else {
                // Creamos una tarjeta por cada vehículo
                for (Vehiculo vehiculo : vehiculos) {
                    JPanel tarjeta = crearTarjetaVehiculo(vehiculo);
                    panelContenidos.add(tarjeta);
                    // Añadimos un espacio entre tarjetas
                    panelContenidos.add(Box.createRigidArea(new Dimension(0, 10)));
                }
            }
            
            // Refrescamos la UI
            panelContenidos.revalidate();
            panelContenidos.repaint();
            
        } catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, "Error al cargar vehículos", e);
            JOptionPane.showMessageDialog(this, "Error al cargar catálogo: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void btnAñadirVehiculosActionPerformed(java.awt.event.ActionEvent evt) {
        new AddVehiculo().setVisible(true);
        this.dispose(); 
    }
    
    
    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {
        new AdminScreen().setVisible(true); // Vuelve a la pantalla principal de Admin
        this.dispose();
    }
private void btnUsuariosActionPerformed(java.awt.event.ActionEvent evt) {
        new MenuAdminGestion().setVisible(true); // Va a la gestión de usuarios
        this.dispose();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        btnUsuarios = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btnAñadirVehiculos = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        scrollPaneCatalogo = new javax.swing.JScrollPane();
        panelContenidos = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(0, 102, 204));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnUsuarios.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnUsuarios.setText("USUARIOS");
        jPanel3.add(btnUsuarios, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 141, 31));

        btnSalir.setBackground(new java.awt.Color(255, 51, 51));
        btnSalir.setForeground(new java.awt.Color(255, 255, 255));
        btnSalir.setText("SALIR");
        jPanel3.add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(23, 425, 133, 34));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adsocar/assets/adsocar_letters.png"))); // NOI18N
        jLabel4.setText("jLabel4");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 170, 73));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 170, 480));

        jLabel1.setText("Añade, Edita y Elimina informacion de los vehiculos");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, -1, -1));

        btnAñadirVehiculos.setBackground(new java.awt.Color(0, 102, 204));
        btnAñadirVehiculos.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAñadirVehiculos.setForeground(new java.awt.Color(255, 255, 255));
        btnAñadirVehiculos.setText("AÑADIR VEHICULO");
        jPanel1.add(btnAñadirVehiculos, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 20, 180, 40));

        jLabel6.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel6.setText("Gestion de Vehiculos");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 20, 250, 20));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("CÁTALOGO DE VEHICULOS"));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        scrollPaneCatalogo.setViewportView(panelContenidos);

        jPanel2.add(scrollPaneCatalogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 670, 300));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 130, 690, 330));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 910, 480));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new AdminScreenVehiculos().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAñadirVehiculos;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnUsuarios;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel panelContenidos;
    private javax.swing.JScrollPane scrollPaneCatalogo;
    // End of variables declaration//GEN-END:variables
}
