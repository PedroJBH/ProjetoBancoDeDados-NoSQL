package com.biblioteca;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class EditoraDAO {
    private final MongoDatabase db = MongoDatabaseManager.getDatabase();
    private final MongoCollection<Document> collection = db.getCollection("editora");

    // CREATE
    public void create(Editora editora) {
        int nextId = MongoDatabaseManager.getNextId("editora");
        editora.setId(nextId);

        Document doc = new Document("_id", editora.getId())
                .append("nome", editora.getNome());

        collection.insertOne(doc);
        System.out.println("Editora cadastrada com sucesso.");
    }

    // READ
    public Editora read(int id) {
        Document doc = collection.find(Filters.eq("_id", id)).first();
        if (doc != null) {
            return new Editora(doc.getInteger("_id"), doc.getString("nome"));
        }
        return null;
    }

    // READ ALL
    public List<Editora> readAll() {
        List<Editora> editoras = new ArrayList<>();
        for (Document doc : collection.find()) {
            editoras.add(new Editora(doc.getInteger("_id"), doc.getString("nome")));
        }
        return editoras;
    }

    // UPDATE
    public void update(Editora editora) {
        collection.updateOne(Filters.eq("_id", editora.getId()),
                Updates.set("nome", editora.getNome()));
        System.out.println("Editora atualizada com sucesso.");
    }

    // DELETE
    public void delete(int id) {
        collection.deleteOne(Filters.eq("_id", id));
        System.out.println("Editora removida com sucesso.");
    }
}