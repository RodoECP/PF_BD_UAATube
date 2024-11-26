package Conexion;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import com.mongodb.client.MongoDatabase;

/**
 *
 * @author pedro
 */
public class conexion {
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
}
