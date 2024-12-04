package Main;

import Conexion.conexion;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.model.Filters;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import static com.sun.java.accessibility.util.SwingEventMonitor.addListSelectionListener;
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
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.bson.Document;
import org.bson.types.ObjectId;

public class VentanaPrincipal extends javax.swing.JFrame {

    private JFXPanel jfxPanel;
    private MediaPlayer mediaPlayer;
    private GridFSBucket gridFSBucket;
    private JSlider volumeSlider;
    private JPanel controlsPanel;
    private JButton playButton, pauseButton, stopButton, rewindButton, fastForwardButton;
    private static Document Usuario;
    private static MongoDatabase database;
    List<ObjectId> ListaVideos = new ArrayList<>();

    //Constructor que se utilizara solo para el inicio de la aplicacion
    public VentanaPrincipal() {
        connectToMongoDB();
        initComponents();
        initializeVideoPlayback();
        loadVideoList();
        addMediaControls();
        checarSesion();
    }

    public VentanaPrincipal(Document Usuario, MongoDatabase database) {
        this.database = database;
        gridFSBucket = GridFSBuckets.create(this.database);
        initComponents();
        initializeVideoPlayback();
        loadVideoList();
        addMediaControls();
        checarSesion();
        this.Usuario = Usuario;
    }

    private void connectToMongoDB() {
        database = conexion.crearConexion("UAATube");
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
        ListaVideos.clear();
        alistarTabla();
        try (MongoCursor<GridFSFile> cursor = gridFSBucket.find().iterator()) {
            while (cursor.hasNext()) {
                GridFSFile file = cursor.next();
                String filename = file.getFilename();
                // Filter files based on video extensions
                if (filename.toLowerCase().endsWith(".mp4")
                        || filename.toLowerCase().endsWith(".avi")
                        || filename.toLowerCase().endsWith(".mkv")
                        || filename.toLowerCase().endsWith(".mov")
                        || filename.toLowerCase().endsWith(".wmv")) {
                    if (filtrarVideos(file.getObjectId())){
                        DefaultTableModel model = (DefaultTableModel) jTableListaVideos.getModel();
                        ImageIcon icon = new ImageIcon(downloadImageFromGridFS(obtenerFilename(obtenerMiniId(file.getObjectId()))).toString());
                        Image img = icon.getImage().getScaledInstance(112, 63, java.awt.Image.SCALE_SMOOTH);
                        ImageIcon newIcon = new ImageIcon(img);
                        model.addRow(new Object[]{newIcon, obtenerTitulo(file.getObjectId())});
                        ListaVideos.add(file.getObjectId());
                        System.out.println(downloadImageFromGridFS(obtenerFilename(obtenerMiniId(file.getObjectId()))).toString());
                    }
                }
            }
        }
    }

    private String obtenerTitulo(ObjectId fileID) {
        String titulo = "";
        MongoCollection<Document> video = database.getCollection("Videos");
        Document videoDoc = video.find(Filters.and(
                Filters.eq("videoId", fileID)
        )).first();
        titulo = videoDoc.getString("title");
        return titulo;
    }
    
    private boolean filtrarVideos(ObjectId fileID){
        MongoCollection<Document> video = database.getCollection("Videos");
        Document videoDoc = video.find(Filters.and(
                Filters.eq("videoId", fileID)
        )).first();
        switch (jComboBoxFiltros.getSelectedItem().toString()){
            case "Titulo":
                if (videoDoc.getString("title").toLowerCase().contains(jTextField_BarraBusqueda.getText().toLowerCase())){
                  return true;  
                }
                break;
            case "Descripcion":
                if (videoDoc.getString("description").toLowerCase().contains(jTextField_BarraBusqueda.getText().toLowerCase())){
                  return true;  
                }
                break;
            case "Canal":
                if (videoDoc.getString("autor").toLowerCase().contains(jTextField_BarraBusqueda.getText().toLowerCase())){
                  return true;  
                }
        }
        return false;
    }

    private ObjectId obtenerMiniId(ObjectId fileID) {
        ObjectId miniId;
        MongoCollection<Document> video = database.getCollection("Videos");
        Document videoDoc = video.find(Filters.and(
                Filters.eq("videoId", fileID)
        )).first();
        miniId = videoDoc.getObjectId("thumbnailId");
        return miniId;
    }

    private String obtenerFilename(ObjectId fileID) {
        String filename = "";
        MongoCollection<Document> archivo = database.getCollection("fs.files");
        Document videoDoc = archivo.find(Filters.and(
                Filters.eq("_id", fileID)
        )).first();
        filename = videoDoc.getString("filename");
        return filename;
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

    private File downloadImageFromGridFS(String filename) {
        try {
            GridFSFile gridFSFile = gridFSBucket.find(new org.bson.Document("filename", filename)).first();
            if (gridFSFile != null) {
                File tempFile = File.createTempFile("imagen", ".jpg");
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
        jButton_PlayVideo = new javax.swing.JButton();
        jButton_StopVideo = new javax.swing.JButton();
        jButton_PauseVideo = new javax.swing.JButton();
        jButton_RewindVideo = new javax.swing.JButton();
        jButton_FastForwardVideo = new javax.swing.JButton();
        jButton_VolumeUp = new javax.swing.JButton();
        jButton_VolumeDownIcon = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableListaVideos = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jComboBoxFiltros = new javax.swing.JComboBox<>();
        jButtonBuscar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaDescripcion = new javax.swing.JTextArea();
        jLabelDescripcion = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldCanal = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(29, 113, 150));

        jTextField_BarraBusqueda.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
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
            .addGap(0, 862, Short.MAX_VALUE)
        );
        jPanel_ReproducirVideoLayout.setVerticalGroup(
            jPanel_ReproducirVideoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

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

        jTableListaVideos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableListaVideos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTableListaVideos.setAutoscrolls(false);
        jTableListaVideos.setColumnSelectionAllowed(true);
        jTableListaVideos.setEditingColumn(0);
        jTableListaVideos.setEditingRow(0);
        jTableListaVideos.setMaximumSize(new java.awt.Dimension(290, 582));
        jTableListaVideos.setMinimumSize(new java.awt.Dimension(290, 582));
        jTableListaVideos.setRowHeight(63);
        jTableListaVideos.setShowHorizontalLines(true);
        jTableListaVideos.getTableHeader().setReorderingAllowed(false);
        jTableListaVideos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableListaVideosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableListaVideos);
        jTableListaVideos.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (jTableListaVideos.getColumnModel().getColumnCount() > 0) {
            jTableListaVideos.getColumnModel().getColumn(0).setResizable(false);
            jTableListaVideos.getColumnModel().getColumn(0).setPreferredWidth(112);
            jTableListaVideos.getColumnModel().getColumn(1).setResizable(false);
            jTableListaVideos.getColumnModel().getColumn(1).setPreferredWidth(168);
        }

        jComboBoxFiltros.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Titulo", "Canal", "Descripcion" }));

        jButtonBuscar.setText("Buscar");
        jButtonBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscarActionPerformed(evt);
            }
        });

        jTextAreaDescripcion.setColumns(20);
        jTextAreaDescripcion.setRows(5);
        jScrollPane2.setViewportView(jTextAreaDescripcion);

        jLabelDescripcion.setText("Descripcion");

        jLabel2.setText("Canal:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel_Icono_UAATube)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                                .addComponent(jComboBoxFiltros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField_BarraBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 553, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonBuscar)
                                .addGap(121, 121, 121))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jButton_RewindVideo, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton_PlayVideo, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton_PauseVideo, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton_StopVideo, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton_FastForwardVideo, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(252, 252, 252)
                                        .addComponent(jButton_VolumeDownIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton_VolumeUp, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jPanel_ReproducirVideo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(24, 24, 24)
                                .addComponent(jLabel1))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnSubirVideo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel_OpcionCuenta1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel_Diagonal)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel_OpcionCuenta2)))
                        .addGap(56, 56, 56))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(94, 94, 94)
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(jTextFieldCanal, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel_Icono_UAATube))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField_BarraBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel_OpcionCuenta1)
                            .addComponent(jLabel_Diagonal)
                            .addComponent(jLabel_OpcionCuenta2)
                            .addComponent(btnSubirVideo)
                            .addComponent(jComboBoxFiltros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonBuscar))))
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel_ReproducirVideo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jButton_RewindVideo, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton_PauseVideo, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton_PlayVideo, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton_StopVideo, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton_FastForwardVideo, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton_VolumeUp, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton_VolumeDownIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(9, 9, 9)))
                        .addGap(18, 18, 18)
                        .addComponent(jLabelDescripcion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2)
                                .addComponent(jTextFieldCanal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(80, 80, 80))))
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
            Ventana_AdmCuenta form = new Ventana_AdmCuenta(Usuario, "PaginaPrincipal", database);
            form.setVisible(true);
            dispose();
        } else {
            Ventana_IniciarSesion form = new Ventana_IniciarSesion("PaginaPrincipal", database);
            form.setVisible(true);
            dispose();
        }
    }//GEN-LAST:event_jLabel_OpcionCuenta1MouseClicked

    private void btnSubirVideoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubirVideoActionPerformed
        Ventana_SubirVideo form = new Ventana_SubirVideo(Usuario, "PaginaPrincipal", database);
        form.setVisible(true);
        dispose(); // Cierra la ventana actual (opcional)
    }//GEN-LAST:event_btnSubirVideoActionPerformed

    private void jLabel_OpcionCuenta2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_OpcionCuenta2MouseClicked
        if (Usuario != null) {
            VentanaPrincipal form = new VentanaPrincipal(null, database);
            form.setVisible(true);
            dispose();
        } else {
            Ventana_RegistrarUsuario form = new Ventana_RegistrarUsuario("PaginaPrincipal", database);
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

    private void jTableListaVideosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableListaVideosMouseClicked
        Platform.runLater(() -> playVideoFromGridFS(obtenerFilename(ListaVideos.get(jTableListaVideos.getSelectedRow()))));
        cargarDatos(ListaVideos.get(jTableListaVideos.getSelectedRow()));
    }//GEN-LAST:event_jTableListaVideosMouseClicked

    private void jButtonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarActionPerformed
        loadVideoList();
    }//GEN-LAST:event_jButtonBuscarActionPerformed

    //Metodo para verificar si hay un usuario que haya iniciado sesion y hacer los cambios necesarios a la pagina
    private void checarSesion() {
        if (Usuario != null) {
            btnSubirVideo.setVisible(true);
            jLabel_OpcionCuenta1.setText("Administrar Cuenta");
            jLabel_OpcionCuenta2.setText("Cerrar Sesion");
        }
    }
    
    
    private void cargarDatos(ObjectId fileID) {
        MongoCollection<Document> video = database.getCollection("Videos");
        Document videoDoc = video.find(Filters.and(
                Filters.eq("videoId", fileID)
        )).first();
        jTextFieldCanal.setText(videoDoc.getString("autor"));
        jTextAreaDescripcion.setText(videoDoc.getString("description"));
        
    }

    //Metodo para sobreescribir los ajustes que netbeans asigna automaticamente a tablas
    private void alistarTabla() {
        jTableListaVideos.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "", ""
                }
        ) {
            Class[] types = new Class[]{
                javax.swing.ImageIcon.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean[]{
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        jTableListaVideos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

        jTableListaVideos.setAutoscrolls(false);

        jTableListaVideos.setColumnSelectionAllowed(true);

        jTableListaVideos.setEditingColumn(0);

        jTableListaVideos.setEditingRow(0);

        jTableListaVideos.setMaximumSize(new java.awt.Dimension(290, 582));

        jTableListaVideos.setMinimumSize(new java.awt.Dimension(290, 582));

        jTableListaVideos.setRowHeight(63);

        jTableListaVideos.setShowHorizontalLines(true);

        jTableListaVideos.getTableHeader().setReorderingAllowed(false);

        jTableListaVideos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        if (jTableListaVideos.getColumnModel().getColumnCount() > 0) {
            jTableListaVideos.getColumnModel().getColumn(0).setResizable(false);
            jTableListaVideos.getColumnModel().getColumn(0).setPreferredWidth(112);
            jTableListaVideos.getColumnModel().getColumn(1).setResizable(false);
            jTableListaVideos.getColumnModel().getColumn(1).setPreferredWidth(168);
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
                new VentanaPrincipal().setVisible(true);
            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelPrincipal;
    private javax.swing.JButton btnSubirVideo;
    private javax.swing.JButton jButtonBuscar;
    private javax.swing.JButton jButton_FastForwardVideo;
    private javax.swing.JButton jButton_PauseVideo;
    private javax.swing.JButton jButton_PlayVideo;
    private javax.swing.JButton jButton_RewindVideo;
    private javax.swing.JButton jButton_StopVideo;
    private javax.swing.JButton jButton_VolumeDownIcon;
    private javax.swing.JButton jButton_VolumeUp;
    private javax.swing.JComboBox<String> jComboBoxFiltros;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabelDescripcion;
    private javax.swing.JLabel jLabel_Diagonal;
    private javax.swing.JLabel jLabel_Icono_UAATube;
    private javax.swing.JLabel jLabel_OpcionCuenta1;
    private javax.swing.JLabel jLabel_OpcionCuenta2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel_ReproducirVideo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableListaVideos;
    private javax.swing.JTextArea jTextAreaDescripcion;
    private javax.swing.JTextField jTextFieldCanal;
    private javax.swing.JTextField jTextField_BarraBusqueda;
    // End of variables declaration//GEN-END:variables
}
