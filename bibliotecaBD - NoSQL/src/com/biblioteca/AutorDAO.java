package com.biblioteca;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class AutorDAO {
    private final MongoDatabase db = MongoDatabaseManager.getDatabase();
    private final MongoCollection<Document> collection = db.getCollection("autor");

    public void create(Autor autor) {
        int nextId = MongoDatabaseManager.getNextId("autor");
        autor.setId(nextId);

        Document doc = new Document("_id", autor.getId())
                .append("nome", autor.getNome());

        collection.insertOne(doc);
        System.out.println("Autor cadastrado com sucesso.");
    }

    public Autor read(int id) {
        Document doc = collection.find(Filters.eq("_id", id)).first();
        if (doc != null) {
            return new Autor(doc.getInteger("_id"), doc.getString("nome"));
        }
        return null;
    }

    public List<Autor> readAll() {
        List<Autor> autores = new ArrayList<>();
        for (Document doc : collection.find()) {
            autores.add(new Autor(doc.getInteger("_id"), doc.getString("nome")));
        }
        return autores;
    }

    public void update(Autor autor) {
        collection.updateOne(Filters.eq("_id", autor.getId()),
                Updates.set("nome", autor.getNome()));
        System.out.println("Autor atualizado com sucesso.");
    }

    public void delete(int id) {
        collection.deleteOne(Filters.eq("_id", id));
        System.out.println("Autor removido com sucesso.");
    }

    public List<Livro> relatorioLivrosPorAutor(int idAutor) {
        List<Livro> livros = new ArrayList<>();
        MongoCollection<Document> linkCollection = db.getCollection("livro_autor");

        LivroDAO livroDAO = new LivroDAO();

        for (Document link : linkCollection.find(Filters.eq("id_autor_fk", idAutor))) {
            int idLivro = link.getInteger("id_livro_fk");

            Livro livro = livroDAO.read(idLivro);
            if (livro != null) {
                livros.add(livro);
            }
        }
        return livros;
    }
}