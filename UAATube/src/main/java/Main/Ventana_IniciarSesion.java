package Main;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import java.awt.Color;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import org.bson.Document;
import Conexion.conexion;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Ventana de inicio de sesión que permite al usuario autenticarse en la
 * aplicación. Esta clase maneja la interfaz gráfica y la lógica para iniciar
 * sesión en el sistema.
 */
public class Ventana_IniciarSesion extends javax.swing.JFrame {

    /**
     * Constructor de la clase `Ventana_IniciarSesion`. Este método inicializa
     * los componentes gráficos de la ventana de inicio de sesión y configura
     * los listeners necesarios para los campos de texto y contraseñas con
     * placeholders dinámicos.
     *
     * @param PaginaOrigen La página de donde se originó la navegación hacia la
     * ventana de inicio de sesión.
     * @param database La conexión a la base de datos (MongoDB) que se utilizará
     * para autenticar al usuario.
     */
    public Ventana_IniciarSesion(String PaginaOrigen, MongoDatabase database) {
        // Inicializa los componentes de la ventana.
        initComponents();

        // Configura los listeners para los campos de texto y contraseña con placeholders dinámicos.
        setupFieldFocusListeners();

        // Establece la página de origen (para redirigir después de iniciar sesión).
        this.PaginaOrigen = PaginaOrigen;

        // Establece el valor inicial del usuario a null (no hay usuario autenticado aún).
        Usuario = null;

        // Establece la conexión a la base de datos.
        this.database = database;
    }
    //Variable para almacenar la pagina de donde se inició esta
    private static String PaginaOrigen;
    
    //Variable para almacenar el documento del usuario que haya iniciado sesion
    private static Document Usuario;
    
    //Variable para almacenar la conexino a la base de datos
    private static MongoDatabase database= null;

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTextField_Usuario_InicioSesion = new javax.swing.JTextField();
        jButton_InicioSesion = new javax.swing.JButton();
        jLabel_Contraseña_Usuario = new javax.swing.JLabel();
        jPasswordField_Contraseña = new javax.swing.JPasswordField();
        jButton_Cancelar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton_Registrar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel_Icono_UAATube = new javax.swing.JLabel();
        jLabel_Inicio_Sesion_UAATube = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(38, 38, 38));

        jTextField_Usuario_InicioSesion.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jTextField_Usuario_InicioSesion.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_Usuario_InicioSesion.setText("Nombre de Usuario");
        jTextField_Usuario_InicioSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_Usuario_InicioSesionActionPerformed(evt);
            }
        });

        jButton_InicioSesion.setBackground(new java.awt.Color(22, 62, 100));
        jButton_InicioSesion.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jButton_InicioSesion.setForeground(new java.awt.Color(255, 255, 255));
        jButton_InicioSesion.setText("Iniciar Sesión");
        jButton_InicioSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_InicioSesionActionPerformed(evt);
            }
        });

        jLabel_Contraseña_Usuario.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel_Contraseña_Usuario.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_Contraseña_Usuario.setText("Contraseña");

        jPasswordField_Contraseña.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPasswordField_ContraseñaActionPerformed(evt);
            }
        });

        jButton_Cancelar.setBackground(new java.awt.Color(22, 62, 100));
        jButton_Cancelar.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jButton_Cancelar.setForeground(new java.awt.Color(255, 255, 255));
        jButton_Cancelar.setText("Cancelar");
        jButton_Cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_CancelarActionPerformed(evt);
            }
        });

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("¿No tienes cuenta?");

        jButton_Registrar.setBackground(new java.awt.Color(22, 62, 100));
        jButton_Registrar.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jButton_Registrar.setForeground(new java.awt.Color(255, 255, 255));
        jButton_Registrar.setText("Registrate Aquí");
        jButton_Registrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_RegistrarActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(89, 89, 89));

        jLabel_Icono_UAATube.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UAATube Icon 150x92.png"))); // NOI18N

        jLabel_Inicio_Sesion_UAATube.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabel_Inicio_Sesion_UAATube.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_Inicio_Sesion_UAATube.setText("Inicio de Sesión UAA Tube");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel_Icono_UAATube)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel_Inicio_Sesion_UAATube)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_Icono_UAATube)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel_Inicio_Sesion_UAATube)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jPasswordField_Contraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField_Usuario_InicioSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(120, 120, 120)
                                .addComponent(jLabel_Contraseña_Usuario))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton_Registrar, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton_InicioSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton_Cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(21, Short.MAX_VALUE))
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField_Usuario_InicioSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel_Contraseña_Usuario)
                .addGap(11, 11, 11)
                .addComponent(jPasswordField_Contraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_InicioSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_Cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jButton_Registrar))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField_Usuario_InicioSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_Usuario_InicioSesionActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_jTextField_Usuario_InicioSesionActionPerformed

    /**
     * Método asociado al botón "Iniciar Sesión". Realiza las siguientes
     * acciones: - Valida los campos de usuario y contraseña. - Conecta a
     * MongoDB para verificar las credenciales. - Muestra mensajes de éxito o
     * error según el resultado.
     *
     * @param evt Evento generado por el clic en el botón.
     */
    private void jButton_InicioSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_InicioSesionActionPerformed
        // TODO add your handling code here:
        // Restablece los bordes de los campos en caso de errores previos.
        resetFieldBorders();

        // Obtiene los valores ingresados en los campos de texto.
        String usuario = jTextField_Usuario_InicioSesion.getText().trim().toLowerCase();
        String contraseña = new String(jPasswordField_Contraseña.getPassword()).trim();

        // Valida que los campos no estén vacíos.
        if (usuario.isEmpty() || contraseña.isEmpty()) {
            if (usuario.isEmpty()) {
                jTextField_Usuario_InicioSesion.setBorder(BorderFactory.createLineBorder(Color.RED));
            }
            if (contraseña.isEmpty()) {
                jPasswordField_Contraseña.setBorder(BorderFactory.createLineBorder(Color.RED));
            }
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            MongoCollection<Document> usuarios = database.getCollection("Usuarios");
            Document usuarioDoc = usuarios.find(Filters.and(
                    Filters.eq("nombre_usuario", usuario),
                    Filters.eq("contraseña", contraseña)
            )).first();
            if (usuarioDoc != null) {
                JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                Usuario = usuarioDoc;
                cerrarVentana();

            } else {
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (MongoException e) {
            JOptionPane.showMessageDialog(this, "Error al consultar la base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton_InicioSesionActionPerformed

    private void resetFieldBorders() {
        jTextField_Usuario_InicioSesion.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        jPasswordField_Contraseña.setBorder(BorderFactory.createLineBorder(Color.GRAY));
    }

    private void jPasswordField_ContraseñaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPasswordField_ContraseñaActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_jPasswordField_ContraseñaActionPerformed

    private void jButton_CancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_CancelarActionPerformed
        cerrarVentana();
    }//GEN-LAST:event_jButton_CancelarActionPerformed

    private void jButton_RegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_RegistrarActionPerformed
        Ventana_RegistrarUsuario form = new Ventana_RegistrarUsuario(PaginaOrigen, database);
        form.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton_RegistrarActionPerformed

    /**
     * Configura los listeners de enfoque (focus) para los campos de texto y
     * contraseña en la ventana de inicio de sesión. Estos listeners permiten
     * mostrar y restaurar placeholders dinámicos en los campos de entrada.
     */
    private void setupFieldFocusListeners() {
        // Configura el listener para el campo de texto del usuario con un placeholder personalizado.
        setupFocusListener(jTextField_Usuario_InicioSesion, "Ingrese su usuario");

        // Configura el listener para el campo de contraseña con un placeholder personalizado.
        setupFocusListener(jPasswordField_Contraseña, "Ingrese su contraseña");
    }

    /**
     * Configura un listener de enfoque para un campo de texto (JTextField).
     * Este listener gestiona la lógica del placeholder dinámico: - Cuando el
     * campo obtiene el enfoque, se limpia el texto si coincide con el
     * placeholder. - Cuando el campo pierde el enfoque, se restaura el
     * placeholder si el campo está vacío.
     *
     * @param field Campo de texto al que se aplicará el listener.
     * @param placeholder Texto del placeholder que se mostrará cuando el campo
     * esté vacío.
     */
    private void setupFocusListener(JTextField field, String placeholder) {
        // Configuración inicial del placeholder.
        field.setText(placeholder);
        field.setForeground(Color.GRAY); // Color del texto del placeholder.

        // Añade un listener para gestionar eventos de enfoque.
        field.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                // Limpia el placeholder cuando el campo obtiene el enfoque.
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK); // Cambia el color del texto a normal.
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                // Restaura el placeholder si el campo está vacío al perder el enfoque.
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(Color.GRAY); // Restaura el color del texto del placeholder.
                }
            }
        });
    }

    /**
     * Configura un listener de enfoque para un campo de contraseña
     * (JPasswordField). Este listener implementa la misma lógica que el de
     * JTextField pero está adaptado para manejar contraseñas, ya que estas se
     * gestionan como un arreglo de caracteres.
     *
     * @param field Campo de contraseña al que se aplicará el listener.
     * @param placeholder Texto del placeholder que se mostrará cuando el campo
     * esté vacío.
     */
    private void setupFocusListener(JPasswordField field, String placeholder) {
        // Configuración inicial del placeholder.
        field.setText(placeholder);
        field.setForeground(Color.GRAY); // Color del texto del placeholder.

        // Añade un listener para gestionar eventos de enfoque.
        field.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                // Limpia el placeholder cuando el campo obtiene el enfoque.
                if (String.valueOf(field.getPassword()).equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK); // Cambia el color del texto a normal.
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                // Restaura el placeholder si el campo está vacío al perder el enfoque.
                if (String.valueOf(field.getPassword()).isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(Color.GRAY); // Restaura el color del texto del placeholder.
                }
            }
        });
    }

    private void highlightField(JTextField field) {
        field.setBorder(BorderFactory.createLineBorder(Color.RED));
    }

    private void highlightField(JPasswordField field) {
        field.setBorder(BorderFactory.createLineBorder(Color.RED));
    }
    
    private void cerrarVentana(){
        if (PaginaOrigen == "PaginaPrincipal"){
            VentanaPrincipal form = new VentanaPrincipal(Usuario, database);
            form.setVisible(true);
            dispose();
        } else {
        }
    }

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Ventana_IniciarSesion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ventana_IniciarSesion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ventana_IniciarSesion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ventana_IniciarSesion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Ventana_IniciarSesion(PaginaOrigen, database).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_Cancelar;
    private javax.swing.JButton jButton_InicioSesion;
    private javax.swing.JButton jButton_Registrar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel_Contraseña_Usuario;
    private javax.swing.JLabel jLabel_Icono_UAATube;
    private javax.swing.JLabel jLabel_Inicio_Sesion_UAATube;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPasswordField jPasswordField_Contraseña;
    private javax.swing.JTextField jTextField_Usuario_InicioSesion;
    // End of variables declaration//GEN-END:variables

    private void clearLoginFields() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
