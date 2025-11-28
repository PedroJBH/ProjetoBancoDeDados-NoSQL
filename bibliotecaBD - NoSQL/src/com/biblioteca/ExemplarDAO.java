package com.biblioteca;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class ExemplarDAO {
    private final MongoDatabase db = MongoDatabaseManager.getDatabase();
    private final MongoCollection<Document> collection = db.getCollection("exemplar");
    private final LivroDAO livroDAO = new LivroDAO();

    public void create(Exemplar exemplar) {
        int nextId = MongoDatabaseManager.getNextId("exemplar");
        exemplar.setId(nextId);

        Document doc = new Document("_id", nextId)
                .append("id_livro_fk", exemplar.getLivro().getId())
                .append("status", exemplar.getStatus());

        collection.insertOne(doc);
        System.out.println("Exemplar cadastrado com sucesso.");
    }

    public Exemplar read(int id) {
        Document doc = collection.find(Filters.eq("_id", id)).first();
        if (doc != null) {
            Livro livro = livroDAO.read(doc.getInteger("id_livro_fk"));
            return new Exemplar(doc.getInteger("_id"), livro, doc.getString("status"));
        }
        return null;
    }

    public List<Exemplar> readAll() {
        List<Exemplar> exemplares = new ArrayList<>();
        for (Document doc : collection.find()) {
            Livro livro = livroDAO.read(doc.getInteger("id_livro_fk"));
            exemplares.add(new Exemplar(doc.getInteger("_id"), livro, doc.getString("status")));
        }
        return exemplares;
    }

    public void update(Exemplar exemplar) {
        collection.updateOne(Filters.eq("_id", exemplar.getId()),
                Updates.set("status", exemplar.getStatus()));
    }

    public void delete(int id) {
        collection.deleteOne(Filters.eq("_id", id));
        System.out.println("Exemplar removido.");
    }

    public Exemplar buscarExemplarDisponivel(int idLivro) {
        Document doc = collection.find(Filters.and(
                Filters.eq("id_livro_fk", idLivro),
                Filters.eq("status", "Disponível")
        )).first();

        if (doc != null) {
            Livro livro = livroDAO.read(doc.getInteger("id_livro_fk"));
            return new Exemplar(doc.getInteger("_id"), livro, doc.getString("status"));
        }
        return null;
    }
}