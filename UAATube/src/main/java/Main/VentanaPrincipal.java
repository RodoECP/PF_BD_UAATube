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

/**
 * Clase VentanaPrincipal Representa la ventana principal de la aplicación para
 * reproducción de videos. Incluye funciones para conectarse a MongoDB, cargar y
 * filtrar videos, controlar la reproducción multimedia y gestionar la interfaz
 * gráfica de usuario.
 */
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

    /**
     * Constructor sin parámetros. Se utiliza para inicializar la ventana
     * principal al inicio de la aplicación.
     */
    public VentanaPrincipal() {
        connectToMongoDB();
        initComponents();
        initializeVideoPlayback();
        loadVideoList();
        addMediaControls();
        checarSesion();
    }
    
    /**
     * Constructor con parámetros. Inicializa la ventana principal utilizando la
     * información del usuario y la base de datos.
     *
     * @param Usuario Documento del usuario autenticado.
     * @param database Base de datos de MongoDB.
     */
    public VentanaPrincipal(Document Usuario, MongoDatabase database) {
        this.database = database;
        gridFSBucket = GridFSBuckets.create(this.database);
        this.Usuario = Usuario;
        initComponents();
        initializeVideoPlayback();
        loadVideoList();
        addMediaControls();
        checarSesion();
    }

    /**
     * Conexión a MongoDB. Crea la conexión con la base de datos "UAATube" y
     * configura el almacén de archivos GridFS.
     */
    private void connectToMongoDB() {
        database = conexion.crearConexion("UAATube");
        gridFSBucket = GridFSBuckets.create(database);
    }

    /**
     * Inicialización de la reproducción de video. Configura el panel de JavaFX
     * para mostrar el video y ajusta su tamaño según el panel.
     */
    private void initializeVideoPlayback() {
        jfxPanel = new JFXPanel();
        jPanel_ReproducirVideo.setLayout(new BorderLayout());
        jPanel_ReproducirVideo.add(jfxPanel, BorderLayout.CENTER);
        jPanel_ReproducirVideo.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {

                Platform.runLater(() -> updateVideoSize());
            }
        });

        Platform.runLater(() -> {
            double initialWidth = jPanel_ReproducirVideo.getWidth();
            double initialHeight = jPanel_ReproducirVideo.getHeight();

            StackPane root = new StackPane();
            Scene scene = new Scene(root, initialWidth, initialHeight);
            jfxPanel.setScene(scene);

            MediaView mediaView = new MediaView();
            root.getChildren().add(mediaView);

            mediaPlayer = null;
        });
    }
    
    /**
     * Carga la lista de videos desde MongoDB. Filtra los archivos con
     * extensiones de video soportadas y los agrega a la tabla.
     */
    private void loadVideoList() {
        ListaVideos.clear();
        alistarTabla();
        try (MongoCursor<GridFSFile> cursor = gridFSBucket.find().iterator()) {
            while (cursor.hasNext()) {
                GridFSFile file = cursor.next();
                String filename = file.getFilename();
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
                    }
                }
            }
        }
    }

    /**
     * Obtiene el título de un video.
     *
     * @param fileID ID del archivo en MongoDB.
     * @return Título del video.
     */
    private String obtenerTitulo(ObjectId fileID) {
        String titulo = "";
        MongoCollection<Document> video = database.getCollection("Videos");
        Document videoDoc = video.find(Filters.and(
                Filters.eq("videoId", fileID)
        )).first();
        titulo = videoDoc.getString("title");
        return titulo;
    }
    
    /**
     * Filtra los videos según el criterio seleccionado por el usuario.
     *
     * @param fileID ID del archivo en MongoDB.
     * @return Verdadero si el video cumple con el filtro; falso en caso
     * contrario.
     */
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

    /**
     * Obtiene el ID de la miniatura asociada a un video.
     *
     * @param fileID ID del archivo en MongoDB.
     * @return ID de la miniatura.
     */
    private ObjectId obtenerMiniId(ObjectId fileID) {
        ObjectId miniId;
        MongoCollection<Document> video = database.getCollection("Videos");
        Document videoDoc = video.find(Filters.and(
                Filters.eq("videoId", fileID)
        )).first();
        miniId = videoDoc.getObjectId("thumbnailId");
        return miniId;
    }

    /**
     * Obtiene el nombre del archivo asociado a un ID en MongoDB.
     *
     * @param fileID ID del archivo en MongoDB.
     * @return Nombre del archivo.
     */
    private String obtenerFilename(ObjectId fileID) {
        String filename = "";
        MongoCollection<Document> archivo = database.getCollection("fs.files");
        Document videoDoc = archivo.find(Filters.and(
                Filters.eq("_id", fileID)
        )).first();
        filename = videoDoc.getString("filename");
        return filename;
    }

    /**
     * Reproduce un video desde GridFS. Detiene cualquier video en reproducción
     * y carga el nuevo video.
     *
     * @param filename Nombre del archivo de video.
     */
    private void playVideoFromGridFS(String filename) {
        stopVideo();
        File videoFile = downloadVideoFromGridFS(filename);
        if (videoFile != null) {
            Media media = new Media(videoFile.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            MediaView mediaView = (MediaView) ((StackPane) jfxPanel.getScene().getRoot()).getChildren().get(0);
            mediaView.setMediaPlayer(mediaPlayer);
            updateVideoSize();
            mediaPlayer.play();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to load video: " + filename, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Actualiza el tamaño del video para ajustarse al panel.
     */
    private void updateVideoSize() {
        if (mediaPlayer != null && mediaPlayer.getMedia() != null) {
            double width = jPanel_ReproducirVideo.getWidth();
            double height = jPanel_ReproducirVideo.getHeight();
            MediaView mediaView = (MediaView) ((StackPane) jfxPanel.getScene().getRoot()).getChildren().get(0);
            mediaView.setFitWidth(width);
            mediaView.setFitHeight(height);
            mediaView.setPreserveRatio(true);
            mediaView.setSmooth(true);
        }
    }

    /**
     * Descarga un video desde GridFS.
     *
     * @param filename Nombre del archivo de video.
     * @return Archivo de video descargado.
     */
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

    /**
     * Descarga una imagen desde GridFS en MongoDB. Este método busca el archivo
     * en GridFS usando el nombre de archivo proporcionado y lo guarda como un
     * archivo temporal en el sistema local.
     *
     * @param filename Nombre del archivo de imagen a descargar desde GridFS.
     * @return Archivo temporal que contiene la imagen descargada, o null si
     * ocurre un error.
     */
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

    /**
     * Detiene la reproducción de un video. Este método detiene el reproductor
     * multimedia si está en reproducción.
     */
    private void stopVideo() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    /**
     * Agrega los controles de medios (reproducción, pausa, detener, rebobinar,
     * adelantar, volumen) a la interfaz gráfica. Este método crea los botones
     * para controlar la reproducción de video, un control deslizante para el
     * volumen y los listeners necesarios para que los controles respondan a las
     * acciones del usuario.
     */
    private void addMediaControls() {
        playButton = new JButton("Play");
        pauseButton = new JButton("Pause");
        stopButton = new JButton("Stop");
        rewindButton = new JButton("Rewind");
        fastForwardButton = new JButton("Fast Forward");
        volumeSlider = new JSlider(0, 100, 50);
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
                    mediaPlayer.setVolume(volumeValue / 100.0);
                }
            });

        });

        controlsPanel = new JPanel();
        controlsPanel.setLayout(new FlowLayout());
        controlsPanel.add(playButton);
        controlsPanel.add(pauseButton);
        controlsPanel.add(stopButton);
        controlsPanel.add(rewindButton);
        controlsPanel.add(fastForwardButton);
        controlsPanel.add(new JLabel("Volume:"));
        controlsPanel.add(volumeSlider);

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
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaDescripcion = new javax.swing.JTextArea();
        jLabelDescripcion = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldCanal = new javax.swing.JTextField();
        jButtonComentarios = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel_Icono_UAATube = new javax.swing.JLabel();
        jComboBoxFiltros = new javax.swing.JComboBox<>();
        jTextField_BarraBusqueda = new javax.swing.JTextField();
        jButtonBuscar = new javax.swing.JButton();
        btnSubirVideo = new javax.swing.JButton();
        jLabel_OpcionCuenta1 = new javax.swing.JLabel();
        jLabel_Diagonal = new javax.swing.JLabel();
        jLabel_OpcionCuenta2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(38, 38, 38));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel_ReproducirVideo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel_ReproducirVideo.setMinimumSize(new java.awt.Dimension(780, 469));
        jPanel_ReproducirVideo.setPreferredSize(new java.awt.Dimension(780, 469));

        javax.swing.GroupLayout jPanel_ReproducirVideoLayout = new javax.swing.GroupLayout(jPanel_ReproducirVideo);
        jPanel_ReproducirVideo.setLayout(jPanel_ReproducirVideoLayout);
        jPanel_ReproducirVideoLayout.setHorizontalGroup(
            jPanel_ReproducirVideoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 778, Short.MAX_VALUE)
        );
        jPanel_ReproducirVideoLayout.setVerticalGroup(
            jPanel_ReproducirVideoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel_ReproducirVideo, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 101, -1, -1));

        jButton_PlayVideo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PlayIcon.png"))); // NOI18N
        jButton_PlayVideo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_PlayVideoActionPerformed(evt);
            }
        });
        jPanel1.add(jButton_PlayVideo, new org.netbeans.lib.awtextra.AbsoluteConstraints(99, 603, 64, 65));

        jButton_StopVideo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/StopIcon.png"))); // NOI18N
        jButton_StopVideo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_StopVideoActionPerformed(evt);
            }
        });
        jPanel1.add(jButton_StopVideo, new org.netbeans.lib.awtextra.AbsoluteConstraints(239, 602, 63, 66));

        jButton_PauseVideo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PauseIcon.png"))); // NOI18N
        jButton_PauseVideo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_PauseVideoActionPerformed(evt);
            }
        });
        jPanel1.add(jButton_PauseVideo, new org.netbeans.lib.awtextra.AbsoluteConstraints(169, 603, 64, 65));

        jButton_RewindVideo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/RewindIcon.png"))); // NOI18N
        jButton_RewindVideo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_RewindVideoActionPerformed(evt);
            }
        });
        jPanel1.add(jButton_RewindVideo, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 603, 64, 65));

        jButton_FastForwardVideo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/FastForwardIcon.png"))); // NOI18N
        jButton_FastForwardVideo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_FastForwardVideoActionPerformed(evt);
            }
        });
        jPanel1.add(jButton_FastForwardVideo, new org.netbeans.lib.awtextra.AbsoluteConstraints(308, 603, 64, 65));

        jButton_VolumeUp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/VolumeUp.png"))); // NOI18N
        jButton_VolumeUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_VolumeUpActionPerformed(evt);
            }
        });
        jPanel1.add(jButton_VolumeUp, new org.netbeans.lib.awtextra.AbsoluteConstraints(694, 603, 64, 65));

        jButton_VolumeDownIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/VolumeDown.png"))); // NOI18N
        jButton_VolumeDownIcon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_VolumeDownIconActionPerformed(evt);
            }
        });
        jPanel1.add(jButton_VolumeDownIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(624, 603, 64, 65));

        jTableListaVideos.setBackground(new java.awt.Color(33, 79, 154));
        jTableListaVideos.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jTableListaVideos.setForeground(new java.awt.Color(255, 255, 255));
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

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 100, 290, 490));
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1408, 106, -1, -1));

        jTextAreaDescripcion.setColumns(20);
        jTextAreaDescripcion.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jTextAreaDescripcion.setLineWrap(true);
        jTextAreaDescripcion.setRows(5);
        jTextAreaDescripcion.setWrapStyleWord(true);
        jTextAreaDescripcion.setAutoscrolls(false);
        jScrollPane2.setViewportView(jTextAreaDescripcion);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 696, 310, -1));

        jLabelDescripcion.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabelDescripcion.setForeground(new java.awt.Color(255, 255, 255));
        jLabelDescripcion.setText("Descripcion");
        jPanel1.add(jLabelDescripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 674, 174, -1));

        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Canal:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(394, 699, -1, -1));

        jTextFieldCanal.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jPanel1.add(jTextFieldCanal, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 696, 142, -1));

        jButtonComentarios.setBackground(new java.awt.Color(22, 62, 100));
        jButtonComentarios.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jButtonComentarios.setForeground(new java.awt.Color(255, 255, 255));
        jButtonComentarios.setText("Ver Comentarios");
        jButtonComentarios.setEnabled(false);
        jButtonComentarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonComentariosActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonComentarios, new org.netbeans.lib.awtextra.AbsoluteConstraints(671, 696, 199, 87));

        jPanel4.setBackground(new java.awt.Color(89, 89, 89));

        jLabel_Icono_UAATube.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UAATube Icon 150x92.png"))); // NOI18N

        jComboBoxFiltros.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jComboBoxFiltros.setForeground(new java.awt.Color(255, 255, 255));
        jComboBoxFiltros.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Titulo", "Canal", "Descripcion" }));

        jTextField_BarraBusqueda.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jTextField_BarraBusqueda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_BarraBusquedaActionPerformed(evt);
            }
        });

        jButtonBuscar.setBackground(new java.awt.Color(22, 62, 100));
        jButtonBuscar.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jButtonBuscar.setForeground(new java.awt.Color(255, 255, 255));
        jButtonBuscar.setText("Buscar");
        jButtonBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscarActionPerformed(evt);
            }
        });

        btnSubirVideo.setBackground(new java.awt.Color(22, 62, 100));
        btnSubirVideo.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        btnSubirVideo.setForeground(new java.awt.Color(255, 255, 255));
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

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel_Icono_UAATube)
                .addGap(29, 29, 29)
                .addComponent(jComboBoxFiltros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField_BarraBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 553, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonBuscar)
                .addGap(53, 53, 53)
                .addComponent(btnSubirVideo)
                .addGap(41, 41, 41)
                .addComponent(jLabel_OpcionCuenta1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel_Diagonal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel_OpcionCuenta2)
                .addContainerGap(186, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel_Icono_UAATube))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel_OpcionCuenta1)
                                .addComponent(jLabel_Diagonal)
                                .addComponent(jLabel_OpcionCuenta2))
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jComboBoxFiltros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField_BarraBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButtonBuscar)
                                .addComponent(btnSubirVideo)))))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1480, -1));

        javax.swing.GroupLayout PanelPrincipalLayout = new javax.swing.GroupLayout(PanelPrincipal);
        PanelPrincipal.setLayout(PanelPrincipalLayout);
        PanelPrincipalLayout.setHorizontalGroup(
            PanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelPrincipalLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1481, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        PanelPrincipalLayout.setVerticalGroup(
            PanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 802, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    /**
     * Evento que maneja el clic en el componente `jLabel_OpcionCuenta1`. Este
     * método verifica si el usuario está autenticado (si el objeto `Usuario` no
     * es nulo). Si el usuario está autenticado, abre la ventana de
     * administración de cuenta (`Ventana_AdmCuenta`). Si el usuario no está
     * autenticado, abre la ventana de inicio de sesión
     * (`Ventana_IniciarSesion`).
     *
     * @param evt El evento del clic del mouse que activa la acción.
     */
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

    /**
     * Evento que maneja la acción de hacer clic en el botón `btnSubirVideo`.
     * Abre la ventana para subir un video (`Ventana_SubirVideo`).
     *
     * @param evt El evento de acción que activa este método.
     */
    private void btnSubirVideoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubirVideoActionPerformed
        Ventana_SubirVideo form = new Ventana_SubirVideo(Usuario, "PaginaPrincipal", database);
        form.setVisible(true);
        dispose(); // Cierra la ventana actual (opcional)
    }//GEN-LAST:event_btnSubirVideoActionPerformed

    /**
     * Evento que maneja el clic en el componente `jLabel_OpcionCuenta2`. Si el
     * usuario está autenticado, abre la ventana principal (`VentanaPrincipal`).
     * Si el usuario no está autenticado, abre la ventana de registro de usuario
     * (`Ventana_RegistrarUsuario`).
     *
     * @param evt El evento de clic del mouse que activa la acción.
     */
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
             
    /**
     * Evento que maneja la acción de subir el volumen del reproductor de video.
     * Aumenta el volumen en 0.1 si el volumen actual es menor que 1.0 (volumen
     * máximo).
     *
     * @param evt El evento de acción que activa este método.
     */
    private void jButton_VolumeUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_VolumeUpActionPerformed
        // TODO add your handling code here:
        if (mediaPlayer != null) {
            double currentVolume = mediaPlayer.getVolume();
            if (currentVolume < 1.0) {
                mediaPlayer.setVolume(currentVolume + 0.1);
            }
        }
    }//GEN-LAST:event_jButton_VolumeUpActionPerformed

    /**
     * Evento que maneja la acción de retroceder el video en 10 segundos. Se
     * ejecuta si el reproductor de video está activo.
     *
     * @param evt El evento de acción que activa este método.
     */
    private void jButton_RewindVideoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_RewindVideoActionPerformed
        // TODO add your handling code here:
        if (mediaPlayer != null) {
            mediaPlayer.seek(mediaPlayer.getCurrentTime().subtract(javafx.util.Duration.seconds(10)));
        }
    }//GEN-LAST:event_jButton_RewindVideoActionPerformed

    /**
     * Evento que maneja la acción de reproducir el video. Se ejecuta si el
     * reproductor de video está activo.
     *
     * @param evt El evento de acción que activa este método.
     */
    private void jButton_PlayVideoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_PlayVideoActionPerformed
        // TODO add your handling code here:
        if (mediaPlayer != null) {
            mediaPlayer.play();
        }
    }//GEN-LAST:event_jButton_PlayVideoActionPerformed

    /**
     * Evento que maneja la acción de pausar el video. Se ejecuta si el
     * reproductor de video está activo.
     *
     * @param evt El evento de acción que activa este método.
     */
    private void jButton_PauseVideoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_PauseVideoActionPerformed
        // TODO add your handling code here:
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }//GEN-LAST:event_jButton_PauseVideoActionPerformed

    /**
     * Evento que maneja la acción de detener el video. Se ejecuta si el
     * reproductor de video está activo.
     *
     * @param evt El evento de acción que activa este método.
     */
    private void jButton_StopVideoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_StopVideoActionPerformed
        // TODO add your handling code here:
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }//GEN-LAST:event_jButton_StopVideoActionPerformed

    /**
     * Evento que maneja la acción de avanzar el video en 10 segundos. Se
     * ejecuta si el reproductor de video está activo.
     *
     * @param evt El evento de acción que activa este método.
     */
    private void jButton_FastForwardVideoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_FastForwardVideoActionPerformed
        // TODO add your handling code here:
        if (mediaPlayer != null) {
            mediaPlayer.seek(mediaPlayer.getCurrentTime().add(javafx.util.Duration.seconds(10)));
        }
    }//GEN-LAST:event_jButton_FastForwardVideoActionPerformed

    /**
     * Evento que maneja la acción de bajar el volumen del reproductor de video.
     * Disminuye el volumen en 0.1 si el volumen actual es mayor que 0.0
     * (volumen mínimo).
     *
     * @param evt El evento de acción que activa este método.
     */
    private void jButton_VolumeDownIconActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_VolumeDownIconActionPerformed
        // TODO add your handling code here:
        if (mediaPlayer != null) {
            double currentVolume = mediaPlayer.getVolume();
            if (currentVolume > 0.0) {
                mediaPlayer.setVolume(currentVolume - 0.1);
            }
        }
    }//GEN-LAST:event_jButton_VolumeDownIconActionPerformed

    /**
     * Evento que maneja la acción de hacer clic en una fila de la tabla
     * `jTableListaVideos`. Al hacer clic, se reproduce el video desde GridFS y
     * se cargan los datos del video en los campos correspondientes.
     *
     * @param evt El evento de clic del mouse que activa este método.
     */
    private void jTableListaVideosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableListaVideosMouseClicked
        Platform.runLater(() -> playVideoFromGridFS(obtenerFilename(ListaVideos.get(jTableListaVideos.getSelectedRow()))));
        cargarDatos(ListaVideos.get(jTableListaVideos.getSelectedRow()));
        jButtonComentarios.setEnabled(true);
    }//GEN-LAST:event_jTableListaVideosMouseClicked

    /**
     * Evento que maneja la acción de buscar videos al presionar el botón
     * `jButtonBuscar`. Este método carga la lista de videos disponibles.
     *
     * @param evt El evento de acción que activa este método.
     */
    private void jButtonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarActionPerformed
        loadVideoList();
    }//GEN-LAST:event_jButtonBuscarActionPerformed

    /**
     * Evento que maneja la acción de abrir la ventana de comentarios al
     * presionar el botón `jButtonComentarios`. Abre una nueva ventana para ver
     * y añadir comentarios al video seleccionado.
     *
     * @param evt El evento de acción que activa este método.
     */
    private void jButtonComentariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonComentariosActionPerformed
        Ventana_Comentarios form = new Ventana_Comentarios(Usuario,cargarVideo(ListaVideos.get(jTableListaVideos.getSelectedRow())), database);
        form.setVisible(true);
        form.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }//GEN-LAST:event_jButtonComentariosActionPerformed

    /**
     * Método para verificar si hay un usuario autenticado y realizar los
     * cambios necesarios en la interfaz. Si el usuario está autenticado, se
     * muestra la opción para administrar la cuenta y cerrar sesión.
     */
    private void checarSesion() {
        if (Usuario != null) {
            btnSubirVideo.setVisible(true);
            jLabel_OpcionCuenta1.setText("Administrar Cuenta");
            jLabel_OpcionCuenta2.setText("Cerrar Sesion");
        }
    }
    
    /**
     * Carga los datos del video en los campos de texto `jTextFieldCanal` y
     * `jTextAreaDescripcion`.
     *
     * @param fileID El ID del archivo del video que se va a cargar.
     */
    private void cargarDatos(ObjectId fileID) {
        MongoCollection<Document> video = database.getCollection("Videos");
        Document videoDoc = video.find(Filters.and(
                Filters.eq("videoId", fileID)
        )).first();
        jTextFieldCanal.setText(videoDoc.getString("autor"));
        jTextAreaDescripcion.setText(videoDoc.getString("description"));
    }
    
    /**
     * Carga los datos completos del video en un documento de la base de datos.
     *
     * @param fileID El ID del archivo del video que se va a cargar.
     * @return El documento del video cargado desde la base de datos.
     */
    private Document cargarVideo(ObjectId fileID) {
        MongoCollection<Document> video = database.getCollection("Videos");
        Document videoDoc = video.find(Filters.and(
                Filters.eq("videoId", fileID)
        )).first();
        return videoDoc;
    }

    /**
     * Configura la tabla `jTableListaVideos` con los ajustes personalizados.
     * Ajusta el modelo de la tabla, el tamaño de las columnas, la altura de las
     * filas, y otros parámetros de visualización.
     */
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
    private javax.swing.JButton jButtonComentarios;
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
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel_ReproducirVideo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableListaVideos;
    private javax.swing.JTextArea jTextAreaDescripcion;
    private javax.swing.JTextField jTextFieldCanal;
    private javax.swing.JTextField jTextField_BarraBusqueda;
    // End of variables declaration//GEN-END:variables
}
