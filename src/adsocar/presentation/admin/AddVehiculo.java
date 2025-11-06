/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package adsocar.presentation.admin;
import adsocar.domain.entities.Propietario;
import adsocar.domain.entities.Vehiculo;
import adsocar.domain.repositories.IPropietarioRepository;
import adsocar.domain.repositories.IVehiculoRepository;
import adsocar.infrastructure.repositories.PropietarioRepositoryImpl;
import adsocar.infrastructure.repositories.VehiculoRepositoryImpl;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author USER
 */
public class AddVehiculo extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(AddVehiculo.class.getName());
    private IVehiculoRepository vehiculoRepo;
    private List<String> rutasImagenesSeleccionadas;
    private IPropietarioRepository propietarioRepo;
    /**
     * Creates new form AddVehiculo
     */
    
    private class PropietarioItem {
        private int id;
        private String nombre;

        public PropietarioItem(int id, String nombre) {
            this.id = id;
            this.nombre = nombre;
        }

        public int getId() {
            return id;
        }

        @Override
        public String toString() {
            // Esto es lo que verá el usuario en el ComboBox
            return nombre; 
        }
    }
    
    public AddVehiculo() {
        initComponents();
     // 1. Instanciar repositorios
        this.vehiculoRepo = new VehiculoRepositoryImpl();
        this.propietarioRepo = new PropietarioRepositoryImpl(); // Instanciamos el nuevo repo
        this.rutasImagenesSeleccionadas = new ArrayList<>();

        // 2. Conectar el botón "AÑADIR VEHICULO"
        btnAñadirVehiculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAñadirVehiculoActionPerformed(evt);
            }
        });
        
        // 3. Conectar el botón "AÑADIR IMAGEN"
        btnAñadirImagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAñadirImagenActionPerformed(evt);
            }
        });
        
        // 4. Estilos de botones
        btnAñadirVehiculo.setOpaque(true);
        btnAñadirVehiculo.setContentAreaFilled(true);
        btnAñadirVehiculo.setBorderPainted(false);
        btnAñadirVehiculo.setFocusPainted(false);
        
        btnAñadirImagen.setOpaque(true);
        btnAñadirImagen.setContentAreaFilled(true);
        btnAñadirImagen.setBorderPainted(false);
        btnAñadirImagen.setFocusPainted(false);
        
        // 5. Cargar los propietarios en el ComboBox
        cargarPropietarios();
    }
    
    private void cargarPropietarios() {
        // Usamos un DefaultComboBoxModel para manejar los items
        DefaultComboBoxModel<PropietarioItem> model = new DefaultComboBoxModel<>();
        
        try {
            List<Propietario> propietarios = propietarioRepo.obtenerTodos();
            
            if (propietarios.isEmpty()) {
                model.addElement(new PropietarioItem(0, "No hay propietarios registrados"));
            } else {
                for (Propietario p : propietarios) {
                    // Añadimos un PropietarioItem por cada propietario
                    model.addElement(new PropietarioItem(p.getId(), p.getNombreCompleto()));
                }
            }
            
            // Asignamos el modelo al ComboBox
            comboBoxPropietario.setModel(model);
            
        } catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, "Error al cargar propietarios", e);
            // Limpiamos el modelo y añadimos un item de error
            model.removeAllElements();
            model.addElement(new PropietarioItem(0, "Error al cargar"));
            comboBoxPropietario.setModel(model);
        }
    }
    
    private void btnAñadirImagenActionPerformed(java.awt.event.ActionEvent evt) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true); 
        
        int result = fileChooser.showOpenDialog(this);
        
        if (result == JFileChooser.APPROVE_OPTION) {
            File[] files = fileChooser.getSelectedFiles();
            
            for (File file : files) {
                String path = file.getAbsolutePath();
                this.rutasImagenesSeleccionadas.add(path);
                System.out.println("Imagen añadida: " + path);
            }
            
            JOptionPane.showMessageDialog(this, 
                files.length + " imagen(es) añadida(s) con éxito.", 
                "Imágenes Cargadas", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
        
   private void btnAñadirVehiculoActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            // 1. Recoger datos de los campos de texto
            String marca = txtMarca.getText();
            String modelo = txtModelo.getText();
            
            // 2. Validar que los campos no estén vacíos
            if (marca.isEmpty() || modelo.isEmpty() || txtAño1.getText().isEmpty() ||
                txtKilometraje.getText().trim().isEmpty() || txtPrecioVehiculo.getText().isEmpty()) {
                
                JOptionPane.showMessageDialog(this, "Todos los campos de texto son obligatorios.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 3. Validar el ComboBox
            Object selectedItem = comboBoxPropietario.getSelectedItem();
            if (selectedItem == null || !(selectedItem instanceof PropietarioItem)) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un propietario.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // 4. Convertir los datos numéricos (con manejo de errores)
            int anio = Integer.parseInt(txtAño1.getText()); // Usamos txtAño1
            int kilometraje = Integer.parseInt(txtKilometraje.getText().trim());
            double precio = Double.parseDouble(txtPrecioVehiculo.getText()); // Usamos txtPrecioVehiculo
            
            // 5. Obtener el ID del Propietario desde el ComboBox
            PropietarioItem pi = (PropietarioItem) selectedItem;
            int propietarioId = pi.getId();
            
            if (propietarioId == 0) { // ID 0 es el item de "Error" o "No hay"
                 JOptionPane.showMessageDialog(this, "Debe seleccionar un propietario válido.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 6. Crear la entidad Vehiculo
            Vehiculo vehiculo = new Vehiculo();
            vehiculo.setMarca(marca);
            vehiculo.setModelo(modelo);
            vehiculo.setAnio(anio);
            vehiculo.setPrecio(precio); // ¡Ya usamos el campo de precio!
            vehiculo.setKilometraje(kilometraje);
            vehiculo.setPropietarioId(propietarioId); // ¡Ya usamos el ID del ComboBox!
            
            // 7. Añadir las listas de rutas (imágenes y videos)
            vehiculo.setRutasImagenes(this.rutasImagenesSeleccionadas);
            vehiculo.setRutasVideos(new ArrayList<>()); // Asumimos que no hay videos por ahora

            // 8. Guardar en la base de datos
            vehiculoRepo.guardar(vehiculo);
            
            // 9. Dar feedback y regresar
            JOptionPane.showMessageDialog(this, 
                "Vehículo guardado con éxito.", 
                "Registro Completo", 
                JOptionPane.INFORMATION_MESSAGE);
            
            // Volver a la pantalla de vehículos
            new AdminScreenVehiculos().setVisible(true);
            this.dispose();

        } catch (NumberFormatException e) {
            logger.log(java.util.logging.Level.SEVERE, "Error de formato numérico", e);
            JOptionPane.showMessageDialog(this, 
                "Error: 'Año', 'Kilometraje' y 'Precio' deben ser números válidos.", 
                "Error de Formato", 
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, "Error al guardar el vehículo", e);
            JOptionPane.showMessageDialog(this, 
                "Error al guardar el vehículo: " + e.getMessage(), 
                "Error de Base de Datos", 
                JOptionPane.ERROR_MESSAGE);
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
        logoadsocar1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtMarca = new javax.swing.JTextField();
        txtModelo = new javax.swing.JTextField();
        txtPrecioVehiculo = new javax.swing.JTextField();
        txtKilometraje = new javax.swing.JTextField();
        btnAñadirVehiculo = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btnAñadirImagen = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        comboBoxPropietario = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        txtAño1 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(0, 102, 204));

        logoadsocar1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        logoadsocar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adsocar/assets/Logo_adso_car.png"))); // NOI18N
        logoadsocar1.setText("jLabel1");

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adsocar/assets/adsocar_letters.png"))); // NOI18N
        jLabel5.setText("jLabel4");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(logoadsocar1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addComponent(logoadsocar1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 180, 490));

        jLabel3.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel3.setText("Ingreso de Datos ");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 20, 320, 40));

        jLabel1.setText("Diligencia los campos de texto para añadir nuevos vehiculos");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 60, -1, -1));
        jPanel1.add(txtMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 140, 230, 30));

        txtModelo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtModeloActionPerformed(evt);
            }
        });
        jPanel1.add(txtModelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 220, 230, 30));
        jPanel1.add(txtPrecioVehiculo, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 390, 230, 30));

        txtKilometraje.setText(" ");
        jPanel1.add(txtKilometraje, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 140, 200, 40));

        btnAñadirVehiculo.setBackground(new java.awt.Color(0, 102, 204));
        btnAñadirVehiculo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAñadirVehiculo.setForeground(new java.awt.Color(255, 255, 255));
        btnAñadirVehiculo.setText("AÑADIR VEHICULO");
        jPanel1.add(btnAñadirVehiculo, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 430, 220, 50));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Adjunta fotos del vehiculo");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 310, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Marca del Vehiculo");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 110, -1, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Modelo del Vehiculo");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 190, -1, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("Precio del vehiculo");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 360, -1, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("Kilometraje del Vehiculo");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 110, -1, -1));

        btnAñadirImagen.setBackground(new java.awt.Color(0, 102, 204));
        btnAñadirImagen.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnAñadirImagen.setForeground(new java.awt.Color(255, 255, 255));
        btnAñadirImagen.setText("+");
        jPanel1.add(btnAñadirImagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 340, 200, 50));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("Propietario del Vehiculo");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 210, -1, -1));

        comboBoxPropietario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxPropietarioActionPerformed(evt);
            }
        });
        jPanel1.add(comboBoxPropietario, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 240, 190, -1));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("Año del Vehiculo");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 280, -1, -1));
        jPanel1.add(txtAño1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 310, 230, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 906, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtModeloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtModeloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtModeloActionPerformed

    private void comboBoxPropietarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxPropietarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboBoxPropietarioActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new AddVehiculo().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAñadirImagen;
    private javax.swing.JButton btnAñadirVehiculo;
    private javax.swing.JComboBox<PropietarioItem> comboBoxPropietario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
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
    private javax.swing.JLabel logoadsocar1;
    private javax.swing.JTextField txtAño1;
    private javax.swing.JTextField txtKilometraje;
    private javax.swing.JTextField txtMarca;
    private javax.swing.JTextField txtModelo;
    private javax.swing.JTextField txtPrecioVehiculo;
    // End of variables declaration//GEN-END:variables
}
