package com.biblioteca;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {
    private final MongoDatabase db = MongoDatabaseManager.getDatabase();
    private final MongoCollection<Document> collection = db.getCollection("categoria");

    public void create(Categoria categoria) {
        int nextId = MongoDatabaseManager.getNextId("categoria");
        categoria.setId(nextId);

        Document doc = new Document("_id", categoria.getId())
                .append("nome", categoria.getNome());

        collection.insertOne(doc);
        System.out.println("Categoria cadastrada com sucesso.");
    }

    public Categoria read(int id) {
        Document doc = collection.find(Filters.eq("_id", id)).first();
        if (doc != null) {
            return new Categoria(doc.getInteger("_id"), doc.getString("nome"));
        }
        return null;
    }

    public List<Categoria> readAll() {
        List<Categoria> categorias = new ArrayList<>();
        for (Document doc : collection.find()) {
            categorias.add(new Categoria(doc.getInteger("_id"), doc.getString("nome")));
        }
        return categorias;
    }

    public void update(Categoria categoria) {
        collection.updateOne(Filters.eq("_id", categoria.getId()),
                Updates.set("nome", categoria.getNome()));
        System.out.println("Categoria atualizada com sucesso.");
    }

    public void delete(int id) {
        collection.deleteOne(Filters.eq("_id", id));
        System.out.println("Categoria removida com sucesso.");
    }
}