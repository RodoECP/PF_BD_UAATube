package Main;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

public class VentanaPrincipal extends javax.swing.JFrame {

   private JFXPanel jfxPanel;
    private MediaPlayer mediaPlayer;
    private GridFSBucket gridFSBucket;
    private JSlider volumeSlider;
    private JPanel controlsPanel;
    private JButton playButton, pauseButton, stopButton, rewindButton, fastForwardButton;
    private static Document Usuario;

    public VentanaPrincipal(Document Usuario) {
          connectToMongoDB();
        initComponents();
        initializeVideoPlayback();
        loadVideoList();
        addMediaControls();
        this.Usuario = Usuario;
        checarSesion();
        System.out.println(Usuario);
    }

    private void connectToMongoDB() {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase database = mongoClient.getDatabase("UAATube");
        gridFSBucket = GridFSBuckets.create(database);
    }

private void initializeVideoPlayback() {
        jfxPanel = new JFXPanel();
        jPanel_ReproducirVideo.setLayout(new BorderLayout());
        jPanel_ReproducirVideo.add(jfxPanel, BorderLayout.CENTER);

        // Add a ComponentListener to listen for resize events on the panel
        jPanel_ReproducirVideo.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // Update the video size when the panel is resized
                Platform.runLater(() -> updateVideoSize());
            }
        });

        Platform.runLater(() -> {
            // Set the initial size to match the current resolution of the panel
            double initialWidth = jPanel_ReproducirVideo.getWidth();
            double initialHeight = jPanel_ReproducirVideo.getHeight();
            
            StackPane root = new StackPane();
            Scene scene = new Scene(root, initialWidth, initialHeight);
            jfxPanel.setScene(scene);

            MediaView mediaView = new MediaView();
            root.getChildren().add(mediaView);

            mediaPlayer = null; // No video loaded initially
        });
    }

private void loadVideoList() {
    List<String> videoNames = new ArrayList<>();
    try (MongoCursor<GridFSFile> cursor = gridFSBucket.find().iterator()) {
        while (cursor.hasNext()) {
            GridFSFile file = cursor.next();
            String filename = file.getFilename();
            // Filter files based on video extensions
            if (filename.toLowerCase().endsWith(".mp4") ||
                filename.toLowerCase().endsWith(".avi") ||
                filename.toLowerCase().endsWith(".mkv") ||
                filename.toLowerCase().endsWith(".mov") ||
                filename.toLowerCase().endsWith(".wmv")) {
                videoNames.add(filename);
            }
        }
    }

    DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(videoNames.toArray(new String[0]));
    jComboBox_ListaVideos.setModel(model);

    jComboBox_ListaVideos.addActionListener(this::onVideoSelected);
}

private void onVideoSelected(ActionEvent e) {
        String selectedVideo = (String) jComboBox_ListaVideos.getSelectedItem();
        if (selectedVideo != null) {
            Platform.runLater(() -> playVideoFromGridFS(selectedVideo));
        }
    }

private void playVideoFromGridFS(String filename) {
        stopVideo(); // Stop any currently playing video
        File videoFile = downloadVideoFromGridFS(filename);
        if (videoFile != null) {
            Media media = new Media(videoFile.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            MediaView mediaView = (MediaView) ((StackPane) jfxPanel.getScene().getRoot()).getChildren().get(0);
            mediaView.setMediaPlayer(mediaPlayer);
            
            // Set initial size based on the current panel dimensions
            updateVideoSize();

            mediaPlayer.play();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to load video: " + filename, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateVideoSize() {
        if (mediaPlayer != null && mediaPlayer.getMedia() != null) {
            // Get the current dimensions of the panel
            double width = jPanel_ReproducirVideo.getWidth();
            double height = jPanel_ReproducirVideo.getHeight();

            // Get the MediaView from the scene
            MediaView mediaView = (MediaView) ((StackPane) jfxPanel.getScene().getRoot()).getChildren().get(0);

            // Set the new size for the MediaView based on panel size
            mediaView.setFitWidth(width);
            mediaView.setFitHeight(height);

            // Preserve the aspect ratio
            mediaView.setPreserveRatio(true);  // Maintain the aspect ratio of the video

            // Optionally, set smooth scaling for better visual quality
            mediaView.setSmooth(true);
        }
    }

    private File downloadVideoFromGridFS(String filename) {
        try {
            GridFSFile gridFSFile = gridFSBucket.find(new org.bson.Document("filename", filename)).first();
            if (gridFSFile != null) {
                File tempFile = File.createTempFile("video", ".mp4");
                tempFile.deleteOnExit();

                try (GridFSDownloadStream downloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId()); FileOutputStream fileOutputStream = new FileOutputStream(tempFile)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = downloadStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, bytesRead);
                    }
                }

                return tempFile;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

     private void stopVideo() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }
     
     private void addMediaControls() {
        // Create control buttons
        playButton = new JButton("Play");
        pauseButton = new JButton("Pause");
        stopButton = new JButton("Stop");
        rewindButton = new JButton("Rewind");
        fastForwardButton = new JButton("Fast Forward");

        volumeSlider = new JSlider(0, 100, 50); // Default value set to 50%
        jLabel_Volumen = new JLabel("Volume: 50%");  // Default volume label

        // Add action listeners for media controls (play, pause, stop, rewind, fast forward)
        playButton.addActionListener(e -> Platform.runLater(() -> {
            if (mediaPlayer != null) {
                mediaPlayer.play();
            }
        }));

        pauseButton.addActionListener(e -> Platform.runLater(() -> {
            if (mediaPlayer != null) {
                mediaPlayer.pause();
            }
        }));

        stopButton.addActionListener(e -> Platform.runLater(() -> {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
        }));

        rewindButton.addActionListener(e -> Platform.runLater(() -> {
            if (mediaPlayer != null) {
                mediaPlayer.seek(mediaPlayer.getCurrentTime().subtract(javafx.util.Duration.seconds(5)));
            }
        }));

        fastForwardButton.addActionListener(e -> Platform.runLater(() -> {
            if (mediaPlayer != null) {
                mediaPlayer.seek(mediaPlayer.getCurrentTime().add(javafx.util.Duration.seconds(5)));
            }
        }));

        volumeSlider.addChangeListener(e -> {
            int volumeValue = volumeSlider.getValue();
            Platform.runLater(() -> {
                if (mediaPlayer != null) {
                    mediaPlayer.setVolume(volumeValue / 100.0);  // Set the volume of the player
                }
            });

            // Update the jLabel_Volumen to show the current volume percentage
            SwingUtilities.invokeLater(() -> jLabel_Volumen.setText("Volume: " + volumeValue + "%"));
        });

        // Create control panel
        controlsPanel = new JPanel();
        controlsPanel.setLayout(new FlowLayout());
        controlsPanel.add(playButton);
        controlsPanel.add(pauseButton);
        controlsPanel.add(stopButton);
        controlsPanel.add(rewindButton);
        controlsPanel.add(fastForwardButton);
        controlsPanel.add(new JLabel("Volume:"));
        controlsPanel.add(volumeSlider);


        // Add the control panel below the video
        add(controlsPanel, BorderLayout.SOUTH);
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
        jPanel1 = new javax.swing.JPanel();
        jTextField_BarraBusqueda = new javax.swing.JTextField();
        btnSubirVideo = new javax.swing.JButton();
        jLabel_OpcionCuenta1 = new javax.swing.JLabel();
        jLabel_Diagonal = new javax.swing.JLabel();
        jLabel_OpcionCuenta2 = new javax.swing.JLabel();
        jLabel_Icono_UAATube = new javax.swing.JLabel();
        jPanel_ReproducirVideo = new javax.swing.JPanel();
        jComboBox_ListaVideos = new javax.swing.JComboBox<>();
        jButton_PlayVideo = new javax.swing.JButton();
        jButton_StopVideo = new javax.swing.JButton();
        jButton_PauseVideo = new javax.swing.JButton();
        jButton_RewindVideo = new javax.swing.JButton();
        jButton_FastForwardVideo = new javax.swing.JButton();
        jButton_VolumeUp = new javax.swing.JButton();
        jButton_VolumeDownIcon = new javax.swing.JButton();
        jLabel_Volumen = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(29, 113, 150));

        jTextField_BarraBusqueda.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jTextField_BarraBusqueda.setText("Barra de Busqueda");
        jTextField_BarraBusqueda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_BarraBusquedaActionPerformed(evt);
            }
        });

        btnSubirVideo.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        btnSubirVideo.setText("Subir Video");
        btnSubirVideo.setVisible(false);
        btnSubirVideo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubirVideoActionPerformed(evt);
            }
        });

        jLabel_OpcionCuenta1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel_OpcionCuenta1.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabel_OpcionCuenta1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_OpcionCuenta1.setText("Inicia Sesión");
        jLabel_OpcionCuenta1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_OpcionCuenta1MouseClicked(evt);
            }
        });

        jLabel_Diagonal.setBackground(new java.awt.Color(255, 255, 255));
        jLabel_Diagonal.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabel_Diagonal.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_Diagonal.setText("/");

        jLabel_OpcionCuenta2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel_OpcionCuenta2.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabel_OpcionCuenta2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_OpcionCuenta2.setText("Registrarse");
        jLabel_OpcionCuenta2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_OpcionCuenta2MouseClicked(evt);
            }
        });

        jLabel_Icono_UAATube.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UAATube Icon 150x92.png"))); // NOI18N

        jPanel_ReproducirVideo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel_ReproducirVideoLayout = new javax.swing.GroupLayout(jPanel_ReproducirVideo);
        jPanel_ReproducirVideo.setLayout(jPanel_ReproducirVideoLayout);
        jPanel_ReproducirVideoLayout.setHorizontalGroup(
            jPanel_ReproducirVideoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel_ReproducirVideoLayout.setVerticalGroup(
            jPanel_ReproducirVideoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 580, Short.MAX_VALUE)
        );

        jComboBox_ListaVideos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton_PlayVideo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PlayIcon.png"))); // NOI18N
        jButton_PlayVideo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_PlayVideoActionPerformed(evt);
            }
        });

        jButton_StopVideo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/StopIcon.png"))); // NOI18N
        jButton_StopVideo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_StopVideoActionPerformed(evt);
            }
        });

        jButton_PauseVideo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PauseIcon.png"))); // NOI18N
        jButton_PauseVideo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_PauseVideoActionPerformed(evt);
            }
        });

        jButton_RewindVideo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/RewindIcon.png"))); // NOI18N
        jButton_RewindVideo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_RewindVideoActionPerformed(evt);
            }
        });

        jButton_FastForwardVideo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/FastForwardIcon.png"))); // NOI18N
        jButton_FastForwardVideo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_FastForwardVideoActionPerformed(evt);
            }
        });

        jButton_VolumeUp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/VolumeUp.png"))); // NOI18N
        jButton_VolumeUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_VolumeUpActionPerformed(evt);
            }
        });

        jButton_VolumeDownIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/VolumeDown.png"))); // NOI18N
        jButton_VolumeDownIcon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_VolumeDownIconActionPerformed(evt);
            }
        });

        jLabel_Volumen.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel_Volumen.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_Volumen.setText("jLabel1");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel_Icono_UAATube)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 287, Short.MAX_VALUE)
                                .addComponent(jTextField_BarraBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 553, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(48, 48, 48)
                                .addComponent(btnSubirVideo))
                            .addComponent(jPanel_ReproducirVideo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton_RewindVideo, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_PlayVideo, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_PauseVideo, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_StopVideo, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_FastForwardVideo, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(217, 217, 217)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton_VolumeDownIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton_VolumeUp, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel_Volumen)
                                .addGap(51, 51, 51)))
                        .addGap(43, 43, 43)))
                .addGap(48, 48, 48)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel_OpcionCuenta1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel_Diagonal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel_OpcionCuenta2))
                    .addComponent(jComboBox_ListaVideos, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField_BarraBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel_OpcionCuenta1)
                            .addComponent(jLabel_Diagonal)
                            .addComponent(jLabel_OpcionCuenta2)
                            .addComponent(btnSubirVideo)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel_Icono_UAATube)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox_ListaVideos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel_ReproducirVideo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jButton_RewindVideo, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton_PauseVideo, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton_PlayVideo, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton_StopVideo, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_FastForwardVideo, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel_Volumen)
                        .addGap(1, 1, 1)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton_VolumeUp, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton_VolumeDownIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(91, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout PanelPrincipalLayout = new javax.swing.GroupLayout(PanelPrincipal);
        PanelPrincipal.setLayout(PanelPrincipalLayout);
        PanelPrincipalLayout.setHorizontalGroup(
            PanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        PanelPrincipalLayout.setVerticalGroup(
            PanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void jLabel_OpcionCuenta1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_OpcionCuenta1MouseClicked
        if (Usuario != null) {
            Ventana_AdmCuenta form = new Ventana_AdmCuenta(Usuario, "PaginaPrincipal");
            form.setVisible(true);
            dispose();
        } else {
            Ventana_IniciarSesion form = new Ventana_IniciarSesion("PaginaPrincipal");
            form.setVisible(true);
            dispose();
        }
    }//GEN-LAST:event_jLabel_OpcionCuenta1MouseClicked

    private void btnSubirVideoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubirVideoActionPerformed
        Ventana_SubirVideo form = new Ventana_SubirVideo(Usuario, "PaginaPrincipal");
        form.setVisible(true);
        dispose(); // Cierra la ventana actual (opcional)
    }//GEN-LAST:event_btnSubirVideoActionPerformed

    private void jLabel_OpcionCuenta2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_OpcionCuenta2MouseClicked
        if (Usuario != null) {
            VentanaPrincipal form = new VentanaPrincipal(null);
            form.setVisible(true);
            dispose();
        } else {
            Ventana_RegistrarUsuario form = new Ventana_RegistrarUsuario("PaginaPrincipal");
            form.setVisible(true);
            dispose();
        }
    }//GEN-LAST:event_jLabel_OpcionCuenta2MouseClicked

    private void jButton_VolumeUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_VolumeUpActionPerformed
        // TODO add your handling code here:
        if (mediaPlayer != null) {
            double currentVolume = mediaPlayer.getVolume();
            if (currentVolume < 1.0) {
                mediaPlayer.setVolume(currentVolume + 0.1);
            }
        }
    }//GEN-LAST:event_jButton_VolumeUpActionPerformed

    private void jButton_RewindVideoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_RewindVideoActionPerformed
        // TODO add your handling code here:
        if (mediaPlayer != null) {
            mediaPlayer.seek(mediaPlayer.getCurrentTime().subtract(javafx.util.Duration.seconds(10)));
        }
    }//GEN-LAST:event_jButton_RewindVideoActionPerformed

    private void jButton_PlayVideoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_PlayVideoActionPerformed
        // TODO add your handling code here:
        if (mediaPlayer != null) {
            mediaPlayer.play();
        }
    }//GEN-LAST:event_jButton_PlayVideoActionPerformed

    private void jButton_PauseVideoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_PauseVideoActionPerformed
        // TODO add your handling code here:
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }//GEN-LAST:event_jButton_PauseVideoActionPerformed

    private void jButton_StopVideoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_StopVideoActionPerformed
        // TODO add your handling code here:
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }//GEN-LAST:event_jButton_StopVideoActionPerformed

    private void jButton_FastForwardVideoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_FastForwardVideoActionPerformed
        // TODO add your handling code here:
        if (mediaPlayer != null) {
            mediaPlayer.seek(mediaPlayer.getCurrentTime().add(javafx.util.Duration.seconds(10)));
        }
    }//GEN-LAST:event_jButton_FastForwardVideoActionPerformed

    private void jButton_VolumeDownIconActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_VolumeDownIconActionPerformed
        // TODO add your handling code here:
        if (mediaPlayer != null) {
            double currentVolume = mediaPlayer.getVolume();
            if (currentVolume > 0.0) {
                mediaPlayer.setVolume(currentVolume - 0.1);
            }
        }
    }//GEN-LAST:event_jButton_VolumeDownIconActionPerformed

    //Metodo para verificar si hay un usuario que haya iniciado sesion y hacer los cambios necesarios a la pagina
    private void checarSesion() {
        if (Usuario != null) {
            btnSubirVideo.setVisible(true);
            jLabel_OpcionCuenta1.setText("Administrar Cuenta");
            jLabel_OpcionCuenta2.setText("Cerrar Sesion");
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
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

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
                new VentanaPrincipal(Usuario).setVisible(true);
            }
        });
        
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelPrincipal;
    private javax.swing.JButton btnSubirVideo;
    private javax.swing.JButton jButton_FastForwardVideo;
    private javax.swing.JButton jButton_PauseVideo;
    private javax.swing.JButton jButton_PlayVideo;
    private javax.swing.JButton jButton_RewindVideo;
    private javax.swing.JButton jButton_StopVideo;
    private javax.swing.JButton jButton_VolumeDownIcon;
    private javax.swing.JButton jButton_VolumeUp;
    private javax.swing.JComboBox<String> jComboBox_ListaVideos;
    private javax.swing.JLabel jLabel_Diagonal;
    private javax.swing.JLabel jLabel_Icono_UAATube;
    private javax.swing.JLabel jLabel_OpcionCuenta1;
    private javax.swing.JLabel jLabel_OpcionCuenta2;
    private javax.swing.JLabel jLabel_Volumen;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel_ReproducirVideo;
    private javax.swing.JTextField jTextField_BarraBusqueda;
    // End of variables declaration//GEN-END:variables
}
