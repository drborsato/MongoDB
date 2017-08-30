package course.week02.homework04;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class App {

	public static void main(String[] args) {
		String mongoURI = "mongodb://localhost";
		MongoClient client = new MongoClient(new MongoClientURI(mongoURI));
        MongoDatabase database = client.getDatabase("students");
        MongoCollection<Document> collection = database.getCollection("grades");
        
        FindIterable<Document> resultado;
        Bson sortStudent = new Document("student_id",-1).append("score", 1);
        Bson filtro = new Document("type","homework");
        
        resultado = collection.find(filtro).sort(sortStudent);
        
        int studentIdAnterior = 1000;
        int studentId;
        int count=0;
        for (Document document : resultado) {

            studentId = document.getInteger("student_id");
            
            if(studentId != studentIdAnterior) {
                collection.deleteOne(Filters.eq("_id", document.get("_id")));
                count++;
            }
            
            studentIdAnterior = studentId;
        }
        System.out.println(count);
        
        resultado = collection.find();
        count=0;
        for (Document document : resultado) {
            count++;
        }
        System.out.println(count);

	}

}
