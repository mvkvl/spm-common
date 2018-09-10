package ws.slink.spm.db;

import org.bson.BSON;
import org.bson.BsonDocument;
import org.bson.BsonDocumentWriter;
import org.bson.Transformer;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;

import ws.slink.spm.model.SRNote;

public class DatastoreProvider {
	private final Morphia morphia = new Morphia();
	private MongoClient   mongoClient;
	private Datastore     datastore;
	private String        dbHost, dbName;
	private int           dbPort;
	
	/**
	 * constructor
	 * @param dbHost - MongoDB host
	 * @param dbPort - MongoDB port
	 * @param dbName - MongoDB database name
	 */
	public DatastoreProvider(String dbHost, int dbPort, String dbName) {
		morphia.mapPackage("package ws.slink.spm.sr.model");
		connect(dbHost, dbPort, dbName);
	}

	/**
	 * connect to MongoDB with given parameters
	 * @param dbHost - MongoDB host
	 * @param dbPort - MongoDB port
	 * @param dbName - MongoDB database name
	 */
	public void connect(String dbHost, int dbPort, String dbName) {
		this.dbHost = dbHost;
		this.dbPort = dbPort;
		this.dbName = dbName;
		connect();
	}

//	public void connect(String dbName) {
//		datastore   = morphia.createDatastore(mongoClient, dbName);
//		datastore.ensureIndexes();
//	}
	
	/**
	 * connect to MongoDB with pre-set parameters
	 */
	private void addBSONEncoding() {
		 // Add a global encoding hook to transform a UUID into a Binary with subtype of 4   
	      BSON.addEncodingHook(SRNote.class, new Transformer() {
			@Override
			public Object transform(Object objectToTransform) {
				SRNote note = (SRNote) objectToTransform;
				BsonDocument holder = new BsonDocument();
				BsonDocumentWriter writer = new BsonDocumentWriter(holder);
				writer.writeStartDocument();
				writer.writeStartDocument("srnote");
				writer.writeName("text");
				writer.writeString(note.text);
				writer.writeName("date");
				writer.writeDateTime(note.date.getTime());
				writer.writeName("type");
				writer.writeString(note.type);
				writer.writeEndDocument();
				writer.writeEndDocument();
//				writer.writeName("srnote");
//				writer.writeString(note.toString());
				writer.close();
				BsonDocument bsonBinary = holder.getDocument("srnote");
				return bsonBinary;//new Binary(bsonBinary.getType(), bsonBinary.getData());
			}
		});
	}
	private void addBSONDecoding() {
		BSON.addDecodingHook(SRNote.class, new Transformer() {
			@Override
			public Object transform(Object obj) {
				System.out.println(obj.getClass().getName());
				System.out.println(obj);
				return null;
			}
		});
	}
	public void connect() {
		addBSONEncoding();
		addBSONDecoding();

//		System.out.println("   -----> CONNECTING TO DATABASE");
//		CodecRegistry cr = CodecRegistries.fromRegistries(
//								CodecRegistries.fromProviders(new SRNoteCodecProvider()),
//								MongoClient.getDefaultCodecRegistry());
//		MongoClientOptions opts = MongoClientOptions.builder()
//				                                    .codecRegistry(cr)
//				                                    .build();
//		mongoClient = new MongoClient(new ServerAddress(dbHost, dbPort), opts);

		mongoClient = new MongoClient(dbHost, dbPort);
		datastore   = morphia.createDatastore(mongoClient, dbName);
		datastore.ensureIndexes();
	}

	/**
	 * disconnect from MongoDB
	 */
	public void disconnect() {
		mongoClient.close();
		this.dbHost = null;
		this.dbPort = 0;
		this.dbName = null;
	}

	/**
	 * returns datastore for further usage
	 * @return
	 */
	public Datastore datastore() {
		return datastore;
	}
}
