/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package adsocar.presentation.admin;
import adsocar.presentation.admin.AdminScreen;
import adsocar.presentation.admin.AdminScreenVehiculos;
import adsocar.domain.entities.Usuario;
import adsocar.domain.repositories.IUsuarioRepository;
import adsocar.infrastructure.repositories.UsuarioRepositoryImpl;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.table.DefaultTableModel;
/**
 *s
 * @author USER
 */
public class MenuAdminGestion extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MenuAdminGestion.class.getName());
private IUsuarioRepository usuarioRepo;
    private DefaultTableModel tableModel;
    /**
     * Creates new form MainMenuAdmin
     */
    public MenuAdminGestion() {
        initComponents();
        
         // --- CÓDIGO MODIFICADO ---
        
        // 1. Instanciar repositorio
        this.usuarioRepo = new UsuarioRepositoryImpl();

        // 2. Configurar la tabla y el menú
        configurarTabla();
        
        // 3. Cargar los datos
        cargarUsuarios();

        // --- Lógica de botones (como la tenías) ---
        
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        
        btnVehiculos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVehiculosActionPerformed(evt);
            }
        });
        
        // Estilos para los botones
        btnSalir.setOpaque(true);
        btnSalir.setContentAreaFilled(true);
        btnSalir.setBorderPainted(false);
        btnSalir.setFocusPainted(false);
        
        btnVehiculos.setOpaque(true);
        btnVehiculos.setContentAreaFilled(true);
        btnVehiculos.setBorderPainted(false);
        btnVehiculos.setFocusPainted(false); 
    }   
    
    private void cargarUsuarios() {
        // Limpiamos la tabla
        tableModel.setRowCount(0);
        
        try {
            List<Usuario> usuarios = usuarioRepo.obtenerTodos();
            for (Usuario u : usuarios) {
                // Añadimos los datos en el orden de las columnas
                tableModel.addRow(new Object[]{
                    u.getId(),
                    u.getNombreCompleto(),
                    u.getNombreUsuario(),
                    u.getEmail(),
                    u.getRol().name() // .name() convierte el Enum a String
                });
            }
        } catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, "Error al cargar usuarios", e);
            JOptionPane.showMessageDialog(this, "Error al cargar la lista de usuarios.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    private void configurarTabla() {
        // Asignamos un modelo de tabla por defecto
        tableModel = new DefaultTableModel() {
            // Hacemos que las celdas no sean editables
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        // Definimos las columnas que SÍ vamos a mostrar
        tableModel.addColumn("ID"); // Lo ocultaremos, pero lo necesitamos
        tableModel.addColumn("Nombre Completo");
        tableModel.addColumn("Nombre Usuario");
        tableModel.addColumn("Email");
        tableModel.addColumn("Rol");

        jTable1.setModel(tableModel);

        // Ocultamos la columna ID (columna 0)
        jTable1.getColumnModel().getColumn(0).setMinWidth(0);
        jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(0).setWidth(0);

        // Crear el menú contextual (popup)
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem menuItemEliminar = new JMenuItem("Eliminar Usuario");
        popupMenu.add(menuItemEliminar);

        // Asignar el listener de clic derecho a la tabla
        jTable1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                // Verificar si es el clic derecho
                if (e.isPopupTrigger()) {
                    int fila = jTable1.rowAtPoint(e.getPoint());
                    if (fila >= 0) {
                        // Seleccionar la fila sobre la que se hizo clic
                        jTable1.setRowSelectionInterval(fila, fila);
                        // Mostrar el menú en la posición del clic
                        popupMenu.show(e.getComponent(), e.getX(), e.getY());
                    }
                }
            }
        });

        // Asignar la acción al botón "Eliminar" del menú
        menuItemEliminar.addActionListener(e -> eliminarUsuarioSeleccionado());
    }
    
    private void eliminarUsuarioSeleccionado() {
        int filaSeleccionada = jTable1.getSelectedRow();
        
        // Si no hay fila seleccionada, no hacemos nada
        if (filaSeleccionada == -1) {
            return;
        }

        try {
            // Obtenemos el ID de la columna oculta (columna 0)
            int idUsuario = (int) tableModel.getValueAt(filaSeleccionada, 0);
            String nombreUsuario = (String) tableModel.getValueAt(filaSeleccionada, 1);

            // Pedimos confirmación
            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Estás seguro de que deseas eliminar a '" + nombreUsuario + "'?",
                    "Confirmar Eliminación",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            if (confirm == JOptionPane.YES_OPTION) {
                // Eliminar de la base de datos
                usuarioRepo.eliminar(idUsuario);
                
                // Mostrar éxito y recargar la tabla
                JOptionPane.showMessageDialog(this, "Usuario eliminado con éxito.");
                cargarUsuarios(); // Recargamos los datos
            }
            
        } catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, "Error al eliminar usuario", e);
            JOptionPane.showMessageDialog(this, "Error al eliminar el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {
        new AdminScreen().setVisible(true);
        this.dispose();
    }
    
    private void btnVehiculosActionPerformed(java.awt.event.ActionEvent evt) {
        new AdminScreenVehiculos().setVisible(true);
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
        btnVehiculos = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(0, 102, 204));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnVehiculos.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnVehiculos.setText("VEHICULOS");
        jPanel3.add(btnVehiculos, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 141, 31));

        btnSalir.setBackground(new java.awt.Color(255, 51, 51));
        btnSalir.setForeground(new java.awt.Color(255, 255, 255));
        btnSalir.setText("SALIR");
        jPanel3.add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 480, 133, 34));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adsocar/assets/adsocar_letters.png"))); // NOI18N
        jLabel4.setText("jLabel4");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 170, 73));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 170, 550));

        jLabel2.setBackground(new java.awt.Color(153, 153, 153));
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 50, -1, -1));

        jLabel3.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel3.setText("Lista de Usuarios");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 90, -1, -1));

        jLabel5.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel5.setText("Gestión de Usuarios");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 20, 250, 20));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Nombre", "Apellido", "Correo", "Contraseña"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 140, 710, 310));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 939, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
        java.awt.EventQueue.invokeLater(() -> new MenuAdminGestion().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnVehiculos;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
