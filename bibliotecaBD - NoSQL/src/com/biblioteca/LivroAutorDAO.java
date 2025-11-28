package com.biblioteca;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

public class LivroAutorDAO {
    private final MongoDatabase db = MongoDatabaseManager.getDatabase();
    private final MongoCollection<Document> collection = db.getCollection("livro_autor");

    public boolean adicionarAutorALivro(int idLivro, int idAutor) {
        Document exists = collection.find(Filters.and(
                Filters.eq("id_livro_fk", idLivro),
                Filters.eq("id_autor_fk", idAutor)
        )).first();

        if (exists != null) {
            System.out.println("Aviso: Esta associação já existe.");
            return false;
        }

        Document doc = new Document("id_livro_fk", idLivro)
                .append("id_autor_fk", idAutor);

        collection.insertOne(doc);
        System.out.println("Autor associado ao livro com sucesso.");
        return true;
    }
}