/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package adsocar.presentation.user;
import adsocar.domain.entities.Usuario;
import adsocar.domain.entities.Vehiculo;
import adsocar.domain.repositories.IVehiculoRepository;
import adsocar.infrastructure.repositories.VehiculoRepositoryImpl;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author danielpacheco
 */
public class UserScreen extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(UserScreen.class.getName());
private Usuario usuario; // El usuario que inició sesión
    private IVehiculoRepository vehiculoRepo;
    /**
     * Creates new form UserScreen
     */
    
    
    public UserScreen() {
        initComponents();
        // Este constructor no debe cargar datos, es solo para el diseñador.
    }
    
    public UserScreen(Usuario usuario) {
        initComponents();
        
        
     
        
        this.usuario = usuario;
        this.vehiculoRepo = new VehiculoRepositoryImpl();
        
        // Configurar el panel para la cuadrícula de 3 columnas
        // (0 filas = dinámicas, 3 columnas, 10px de espacio h/v)
        panelGridContenidos.setLayout(new GridLayout(0, 3, 10, 10));
        panelGridContenidos.setBackground(Color.WHITE);
        
        // Poner el título de bienvenida
        jLabel3.setText("¡Bienvenido, " + usuario.getNombreUsuario() + "!");
        
        // Cargar los vehículos
        cargarVehiculos();
    }
private void cargarVehiculos() {
        try {
            List<Vehiculo> vehiculos = vehiculoRepo.obtenerTodos();
            panelGridContenidos.removeAll();
            
            if (vehiculos.isEmpty()) {
                panelGridContenidos.add(new JLabel("No hay vehículos en el catálogo por ahora."));
            } else {
                for (Vehiculo v : vehiculos) {
                    JPanel tarjeta = crearTarjetaVehiculo(v);
                    panelGridContenidos.add(tarjeta);
                }
            }
            
            panelGridContenidos.revalidate();
            panelGridContenidos.repaint();
            
        } catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, "Error al cargar vehículos", e);
            JOptionPane.showMessageDialog(this, "Error al cargar catálogo.");
        }
    }

private JPanel crearTarjetaVehiculo(Vehiculo vehiculo) {
        // 1. Panel principal de la tarjeta
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS)); // Apilado vertical
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY),
            new EmptyBorder(10, 10, 10, 10)
        ));
        tarjeta.setBackground(new Color(245, 245, 245));
        tarjeta.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Poner manito
        
        // 2. Imagen
        JLabel lblImagen = new JLabel();
        lblImagen.setPreferredSize(new Dimension(220, 120));
        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
        
        String rutaImagen = "adsocar/assets/carrito_placeholder.png";
        if (vehiculo.getRutasImagenes() != null && !vehiculo.getRutasImagenes().isEmpty()) {
            rutaImagen = vehiculo.getRutasImagenes().get(0);
        }
        
        try {
            ImageIcon icon = new ImageIcon(rutaImagen);
            Image img = icon.getImage().getScaledInstance(220, 120, Image.SCALE_SMOOTH);
            lblImagen.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            lblImagen.setText("Sin Imagen");
        }
        
        // 3. Título (Marca y Modelo)
        JLabel lblTitulo = new JLabel(vehiculo.getMarca() + " " + vehiculo.getModelo());
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setBorder(new EmptyBorder(10, 0, 5, 0));
        
        // 4. Precio
        JLabel lblPrecio = new JLabel("$ " + String.format("%,.0f", vehiculo.getPrecio()) + " COP");
        lblPrecio.setFont(new Font("Arial", Font.PLAIN, 14));

        // 5. Añadir componentes a la tarjeta
        tarjeta.add(lblImagen);
        tarjeta.add(lblTitulo);
        tarjeta.add(lblPrecio);
        
        // 6. AÑADIR ACCIÓN DE CLIC
        tarjeta.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Abrir la pantalla de detalles (UserCatalog)
                new UserCatalog(usuario, vehiculo).setVisible(true);
                // Cerrar esta pantalla
                dispose(); 
            }
        });
        
        return tarjeta;
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
        jLabel6 = new javax.swing.JLabel();
        scrollPaneCatalogo = new javax.swing.JScrollPane();
        panelGridContenidos = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(0, 102, 204));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adsocar/assets/circulo48mpx.png"))); // NOI18N

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adsocar/assets/iconfinder-contat-us-40notification-4211863_115056 (2).png"))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Bienvenido a AdsoCar");

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

        jLabel6.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel6.setText("Catalogo");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, -1));

        javax.swing.GroupLayout panelGridContenidosLayout = new javax.swing.GroupLayout(panelGridContenidos);
        panelGridContenidos.setLayout(panelGridContenidosLayout);
        panelGridContenidosLayout.setHorizontalGroup(
            panelGridContenidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 878, Short.MAX_VALUE)
        );
        panelGridContenidosLayout.setVerticalGroup(
            panelGridContenidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 338, Short.MAX_VALUE)
        );

        scrollPaneCatalogo.setViewportView(panelGridContenidos);

        jPanel1.add(scrollPaneCatalogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 880, 340));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
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
        java.awt.EventQueue.invokeLater(() -> new UserScreen().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel panelGridContenidos;
    private javax.swing.JScrollPane scrollPaneCatalogo;
    // End of variables declaration//GEN-END:variables
}
