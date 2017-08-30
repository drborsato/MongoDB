package course.week03.homework01;

import java.util.ArrayList;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class App {

	public static void main(String[] args) {

		String mongoURI = "mongodb://localhost";
		MongoClient client = new MongoClient(new MongoClientURI(mongoURI));
		MongoDatabase database = client.getDatabase("school");
		MongoCollection<Document> collection = database.getCollection("students");

		FindIterable<Document> resultado;
		Bson sortStudent = new Document("_id", 1);
		resultado = collection.find().sort(sortStudent);

		int studentId;
		ArrayList<Document> scores;
		
		for (Document document : resultado) {
			
			studentId = document.getInteger("_id");
			
			ArrayList<Document> newScores = new ArrayList<Document>();
			scores = (ArrayList<Document>) document.get("scores");
			Double homework = 0.0;
			for (Document grade : scores) {
				if (grade.get("type").equals("homework")) {
					if(grade.getDouble("score")>homework)
						homework = grade.getDouble("score");
				} else {
					newScores.add(grade);
				}
			}
			newScores.add( new Document("type","homework").append("score", homework));

//			System.out.println(studentId + scores.toString());
//			System.out.println(studentId + newScores.toString());

			 collection.updateOne(new Document("_id", studentId), new
			 Document("$set", new Document("scores",newScores)));

		}

		resultado = collection.find().sort(sortStudent);
		for (Document document : resultado) {
			System.out.println(document);
		}
		
	}

}
