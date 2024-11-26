package Main;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 *
 * @author pedro
 */
public class VentanaPrincipal extends javax.swing.JFrame {

    /**
     * Constructor de la clase VentanaPrincipal. Este método inicializa los
     * componentes gráficos de la ventana principal, configura la interactividad
     * de las etiquetas `Inicia Sesión` y `Registrarse`, y establece el
     * comportamiento dinámico de la barra de búsqueda.
     */
    public VentanaPrincipal() {
        // Inicializa los componentes gráficos de la interfaz
        initComponents();

        // Configura las etiquetas `Inicia Sesión` y `Registrarse` para que sean clicables.
        // Cambia el cursor al pasar sobre ellas para indicar interactividad.
        jLabel_Inicia_Sesion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel_Registrarse.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        /**
         * Listener para jLabel_Inicia_Sesion. Cuando el usuario hace clic en la
         * etiqueta "Inicia Sesión", se abre la ventana de inicio de sesión
         * (Ventana_IniciarSesion). Opcionalmente, se cierra la ventana actual.
         */
        jLabel_Inicia_Sesion.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Abre la ventana de inicio de sesión
                Ventana_IniciarSesion loginForm = new Ventana_IniciarSesion();
                loginForm.setVisible(true);
                dispose(); // Cierra la ventana actual (opcional)
            }
        });

        /**
         * Listener para jLabel_Registrarse. Cuando el usuario hace clic en la
         * etiqueta "Registrarse", se abre la ventana de registro de usuario
         * (Ventana_RegistrarUsuario). Opcionalmente, se cierra la ventana
         * actual.
         */
        jLabel_Registrarse.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Abre la ventana de registro de usuario
                Ventana_RegistrarUsuario registerForm = new Ventana_RegistrarUsuario();
                registerForm.setVisible(true);
                dispose(); // Cierra la ventana actual (opcional)
            }
        });

        /**
         * Configura el comportamiento dinámico de la barra de búsqueda. Se
         * establece un texto placeholder inicial y se implementan las
         * siguientes acciones: - Cuando el campo de texto recibe foco
         * (focusGained), se elimina el texto placeholder. - Cuando el campo de
         * texto pierde el foco (focusLost), se restablece el texto placeholder
         * si el usuario no ingresó ningún texto.
         */
        jTextField_BarraBusqueda.addFocusListener(new FocusListener() {
            // Texto inicial del placeholder
            private final String placeholder = "Buscar...";

            /**
             * Acción ejecutada cuando el campo de texto recibe el foco. Si el
             * contenido es igual al texto placeholder, se limpia el campo.
             *
             * @param e Evento de foco (FocusEvent).
             */
            public void focusGained(FocusEvent e) {
                if (jTextField_BarraBusqueda.getText().equals(placeholder)) {
                    jTextField_BarraBusqueda.setText("");
                }
            }

            /**
             * Acción ejecutada cuando el campo de texto pierde el foco. Si el
             * campo está vacío, se restablece el texto placeholder.
             *
             * @param e Evento de foco (FocusEvent).
             */
            public void focusLost(FocusEvent e) {
                if (jTextField_BarraBusqueda.getText().trim().isEmpty()) {
                    jTextField_BarraBusqueda.setText(placeholder);
                }
            }
        });

        // Establece el texto inicial del campo de búsqueda.
        jTextField_BarraBusqueda.setText("Buscar...");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelPrincipal = new javax.swing.JPanel();
        jLabel_Inicia_Sesion = new javax.swing.JLabel();
        jLabel_Diagonal = new javax.swing.JLabel();
        jLabel_Registrarse = new javax.swing.JLabel();
        jTextField_BarraBusqueda = new javax.swing.JTextField();
        btnSubirVideo = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel_Inicia_Sesion.setText("Inicia Sesión");
        jLabel_Inicia_Sesion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_Inicia_SesionMouseClicked(evt);
            }
        });

        jLabel_Diagonal.setText("/");

        jLabel_Registrarse.setText("Registrarse");

        jTextField_BarraBusqueda.setText("jTextField1");
        jTextField_BarraBusqueda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_BarraBusquedaActionPerformed(evt);
            }
        });

        btnSubirVideo.setText("Subir Video");
        btnSubirVideo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubirVideoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelPrincipalLayout = new javax.swing.GroupLayout(PanelPrincipal);
        PanelPrincipal.setLayout(PanelPrincipalLayout);
        PanelPrincipalLayout.setHorizontalGroup(
            PanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelPrincipalLayout.createSequentialGroup()
                .addGap(346, 346, 346)
                .addComponent(jTextField_BarraBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 553, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(122, 122, 122)
                .addComponent(btnSubirVideo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 120, Short.MAX_VALUE)
                .addComponent(jLabel_Inicia_Sesion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel_Diagonal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel_Registrarse)
                .addContainerGap())
        );
        PanelPrincipalLayout.setVerticalGroup(
            PanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel_Inicia_Sesion)
                        .addComponent(jLabel_Diagonal)
                        .addComponent(jLabel_Registrarse))
                    .addGroup(PanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField_BarraBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSubirVideo)))
                .addContainerGap(774, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField_BarraBusquedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_BarraBusquedaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_BarraBusquedaActionPerformed

    private void jLabel_Inicia_SesionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_Inicia_SesionMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel_Inicia_SesionMouseClicked

    private void btnSubirVideoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubirVideoActionPerformed
        Ventana_SubirVideo form = new Ventana_SubirVideo();
        form.setVisible(true);
        dispose(); // Cierra la ventana actual (opcional)
    }//GEN-LAST:event_btnSubirVideoActionPerformed

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
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelPrincipal;
    private javax.swing.JButton btnSubirVideo;
    private javax.swing.JLabel jLabel_Diagonal;
    private javax.swing.JLabel jLabel_Inicia_Sesion;
    private javax.swing.JLabel jLabel_Registrarse;
    private javax.swing.JTextField jTextField_BarraBusqueda;
    // End of variables declaration//GEN-END:variables
}
