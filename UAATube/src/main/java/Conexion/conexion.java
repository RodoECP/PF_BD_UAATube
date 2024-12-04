package Conexion;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 *
 * @author pedro
 */
public class conexion {
    //Metodo para realizar la conexion a la base de datos
    public static MongoDatabase crearConexion(String databaseName) {
        MongoDatabase database = null;
        try {
            String connectionString = "mongodb+srv://EquipoBD:RPD@cluster0.qrldq.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(new ConnectionString(connectionString))
                    .build();
            // Create a new client and connect to the server
            com.mongodb.client.MongoClient mongoClient = MongoClients.create(settings);
            database = mongoClient.getDatabase(databaseName);
            database.runCommand(new Document("ping", 1));
            System.out.println("Conexion a base de datos realizada!" + database);
            return database;
        } catch (MongoException e){
            System.out.println("Error al conectarse con la base de datos: " + e);
        }
        return database;
    }
}
