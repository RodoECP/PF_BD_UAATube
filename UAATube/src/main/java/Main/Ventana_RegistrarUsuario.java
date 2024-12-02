package Main;

import javax.swing.JTextField;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import org.bson.Document;

/**
 *
 * @author pedro
 */
public class Ventana_RegistrarUsuario extends javax.swing.JFrame {

    /**
     * Constructor de la clase `Ventana_RegistrarUsuario`. Inicializa la ventana
     * de registro de usuario, configura los componentes de la interfaz gráfica
     * y ajusta los comportamientos iniciales de los campos de texto.
     */
    public Ventana_RegistrarUsuario(String PaginaOrigen) {
        initComponents();       // Inicializa los componentes de la interfaz gráfica.
        initializeFields();     // Configura los valores iniciales de los campos de texto.
        removeInitialFocus();   // Ajusta el enfoque para evitar que un campo esté enfocado inicialmente.
        this.PaginaOrigen = PaginaOrigen;
    }

//Variables para almacenar los datos que ingrese el usuario
    private String nombreUsuario = "";
    private String nombreCanal = "";
    private String correoElectronico = "";
    private String contraseña = "";
    private String confirmarContraseña = "";

//Variable para almacenar la pagina de donde se abrió esta
    private static String PaginaOrigen;
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel_RegistrarUsuario = new javax.swing.JPanel();
        jTextField_NombreUsuario = new javax.swing.JTextField();
        jTextField_NombreCanal = new javax.swing.JTextField();
        jTextField_CorreoElectronico = new javax.swing.JTextField();
        jButton_RegistrarUsuario = new javax.swing.JButton();
        jLabel_Registro_UAA = new javax.swing.JLabel();
        jPasswordField_Contraseña = new javax.swing.JPasswordField();
        jPasswordField_Contraseña_Confirmar = new javax.swing.JPasswordField();
        jLabel_Contraseña_Usuario = new javax.swing.JLabel();
        jLabel_Contraseña_Usuario1 = new javax.swing.JLabel();
        jLabel_Icono_UAATube = new javax.swing.JLabel();
        jButton_Cancelar = new javax.swing.JButton();
        jButton_IniciaSesion = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanelCategorias = new javax.swing.JPanel();
        chkFamilia = new javax.swing.JCheckBox();
        chkMusica = new javax.swing.JCheckBox();
        chkPeliculas = new javax.swing.JCheckBox();
        chkVideojuegos = new javax.swing.JCheckBox();
        chkVlog = new javax.swing.JCheckBox();
        chkTecnologia = new javax.swing.JCheckBox();
        chkCocina = new javax.swing.JCheckBox();
        chkBelleza = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel_RegistrarUsuario.setBackground(new java.awt.Color(29, 113, 150));

        jTextField_NombreUsuario.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jTextField_NombreUsuario.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_NombreUsuario.setText("Nombre de Usuario");
        jTextField_NombreUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_NombreUsuarioActionPerformed(evt);
            }
        });

        jTextField_NombreCanal.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jTextField_NombreCanal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_NombreCanal.setText("Nombre del Canal ");
        jTextField_NombreCanal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_NombreCanalActionPerformed(evt);
            }
        });

        jTextField_CorreoElectronico.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jTextField_CorreoElectronico.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_CorreoElectronico.setText("Correo Electronico");
        jTextField_CorreoElectronico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_CorreoElectronicoActionPerformed(evt);
            }
        });

        jButton_RegistrarUsuario.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jButton_RegistrarUsuario.setText("Registrarse");
        jButton_RegistrarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_RegistrarUsuarioActionPerformed(evt);
            }
        });

        jLabel_Registro_UAA.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabel_Registro_UAA.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_Registro_UAA.setText("Registro UAA Tube");

        jPasswordField_Contraseña.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPasswordField_ContraseñaActionPerformed(evt);
            }
        });

        jPasswordField_Contraseña_Confirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPasswordField_Contraseña_ConfirmarActionPerformed(evt);
            }
        });

        jLabel_Contraseña_Usuario.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel_Contraseña_Usuario.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_Contraseña_Usuario.setText("Contraseña");

        jLabel_Contraseña_Usuario1.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel_Contraseña_Usuario1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_Contraseña_Usuario1.setText("Confirmar Contraseña");

        jLabel_Icono_UAATube.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UAATube Icon 150x92.png"))); // NOI18N

        jButton_Cancelar.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jButton_Cancelar.setText("Cancelar");
        jButton_Cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_CancelarActionPerformed(evt);
            }
        });

        jButton_IniciaSesion.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jButton_IniciaSesion.setText("Inicia Sesión Aquí");
        jButton_IniciaSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_IniciaSesionActionPerformed(evt);
            }
        });

        jLabel1.setText("¿Ya tienes cuenta?");

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

        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel2.setText("Categorias");

        javax.swing.GroupLayout jPanelCategoriasLayout = new javax.swing.GroupLayout(jPanelCategorias);
        jPanelCategorias.setLayout(jPanelCategoriasLayout);
        jPanelCategoriasLayout.setHorizontalGroup(
            jPanelCategoriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCategoriasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelCategoriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
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
                .addComponent(jLabel2)
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

        javax.swing.GroupLayout jPanel_RegistrarUsuarioLayout = new javax.swing.GroupLayout(jPanel_RegistrarUsuario);
        jPanel_RegistrarUsuario.setLayout(jPanel_RegistrarUsuarioLayout);
        jPanel_RegistrarUsuarioLayout.setHorizontalGroup(
            jPanel_RegistrarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_RegistrarUsuarioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_RegistrarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_RegistrarUsuarioLayout.createSequentialGroup()
                        .addGroup(jPanel_RegistrarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_RegistrarUsuarioLayout.createSequentialGroup()
                                .addComponent(jLabel_Icono_UAATube)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel_Registro_UAA))
                            .addGroup(jPanel_RegistrarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTextField_NombreCanal, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
                                .addComponent(jTextField_NombreUsuario)
                                .addComponent(jTextField_CorreoElectronico))
                            .addComponent(jPanelCategorias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel_RegistrarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jPasswordField_Contraseña_Confirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel_RegistrarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel_RegistrarUsuarioLayout.createSequentialGroup()
                                        .addGap(144, 144, 144)
                                        .addComponent(jLabel_Contraseña_Usuario))
                                    .addGroup(jPanel_RegistrarUsuarioLayout.createSequentialGroup()
                                        .addGap(113, 113, 113)
                                        .addComponent(jLabel_Contraseña_Usuario1))
                                    .addComponent(jPasswordField_Contraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel_RegistrarUsuarioLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jButton_RegistrarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton_Cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17))))
            .addGroup(jPanel_RegistrarUsuarioLayout.createSequentialGroup()
                .addGap(123, 123, 123)
                .addGroup(jPanel_RegistrarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_RegistrarUsuarioLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel1))
                    .addComponent(jButton_IniciaSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel_RegistrarUsuarioLayout.setVerticalGroup(
            jPanel_RegistrarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_RegistrarUsuarioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_RegistrarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_Icono_UAATube)
                    .addGroup(jPanel_RegistrarUsuarioLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel_Registro_UAA)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField_NombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTextField_NombreCanal, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTextField_CorreoElectronico, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanelCategorias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel_Contraseña_Usuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPasswordField_Contraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel_Contraseña_Usuario1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPasswordField_Contraseña_Confirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel_RegistrarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_RegistrarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_Cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_IniciaSesion)
                .addContainerGap(10, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_RegistrarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_RegistrarUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Captura el evento de acción en el campo "Nombre de Usuario". Actualiza la
     * variable global `nombreUsuario` con el texto ingresado.
     *
     * @param evt Evento de acción generado por el campo de texto.
     */
    private void jTextField_NombreUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_NombreUsuarioActionPerformed
        // TODO add your handling code here:
        nombreUsuario = jTextField_NombreUsuario.getText();
    }//GEN-LAST:event_jTextField_NombreUsuarioActionPerformed

    /**
     * Captura el evento de acción en el campo "Nombre de Canal". Actualiza la
     * variable global `nombreCanal` con el texto ingresado.
     *
     * @param evt Evento de acción generado por el campo de texto.
     */
    private void jTextField_NombreCanalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_NombreCanalActionPerformed
        // TODO add your handling code here:
        nombreCanal = jTextField_NombreCanal.getText();
    }//GEN-LAST:event_jTextField_NombreCanalActionPerformed

    /**
     * Captura el evento de acción en el campo "Correo Electrónico". Actualiza
     * la variable global `correoElectronico` con el texto ingresado.
     *
     * @param evt Evento de acción generado por el campo de texto.
     */
    private void jTextField_CorreoElectronicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_CorreoElectronicoActionPerformed
        // TODO add your handling code here:
        correoElectronico = jTextField_CorreoElectronico.getText();
    }//GEN-LAST:event_jTextField_CorreoElectronicoActionPerformed

    /**
     * Captura el evento de acción al hacer clic en el botón "Registrar
     * Usuario". Gestiona la validación de campos, la conexión a la base de
     * datos, y la lógica para registrar un nuevo usuario en MongoDB si no
     * existen duplicados.
     *
     * @param evt Evento de acción generado por el botón.
     */
    private void jButton_RegistrarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_RegistrarUsuarioActionPerformed
        // Reinicia los bordes de los campos para eliminar resaltados previos
        resetFieldBorders();

        // Recupera y normaliza los valores ingresados
        nombreUsuario = jTextField_NombreUsuario.getText().trim().toLowerCase();
        nombreCanal = jTextField_NombreCanal.getText().trim().toLowerCase();
        correoElectronico = jTextField_CorreoElectronico.getText().trim().toLowerCase();
        List<String> categorias = juntarCategorias();
        contraseña = new String(jPasswordField_Contraseña.getPassword()).trim();
        confirmarContraseña = new String(jPasswordField_Contraseña_Confirmar.getPassword()).trim();

        // Validación de campos
        boolean isValid = true;
        StringBuilder errorMessage = new StringBuilder("Por favor complete los siguientes campos:\n");

        if (nombreUsuario.isEmpty()) {
            isValid = false;
            errorMessage.append("- Nombre de Usuario\n");
            highlightField(jTextField_NombreUsuario);
        }
        if (nombreCanal.isEmpty()) {
            isValid = false;
            errorMessage.append("- Nombre del Canal\n");
            highlightField(jTextField_NombreCanal);
        }
        if (correoElectronico.isEmpty()) {
            isValid = false;
            errorMessage.append("- Correo Electrónico\n");
            highlightField(jTextField_CorreoElectronico);
        }
        if (categorias.isEmpty()) {
            isValid = false;
            errorMessage.append("- Categorías\n");
            highlightField(jPanelCategorias);
        }
        if (contraseña.isEmpty()) {
            isValid = false;
            errorMessage.append("- Contraseña\n");
            highlightField(jPasswordField_Contraseña);
        }
        if (confirmarContraseña.isEmpty()) {
            isValid = false;
            errorMessage.append("- Confirmar Contraseña\n");
            highlightField(jPasswordField_Contraseña_Confirmar);
        }

        // Si hay campos inválidos, muestra un mensaje de error y termina
        if (!isValid) {
            JOptionPane.showMessageDialog(this, errorMessage.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verifica que las contraseñas coincidan
        if (!contraseña.equals(confirmarContraseña)) {
            JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden.", "Error", JOptionPane.ERROR_MESSAGE);
            highlightField(jPasswordField_Contraseña);
            highlightField(jPasswordField_Contraseña_Confirmar);
            return;
        }

        // Obtiene la fecha y hora actual
        LocalDateTime now = LocalDateTime.now();
        String fechaRegistro = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // Conexión a MongoDB
        MongoDatabase database = crearConexion("UAATube");
        if (database != null) {
            MongoCollection<Document> usuariosCollection = database.getCollection("Usuarios");

            // Verifica duplicados en los campos clave
            if (usuariosCollection.find(new Document("nombre_usuario", nombreUsuario)).first() != null) {
                JOptionPane.showMessageDialog(this, "El Nombre de Usuario ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
                jTextField_NombreUsuario.setText("");
                return;
            }
            if (usuariosCollection.find(new Document("nombre_canal", nombreCanal)).first() != null) {
                JOptionPane.showMessageDialog(this, "El Nombre del Canal ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
                jTextField_NombreCanal.setText("");
                return;
            }
            if (usuariosCollection.find(new Document("correo_electronico", correoElectronico)).first() != null) {
                JOptionPane.showMessageDialog(this, "El Correo Electrónico ya está registrado.", "Error", JOptionPane.ERROR_MESSAGE);
                jTextField_CorreoElectronico.setText("");
                return;
            }

            // Solicita confirmación del usuario para registrar
            int confirmation = JOptionPane.showConfirmDialog(this, "Desea crear su usuario?", "Confirmación", JOptionPane.YES_NO_OPTION);
            if (confirmation == JOptionPane.YES_OPTION) {
                Document newUser = new Document("nombre_usuario", nombreUsuario)
                        .append("nombre_canal", nombreCanal)
                        .append("correo_electronico", correoElectronico)
                        .append("contraseña", contraseña) // Contraseña sin hash (mejor usar hashing)
                        .append("fecha_registro", fechaRegistro)
                        .append("categorias", categorias);
                try {
                    usuariosCollection.insertOne(newUser);
                    JOptionPane.showMessageDialog(this, "Usuario registrado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    Ventana_IniciarSesion form = new Ventana_IniciarSesion(PaginaOrigen);
                    form.setVisible(true);
                    dispose();
                } catch (MongoException e) {
                    JOptionPane.showMessageDialog(this, "Error al registrar el usuario: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Operación cancelada por el usuario.", "Cancelación", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo conectar a la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to clear all the fields after successful registration
    private void clearFields() {
        jTextField_NombreUsuario.setText("");
        jTextField_NombreCanal.setText("");
        jTextField_CorreoElectronico.setText("");
        jPasswordField_Contraseña.setText("");
        jPasswordField_Contraseña_Confirmar.setText("");

        // Reset the border color of the fields back to the default color
        resetFieldBorders();
    }

// Method to add focus listeners to fields and reset background color on input
    private void resetFieldBorders() {
        jTextField_NombreUsuario.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        jTextField_NombreCanal.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        jTextField_CorreoElectronico.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        jPasswordField_Contraseña.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        jPasswordField_Contraseña_Confirmar.setBorder(BorderFactory.createLineBorder(Color.GRAY));
    }

// Helper method to highlight the border of a field
    private void highlightField(JComponent field) {
        field.setBorder(BorderFactory.createLineBorder(Color.RED, 2));// Red border with width of 2px
    }

// Method to add focus listeners to fields and reset the border when user starts typing
    private void addFocusListeners() {
        jTextField_NombreUsuario.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent evt) {
                resetFieldColorOnFocus(jTextField_NombreUsuario);
            }
        });
        jTextField_NombreCanal.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent evt) {
                resetFieldColorOnFocus(jTextField_NombreCanal);
            }
        });
        jTextField_CorreoElectronico.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent evt) {
                resetFieldColorOnFocus(jTextField_CorreoElectronico);
            }
        });
        jPasswordField_Contraseña.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent evt) {
                resetFieldColorOnFocus(jPasswordField_Contraseña);
            }
        });
        jPasswordField_Contraseña_Confirmar.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent evt) {
                resetFieldColorOnFocus(jPasswordField_Contraseña_Confirmar);
            }
        });

        // Adding key listeners to check if the user starts typing
        addKeyListenerToField(jTextField_NombreUsuario);
        addKeyListenerToField(jTextField_NombreCanal);
        addKeyListenerToField(jTextField_CorreoElectronico);
        addKeyListenerToField(jPasswordField_Contraseña);
        addKeyListenerToField(jPasswordField_Contraseña_Confirmar);
    }//GEN-LAST:event_jButton_RegistrarUsuarioActionPerformed

    // Helper method to reset the color on focus (user clicks or types)
    private void resetFieldColorOnFocus(JComponent field) {
        if (field instanceof JTextField) {
            if (field.getBorder().toString().contains("RED") && !((JTextField) field).getText().trim().isEmpty()) {
                field.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }
        } else if (field instanceof JPasswordField) {
            if (field.getBorder().toString().contains("RED") && !new String(((JPasswordField) field).getPassword()).trim().isEmpty()) {
                field.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }
        }
    }

    // Method to add key listener for fields to check if input is entered
    private void addKeyListenerToField(JComponent field) {
        field.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                resetFieldColorOnFocus(field);
            }
        });
    }

    private void jPasswordField_ContraseñaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPasswordField_ContraseñaActionPerformed
        // TODO add your handling code here:
        contraseña = new String(jPasswordField_Contraseña.getPassword());
    }//GEN-LAST:event_jPasswordField_ContraseñaActionPerformed

    private void jPasswordField_Contraseña_ConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPasswordField_Contraseña_ConfirmarActionPerformed
        // TODO add your handling code here:
        confirmarContraseña = new String(jPasswordField_Contraseña_Confirmar.getPassword());
    }//GEN-LAST:event_jPasswordField_Contraseña_ConfirmarActionPerformed

    private void jButton_CancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_CancelarActionPerformed
        if (PaginaOrigen == "PaginaPrincipal"){
            VentanaPrincipal form = new VentanaPrincipal(null);
            form.setVisible(true);
            this.dispose();
        } else {
            System.out.println(PaginaOrigen);
        }
    }//GEN-LAST:event_jButton_CancelarActionPerformed

    private void jButton_IniciaSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_IniciaSesionActionPerformed
        Ventana_IniciarSesion form = new Ventana_IniciarSesion(PaginaOrigen);
        form.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton_IniciaSesionActionPerformed

    public MongoDatabase crearConexion(String databaseName) {
        MongoDatabase database = null;
        try {
            String uri = "mongodb://localhost:27017"; // Update if needed
            MongoClient mongoClient = new MongoClient(new MongoClientURI(uri));
            database = mongoClient.getDatabase(databaseName);

            System.out.println("Conexión exitosa a MongoDB. Base de datos: " + databaseName);
        } catch (MongoException e) {
            System.err.println("Error al conectar con MongoDB: " + e.getMessage());
        }
        return database;
    }

    private void removeInitialFocus() {
        // Request focus on a non-input component, such as the main panel
        jPanel_RegistrarUsuario.requestFocusInWindow();
    }

// Method to add focus listener to clear a JTextField
    private void addClearOnFocusListener(JTextField textField) {
        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                textField.setText("");
            }
        });
    }
// Method to add focus listener to clear a JTextField and restore old value on focus lost

    private void addFocusBehavior(JTextField textField) {
        // Array to store the old value
        final String[] oldValue = {textField.getText()};

        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                // Store the current text before clearing
                oldValue[0] = textField.getText();
                textField.setText("");
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                // If no new value is entered, restore the old value
                if (textField.getText().isEmpty()) {
                    textField.setText(oldValue[0]);
                }
            }
        });
    }
    
    private List juntarCategorias(){
        List<String> categorias = new ArrayList<>();
        if (chkVideojuegos.isSelected()){
            categorias.add("Videojuegos");
        }
        if (chkPeliculas.isSelected()){
            categorias.add("Peliculas");
        }
        if (chkFamilia.isSelected()){
            categorias.add("Familia");
        }
        if (chkMusica.isSelected()){
            categorias.add("Musica");
        }
        if (chkTecnologia.isSelected()) {
            categorias.add("Tecnologia");
        }
        if (chkBelleza.isSelected()) {
            categorias.add("Belleza");
        }
        if(chkCocina.isSelected()){
            categorias.add("Cocina");
        }
        if(chkVlog.isSelected()){
            categorias.add("Vlog");
        }
        return categorias;
    }

    public void initializeFields() {
        // Call the method for each JTextField
        addFocusBehavior(jTextField_NombreUsuario);
        addFocusBehavior(jTextField_NombreCanal);
        addFocusBehavior(jTextField_CorreoElectronico);
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
            java.util.logging.Logger.getLogger(Ventana_RegistrarUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ventana_RegistrarUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ventana_RegistrarUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ventana_RegistrarUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Ventana_RegistrarUsuario(PaginaOrigen).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox chkBelleza;
    private javax.swing.JCheckBox chkCocina;
    private javax.swing.JCheckBox chkFamilia;
    private javax.swing.JCheckBox chkMusica;
    private javax.swing.JCheckBox chkPeliculas;
    private javax.swing.JCheckBox chkTecnologia;
    private javax.swing.JCheckBox chkVideojuegos;
    private javax.swing.JCheckBox chkVlog;
    private javax.swing.JButton jButton_Cancelar;
    private javax.swing.JButton jButton_IniciaSesion;
    private javax.swing.JButton jButton_RegistrarUsuario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel_Contraseña_Usuario;
    private javax.swing.JLabel jLabel_Contraseña_Usuario1;
    private javax.swing.JLabel jLabel_Icono_UAATube;
    private javax.swing.JLabel jLabel_Registro_UAA;
    private javax.swing.JPanel jPanelCategorias;
    private javax.swing.JPanel jPanel_RegistrarUsuario;
    private javax.swing.JPasswordField jPasswordField_Contraseña;
    private javax.swing.JPasswordField jPasswordField_Contraseña_Confirmar;
    private javax.swing.JTextField jTextField_CorreoElectronico;
    private javax.swing.JTextField jTextField_NombreCanal;
    private javax.swing.JTextField jTextField_NombreUsuario;
    // End of variables declaration//GEN-END:variables
}
