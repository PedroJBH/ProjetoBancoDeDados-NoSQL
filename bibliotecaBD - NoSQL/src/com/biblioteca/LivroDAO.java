package com.biblioteca;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class LivroDAO {
    private final MongoDatabase db = MongoDatabaseManager.getDatabase();
    private final MongoCollection<Document> collection = db.getCollection("livro");
    private final EditoraDAO editoraDAO = new EditoraDAO();
    private final CategoriaDAO categoriaDAO = new CategoriaDAO();

    private List<Autor> loadAutores(int idLivro) {
        List<Autor> autores = new ArrayList<>();
        MongoCollection<Document> linkCollection = db.getCollection("livro_autor");
        MongoCollection<Document> autorCollection = db.getCollection("autor");

        for (Document link : linkCollection.find(Filters.eq("id_livro_fk", idLivro))) {
            int idAutor = link.getInteger("id_autor_fk");
            Document autorDoc = autorCollection.find(Filters.eq("_id", idAutor)).first();
            if (autorDoc != null) {
                autores.add(new Autor(autorDoc.getInteger("_id"), autorDoc.getString("nome")));
            }
        }
        return autores;
    }

    public int create(Livro livro) {
        int nextId = MongoDatabaseManager.getNextId("livro");
        livro.setId(nextId);

        Document doc = new Document("_id", nextId)
                .append("titulo", livro.getTitulo())
                .append("ano_publicacao", livro.getAnoPublicacao())
                .append("id_editora_fk", livro.getEditora().getId())
                .append("id_categoria_fk", livro.getCategoria().getId());

        collection.insertOne(doc);
        System.out.println("Livro cadastrado com sucesso.");
        return nextId;
    }

    public Livro read(int id) {
        Document doc = collection.find(Filters.eq("_id", id)).first();
        if (doc != null) {
            Editora ed = editoraDAO.read(doc.getInteger("id_editora_fk"));
            Categoria cat = categoriaDAO.read(doc.getInteger("id_categoria_fk"));

            Livro livro = new Livro(
                    doc.getInteger("_id"),
                    doc.getString("titulo"),
                    doc.getInteger("ano_publicacao"),
                    ed, cat
            );
            livro.setAutores(loadAutores(id));
            return livro;
        }
        return null;
    }

    public List<Livro> readAll() {
        List<Livro> livros = new ArrayList<>();
        for (Document doc : collection.find()) {
            Editora ed = editoraDAO.read(doc.getInteger("id_editora_fk"));
            Categoria cat = categoriaDAO.read(doc.getInteger("id_categoria_fk"));

            Livro livro = new Livro(
                    doc.getInteger("_id"),
                    doc.getString("titulo"),
                    doc.getInteger("ano_publicacao"),
                    ed, cat
            );
            livro.setAutores(loadAutores(livro.getId()));
            livros.add(livro);
        }
        return livros;
    }

    public void update(Livro livro) {
        collection.updateOne(Filters.eq("_id", livro.getId()),
                Updates.combine(
                        Updates.set("titulo", livro.getTitulo()),
                        Updates.set("ano_publicacao", livro.getAnoPublicacao()),
                        Updates.set("id_editora_fk", livro.getEditora().getId()),
                        Updates.set("id_categoria_fk", livro.getCategoria().getId())
                ));
        System.out.println("Livro atualizado com sucesso.");
    }

    public void delete(int id) {
        collection.deleteOne(Filters.eq("_id", id));
        System.out.println("Livro removido com sucesso.");
    }
}