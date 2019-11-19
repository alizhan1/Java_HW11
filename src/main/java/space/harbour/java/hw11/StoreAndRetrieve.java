package space.harbour.java.hw10;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.print.Doc;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.mongodb.client.model.Filters.eq;

public class StoreAndRetrieve {

    private static MongoClient mongo;

    public StoreAndRetrieve(String host, Integer port) {
        this.mongo = new MongoClient(host, port);
    }

    public static void main(String[] args) throws IOException {
        StoreAndRetrieve service = new StoreAndRetrieve("localhost", 27017);
        service.store("Batman.json");
        Document d = service.retrieve("Batman");
        mongo.close();
    }

    public void store(String filename) throws IOException {
        String content = "";
        content = new String(Files.readAllBytes(Paths.get(filename)));
        Document doc = Document.parse(content);
        MongoDatabase mongoDb = mongo.getDatabase("MOVIE_DB");
        MongoCollection<Document> collection = mongoDb.getCollection("MOVIE");
        collection.insertOne(doc);
    }

    public Document retrieve(String title) {
        MongoDatabase mongoDb = mongo.getDatabase("MOVIE_DB");
        MongoCollection<Document> collection = mongoDb.getCollection("MOVIE");
        return collection.find(eq("Title", title)).first();
    }
}
