/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Main;

import Conexion.conexion;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSUploadStream;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import java.awt.Desktop;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author rodol
 */
public class Ventana_SubirVideo extends javax.swing.JFrame {

    boolean videoCargado = false;
    boolean miniCargado = false;
    /**
     * Creates new form Ventana_SubirVideo
     */
    public Ventana_SubirVideo() {
        initComponents();
        initComponents2();
        
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
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        txtTitulo = new javax.swing.JTextField();
        lblDescripcion = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();
        lblCategorias = new javax.swing.JLabel();
        lblInstruccion = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        chkFamilia = new javax.swing.JCheckBox();
        chkMusica = new javax.swing.JCheckBox();
        chkPeliculas = new javax.swing.JCheckBox();
        chkVideojuegos = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        btnPublicar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        lblCargarVideo = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        lblCargarMini = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setForeground(new java.awt.Color(38, 38, 38));

        jPanel1.setBackground(new java.awt.Color(0, 102, 255));

        txtDescripcion.setColumns(20);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setRows(5);
        txtDescripcion.setName("txtDescripcion"); // NOI18N
        jScrollPane1.setViewportView(txtDescripcion);

        txtTitulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTituloActionPerformed(evt);
            }
        });

        lblDescripcion.setText("Descripción:");
        lblDescripcion.setName("lblDescripcion"); // NOI18N

        lblTitulo.setText("Titulo:");

        lblCategorias.setText("Categorias:");
        lblCategorias.setName("lblCategorias"); // NOI18N

        lblInstruccion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInstruccion.setText("Llena la informacion de tu video");
        lblInstruccion.setName("lblQueHacer"); // NOI18N

        chkFamilia.setText("Familia");

        chkMusica.setText("Musica");

        chkPeliculas.setText("Peliculas");

        chkVideojuegos.setText("Videojuegos");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chkMusica)
                    .addComponent(chkFamilia)
                    .addComponent(chkPeliculas)
                    .addComponent(chkVideojuegos))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(chkVideojuegos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkMusica)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkFamilia)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkPeliculas)
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addComponent(lblInstruccion, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblDescripcion)
                                    .addComponent(lblTitulo)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(lblCategorias)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblInstruccion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTitulo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDescripcion))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(lblCategorias))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(293, 293, 293))
        );

        jPanel2.setBackground(new java.awt.Color(0, 102, 255));

        btnPublicar.setText("Publicar Video");
        btnPublicar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPublicarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(btnPublicar, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnPublicar, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(0, 102, 204));

        txtCargarVideo.setEditable(false);
        txtCargarVideo.setColumns(20);
        txtCargarVideo.setLineWrap(true);
        txtCargarVideo.setRows(5);
        txtCargarVideo.setText("Sube aqui tu archivo o da clic para examinar tus archivos");
        txtCargarVideo.setWrapStyleWord(true);
        txtCargarVideo.setAutoscrolls(false);
        txtCargarVideo.setName("txtCargarVideo"); // NOI18N
        txtCargarVideo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtCargarVideoMouseClicked(evt);
            }
        });
        txtCargarVideo.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtCargarVideoPropertyChange(evt);
            }
        });
        jScrollPane3.setViewportView(txtCargarVideo);
        txtCargarVideo.getAccessibleContext().setAccessibleName("");

        lblCargarVideo.setText("Video");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblCargarVideo)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(112, 112, 112))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(113, 113, 113)
                        .addComponent(lblCargarVideo)))
                .addContainerGap(43, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(0, 102, 255));

        txtCargarMini.setEditable(false);
        txtCargarMini.setColumns(20);
        txtCargarMini.setLineWrap(true);
        txtCargarMini.setRows(5);
        txtCargarMini.setText("Sube aqui tu archivo o da clic para examinar tus archivos");
        txtCargarMini.setWrapStyleWord(true);
        txtCargarMini.setAutoscrolls(false);
        txtCargarMini.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        txtCargarMini.setName("txtCargarMini"); // NOI18N
        txtCargarMini.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtCargarMiniMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(txtCargarMini);

        lblCargarMini.setText("Miniatura");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblCargarMini)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(lblCargarMini)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(129, 129, 129)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(138, 138, 138))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(44, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void initComponents2(){
        txtCargarVideo.setDropTarget(new DropTarget() {
        public synchronized void drop(DropTargetDropEvent evt) {
            try {
                evt.acceptDrop(DnDConstants.ACTION_COPY);
                List<File> droppedFiles = (List<File>)
                    evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                for (File file : droppedFiles) {
                    String fileName = file.getName().toUpperCase();
                    boolean extension = fileName.endsWith(".MP4");
                    if(!extension){
                        JOptionPane.showMessageDialog(rootPane, "El archivo cargado no es un tipo valido", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        txtCargarVideo.setText(file.getAbsolutePath());
                    }
                }
                evt.dropComplete(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            }
        });
        txtCargarMini.setDropTarget(new DropTarget() {
        public synchronized void drop(DropTargetDropEvent evt) {
            try {
                evt.acceptDrop(DnDConstants.ACTION_COPY);
                List<File> droppedFiles = (List<File>)
                    evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                for (File file : droppedFiles) {
                    String fileName = file.getName().toUpperCase();
                    boolean extension = fileName.endsWith(".PNG") || fileName.endsWith(".JPG") || fileName.endsWith(".JPEG");
                    if(!extension){
                        JOptionPane.showMessageDialog(rootPane, "El archivo cargado no es un tipo valido", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        txtCargarMini.setText(file.getAbsolutePath());
                    }
                }
                evt.dropComplete(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            }
        });

    }
    
    private void txtCargarVideoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCargarVideoMouseClicked
        OpenFileViaExplorer("Video");
    }//GEN-LAST:event_txtCargarVideoMouseClicked

    private void txtCargarMiniMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCargarMiniMouseClicked
        OpenFileViaExplorer("Mini");
    }//GEN-LAST:event_txtCargarMiniMouseClicked

    private void btnPublicarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPublicarActionPerformed
        if(verificarCampos()){
            subirVideo();
            JOptionPane.showConfirmDialog(this,"El video se ha subido", "Confirmacion",JOptionPane.PLAIN_MESSAGE);
            VentanaPrincipal form = new VentanaPrincipal();
            form.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this,"Faltan campos de llenar", "Error",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnPublicarActionPerformed

    private void txtCargarVideoPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtCargarVideoPropertyChange

    }//GEN-LAST:event_txtCargarVideoPropertyChange

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        VentanaPrincipal form = new VentanaPrincipal();
        form.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txtTituloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTituloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTituloActionPerformed

    public void OpenFileViaExplorer(String tipo)
    {
        try {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter;
            if (tipo == "Video"){
                filter = new FileNameExtensionFilter("Archivos de Video", "mp4");
            } else {
                filter = new FileNameExtensionFilter("Archivos de Imagen", "jpg", "png", "jpeg");
            }
            fileChooser.setFileFilter(filter);
            fileChooser.setCurrentDirectory(new File("."));
            int result = fileChooser.showOpenDialog(null);
            if(result == JFileChooser.APPROVE_OPTION){
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                String fileName = file.getName().toUpperCase();
                if (tipo == "Mini"){
                    boolean extension = fileName.endsWith(".PNG") || fileName.endsWith(".JPG") || fileName.endsWith(".JPEG");
                    if(!extension){
                        JOptionPane.showMessageDialog(rootPane, "El archivo cargado no es un tipo valido", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        txtCargarMini.setText(file.getAbsolutePath());
                        miniCargado = true;
                    }
                } else {
                    boolean extension = fileName.endsWith(".MP4");
                    if(!extension){
                        JOptionPane.showMessageDialog(rootPane, "El archivo cargado no es un tipo valido", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        txtCargarVideo.setText(file.getAbsolutePath());
                        videoCargado = true;
                    }
                }
                
            }
            else if(result == JFileChooser.CANCEL_OPTION)
            {
                System.out.println("Cancelled.");
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
    
    private void subirVideo(){
        try {
            conexion conexionInstance = new conexion();
            MongoDatabase database = conexionInstance.crearConexion("UAATube");
            GridFSBucket gridFSBucket = GridFSBuckets.create(database);
            String filePathVideo = txtCargarVideo.getText();
            String filePathMini = txtCargarMini.getText();
            try (InputStream videoStream = new FileInputStream(filePathVideo);
                    InputStream miniStream = new FileInputStream(filePathMini)) {
                String videoFileName = txtTitulo.getText().replaceAll(" ", "_");
                ObjectId videoId = gridFSBucket.uploadFromStream(videoFileName, videoStream);
                System.out.println("Video subido con éxito: " + videoFileName);

                String thumbnailFileName = txtTitulo.getText().replaceAll(" ", "_");
                ObjectId miniId = gridFSBucket.uploadFromStream(thumbnailFileName, miniStream);
                System.out.println("Miniatura subida con éxito: " + thumbnailFileName);
                
                List<String> categorias = juntarCategorias();
                Document videoMetadata = new Document()
                        .append("videoId", videoId)
                        .append("thumbnailId", miniId)
                        .append("autor", "usuarioId")
                        .append("title", txtTitulo.getText())
                        .append("description", txtDescripcion.getText())
                        .append("categorias", categorias)
                        .append("me_gusta", 0);
                database.getCollection("Videos").insertOne(videoMetadata);
                System.out.println("Relacion entre video y miniatura guardada.");
            }
        } catch (IOException ex) {
            Logger.getLogger(Ventana_SubirVideo.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        return categorias;
    }

    private boolean verificarCampos(){
        if (!videoCargado|| 
                !miniCargado ||
                txtTitulo.getText().isBlank() ||
                txtDescripcion.getText().isBlank()||
                juntarCategorias().isEmpty()){
            return false;
        } else {
            return true;
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
            java.util.logging.Logger.getLogger(Ventana_SubirVideo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ventana_SubirVideo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ventana_SubirVideo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ventana_SubirVideo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Ventana_SubirVideo().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnPublicar;
    private javax.swing.JCheckBox chkFamilia;
    private javax.swing.JCheckBox chkMusica;
    private javax.swing.JCheckBox chkPeliculas;
    private javax.swing.JCheckBox chkVideojuegos;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblCargarMini;
    private javax.swing.JLabel lblCargarVideo;
    private javax.swing.JLabel lblCategorias;
    private javax.swing.JLabel lblDescripcion;
    private javax.swing.JLabel lblInstruccion;
    private javax.swing.JLabel lblTitulo;
    private final javax.swing.JTextArea txtCargarMini = new javax.swing.JTextArea();
    private final javax.swing.JTextArea txtCargarVideo = new javax.swing.JTextArea();
    private javax.swing.JTextArea txtDescripcion;
    private javax.swing.JTextField txtTitulo;
    // End of variables declaration//GEN-END:variables
}


