package unisannio.assd.gruppo3.RTC.utils;



import com.mongodb.MongoCommandException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;


public class ConnectionDB {
	private static ConnectionDB instance = null;
	private MongoDatabase database;
	public ConnectionDB() {}
	public static ConnectionDB getInstance() {
		if (instance == null) {
			instance = new ConnectionDB();
		}
		return instance;
	}

	public void connectToDB(String uri) {
		// Replace the placeholder with your MongoDB deployment's connection string
		// String uri = "mongodb://127.0.0.1:27017";

		MongoClient mongoClient = MongoClients.create(uri);
		try {
			database = mongoClient.getDatabase("RTCStorage");
			if(database.getCollection("LoadDatas") == null) database.createCollection("LoadDatas");
		} catch (MongoCommandException e) {
			System.err.println("Already exist: " + e.getMessage());
		}
		System.out.println("Connessione effettuata al db: "+database.getName());
	}

	public MongoDatabase getDatabase() {
		return database;
	}



}
