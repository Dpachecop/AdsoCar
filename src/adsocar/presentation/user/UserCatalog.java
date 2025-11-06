/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package adsocar.presentation.user;
import adsocar.domain.entities.Cita;
import adsocar.domain.entities.Propietario;
import adsocar.domain.entities.Usuario;
import adsocar.domain.entities.Vehiculo;
import adsocar.domain.enums.EstadoCita;
import adsocar.domain.repositories.ICitaRepository;
import adsocar.domain.repositories.IPropietarioRepository;
import adsocar.infrastructure.repositories.CitaRepositoryImpl;
import adsocar.infrastructure.repositories.PropietarioRepositoryImpl;
import java.awt.Image;
import java.time.LocalDateTime;
import java.util.Optional;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author danielpacheco
 */
public class UserCatalog extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(UserCatalog.class.getName());
private Usuario usuario;
    private Vehiculo vehiculo;
    private IPropietarioRepository propietarioRepo;
    private ICitaRepository citaRepo;
    /**
     * Creates new form UserScreen
     */
    
    public UserCatalog() {
        initComponents();
        // ESTE CONSTRUCTOR DEBE ESTAR VACÍO (O SOLO CON INITCOMPONENTS)
        // PARA QUE EL DISEÑADOR FUNCIONE.
    }
    
    
    public UserCatalog(Usuario usuario, Vehiculo vehiculo) {
        initComponents();
        this.usuario = usuario;
        this.vehiculo = vehiculo;
        this.propietarioRepo = new PropietarioRepositoryImpl();
        this.citaRepo = new CitaRepositoryImpl();
        
        // Estilo del botón
        btnPedirCita.setOpaque(true);
        btnPedirCita.setContentAreaFilled(true);
        btnPedirCita.setBorderPainted(false);
        btnPedirCita.setFocusPainted(false);
        
        // Conectar la acción del botón
        btnPedirCita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPedirCitaActionPerformed(evt);
            }
        });
        
        // Llenar la información en la pantalla
        mostrarDetalles();
    }
    
    private void mostrarDetalles() {
        // Cargar Imagen
        String rutaImagen = "adsocar/assets/carrito_placeholder.png";
        if (vehiculo.getRutasImagenes() != null && !vehiculo.getRutasImagenes().isEmpty()) {
            rutaImagen = vehiculo.getRutasImagenes().get(0);
        }
        try {
            ImageIcon icon = new ImageIcon(rutaImagen);
            // Ajustamos al tamaño del panel (350x310)
            Image img = icon.getImage().getScaledInstance(350, 310, Image.SCALE_SMOOTH);
            lblImagenVehiculo.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            lblImagenVehiculo.setText("Imagen no disponible");
        }
        
        // Cargar Textos
        lblMarcaVehiculo.setText(vehiculo.getMarca());
        lblModeloVehiculo.setText(vehiculo.getModelo());
        lblAñoVehiculo.setText(String.valueOf(vehiculo.getAnio()));
        lblKilometrajeVehiculo.setText(String.valueOf(vehiculo.getKilometraje()) + " km");
        
        // Obtener nombre del propietario
        lblPropietario.setText(obtenerNombrePropietario(vehiculo.getPropietarioId()));
    }
    
    private String obtenerNombrePropietario(int id) {
        try {
            Optional<Propietario> p = propietarioRepo.obtenerPorId(id);
            return p.isPresent() ? p.get().getNombreCompleto() : "No disponible";
        } catch (Exception e) {
            logger.log(java.util.logging.Level.WARNING, "Error al buscar propietario", e);
            return "Error";
        }
    }
    
    private void btnPedirCitaActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            // 1. Crear la entidad Cita
            Cita nuevaCita = new Cita();
            nuevaCita.setClienteId(this.usuario.getId()); // ID del usuario que inició sesión
            nuevaCita.setVehiculoId(this.vehiculo.getId()); // ID del vehículo que se está viendo
            nuevaCita.setFechaSolicitud(LocalDateTime.now()); // La fecha y hora actual
            nuevaCita.setEstado(EstadoCita.PENDIENTE); // Estado inicial
            
            // 2. Guardar en la BD
            citaRepo.guardar(nuevaCita);
            
            // 3. Mostrar éxito y regresar
            JOptionPane.showMessageDialog(this, 
                "¡Cita solicitada con éxito! Un administrador la revisará pronto.", 
                "Cita Enviada", 
                JOptionPane.INFORMATION_MESSAGE);
            
            // Regresamos al catálogo
            new UserScreen(this.usuario).setVisible(true);
            this.dispose();
            
        } catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, "Error al solicitar cita", e);
            JOptionPane.showMessageDialog(this, "Error al guardar la cita: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
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
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblMarcaVehiculo = new javax.swing.JLabel();
        lblKilometrajeVehiculo = new javax.swing.JLabel();
        lblModeloVehiculo = new javax.swing.JLabel();
        lblAñoVehiculo = new javax.swing.JLabel();
        lblPropietario = new javax.swing.JLabel();
        btnPedirCita = new javax.swing.JButton();
        lblImagenVehiculo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(0, 102, 204));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adsocar/assets/circulo48mpx.png"))); // NOI18N

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adsocar/assets/iconfinder-contat-us-40notification-4211863_115056 (2).png"))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Detalles del Vehiculo");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 435, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addGap(12, 12, 12)
                .addComponent(jLabel2)
                .addGap(27, 27, 27))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel2))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel4)
                                .addComponent(jLabel1)))))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 910, 70));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setText("Año:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 270, -1, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setText("Marca:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 130, -1, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setText("Modelo:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 220, -1, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setText("Propietario:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 310, -1, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setText("Kilometraje:");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 170, -1, -1));

        lblMarcaVehiculo.setText("lblMarcaVehiculo");
        jPanel1.add(lblMarcaVehiculo, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 130, 140, -1));

        lblKilometrajeVehiculo.setText("lblKilometrajeVehiculo");
        jPanel1.add(lblKilometrajeVehiculo, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 170, -1, -1));

        lblModeloVehiculo.setText("lblModeloVehiculo");
        jPanel1.add(lblModeloVehiculo, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 230, -1, -1));

        lblAñoVehiculo.setText("lblAñoVehiculo");
        jPanel1.add(lblAñoVehiculo, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 280, -1, -1));

        lblPropietario.setText("lblPropietario");
        jPanel1.add(lblPropietario, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 320, -1, -1));

        btnPedirCita.setBackground(new java.awt.Color(0, 102, 204));
        btnPedirCita.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnPedirCita.setForeground(new java.awt.Color(255, 255, 255));
        btnPedirCita.setText("SOLICITAR CITA");
        jPanel1.add(btnPedirCita, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 373, 440, 40));
        jPanel1.add(lblImagenVehiculo, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 130, 350, 280));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 924, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE)
        );

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
        java.awt.EventQueue.invokeLater(() -> new UserCatalog().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPedirCita;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblAñoVehiculo;
    private javax.swing.JLabel lblImagenVehiculo;
    private javax.swing.JLabel lblKilometrajeVehiculo;
    private javax.swing.JLabel lblMarcaVehiculo;
    private javax.swing.JLabel lblModeloVehiculo;
    private javax.swing.JLabel lblPropietario;
    // End of variables declaration//GEN-END:variables
}
