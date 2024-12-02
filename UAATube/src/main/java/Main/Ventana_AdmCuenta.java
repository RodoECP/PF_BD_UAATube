/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Main;

import org.bson.Document;

/**
 *
 * @author rodol
 */
public class Ventana_AdmCuenta extends javax.swing.JFrame {

    private static Document Usuario;
    private static String PaginaOrigen;
    /**
     * Creates new form Ventana_AdmCuenta
     */
    public Ventana_AdmCuenta(Document Usuario, String PaginaOrigen) {
        initComponents();
        this.Usuario = Usuario;
        this.PaginaOrigen = PaginaOrigen;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblIcono = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();
        jPanelCategorias = new javax.swing.JPanel();
        chkFamilia = new javax.swing.JCheckBox();
        chkMusica = new javax.swing.JCheckBox();
        chkPeliculas = new javax.swing.JCheckBox();
        chkVideojuegos = new javax.swing.JCheckBox();
        chkVlog = new javax.swing.JCheckBox();
        chkTecnologia = new javax.swing.JCheckBox();
        chkCocina = new javax.swing.JCheckBox();
        chkBelleza = new javax.swing.JCheckBox();
        lblCategorias = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        lblUsuario = new javax.swing.JLabel();
        txtCanal = new javax.swing.JTextField();
        lblCanal = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        lblCorreo = new javax.swing.JLabel();
        txtConfirmarContra = new javax.swing.JTextField();
        lblConfirmaContra = new javax.swing.JLabel();
        txtContra = new javax.swing.JTextField();
        lblContra = new javax.swing.JLabel();
        btnAplicarCambios = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblIcono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UAATube Icon 150x92.png"))); // NOI18N

        lblTitulo.setText("Administrar Cuenta");

        jPanelCategorias.setBackground(new java.awt.Color(29, 113, 150));
        jPanelCategorias.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanelCategorias.setForeground(new java.awt.Color(255, 255, 255));
        jPanelCategorias.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        chkFamilia.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        chkFamilia.setText("Comedia");

        chkMusica.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        chkMusica.setText("Musica");

        chkPeliculas.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        chkPeliculas.setText("Peliculas");

        chkVideojuegos.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        chkVideojuegos.setText("Videojuegos");

        chkVlog.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        chkVlog.setText("Vlog");

        chkTecnologia.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        chkTecnologia.setText("Tecnología");

        chkCocina.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        chkCocina.setText("Cocina");

        chkBelleza.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        chkBelleza.setText("Belleza");

        lblCategorias.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        lblCategorias.setText("Categorias");

        javax.swing.GroupLayout jPanelCategoriasLayout = new javax.swing.GroupLayout(jPanelCategorias);
        jPanelCategorias.setLayout(jPanelCategoriasLayout);
        jPanelCategoriasLayout.setHorizontalGroup(
            jPanelCategoriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCategoriasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelCategoriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCategorias)
                    .addGroup(jPanelCategoriasLayout.createSequentialGroup()
                        .addGroup(jPanelCategoriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chkMusica)
                            .addComponent(chkFamilia)
                            .addComponent(chkPeliculas)
                            .addComponent(chkVideojuegos))
                        .addGap(39, 39, 39)
                        .addGroup(jPanelCategoriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chkCocina)
                            .addComponent(chkBelleza)
                            .addComponent(chkVlog)
                            .addComponent(chkTecnologia))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelCategoriasLayout.setVerticalGroup(
            jPanelCategoriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCategoriasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblCategorias)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelCategoriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelCategoriasLayout.createSequentialGroup()
                        .addComponent(chkTecnologia)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chkCocina)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chkBelleza)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chkVlog))
                    .addGroup(jPanelCategoriasLayout.createSequentialGroup()
                        .addComponent(chkVideojuegos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chkMusica)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chkFamilia)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chkPeliculas)))
                .addContainerGap())
        );

        txtUsuario.setText("Usuario");

        lblUsuario.setText("Usuario");

        txtCanal.setText("Canal");

        lblCanal.setText("Canal");

        txtCorreo.setText("Correo");

        lblCorreo.setText("Correo");

        txtConfirmarContra.setText("Confirmar Contraseña");

        lblConfirmaContra.setText("Confirma Contraseña");

        txtContra.setText("Contraseña");

        lblContra.setText("Contraseña");

        btnAplicarCambios.setText("Aplicar Cambios");

        btnCancelar.setText("Cancelar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelCategorias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtUsuario)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(lblIcono)
                            .addGap(18, 18, 18)
                            .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(lblUsuario)
                        .addComponent(txtCanal, javax.swing.GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE)
                        .addComponent(lblCanal)
                        .addComponent(lblCorreo)
                        .addComponent(txtCorreo))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btnAplicarCambios)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCancelar))
                        .addComponent(txtContra, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblContra, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblConfirmaContra, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtConfirmarContra, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(205, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblIcono)
                    .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(lblUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblCanal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCanal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblCorreo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jPanelCategorias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblContra)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtContra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblConfirmaContra)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtConfirmarContra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAplicarCambios)
                    .addComponent(btnCancelar))
                .addContainerGap(45, Short.MAX_VALUE))
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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Ventana_AdmCuenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ventana_AdmCuenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ventana_AdmCuenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ventana_AdmCuenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Ventana_AdmCuenta(Usuario, PaginaOrigen).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAplicarCambios;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JCheckBox chkBelleza;
    private javax.swing.JCheckBox chkCocina;
    private javax.swing.JCheckBox chkFamilia;
    private javax.swing.JCheckBox chkMusica;
    private javax.swing.JCheckBox chkPeliculas;
    private javax.swing.JCheckBox chkTecnologia;
    private javax.swing.JCheckBox chkVideojuegos;
    private javax.swing.JCheckBox chkVlog;
    private javax.swing.JPanel jPanelCategorias;
    private javax.swing.JLabel lblCanal;
    private javax.swing.JLabel lblCategorias;
    private javax.swing.JLabel lblConfirmaContra;
    private javax.swing.JLabel lblContra;
    private javax.swing.JLabel lblCorreo;
    private javax.swing.JLabel lblIcono;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JTextField txtCanal;
    private javax.swing.JTextField txtConfirmarContra;
    private javax.swing.JTextField txtContra;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
