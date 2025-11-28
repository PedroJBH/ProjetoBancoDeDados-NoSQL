package com.biblioteca;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private final MongoDatabase db = MongoDatabaseManager.getDatabase();
    private final MongoCollection<Document> collection = db.getCollection("usuario");

    public void create(Usuario usuario) {
        int nextId = MongoDatabaseManager.getNextId("usuario");
        usuario.setId(nextId);

        Document doc = new Document("_id", usuario.getId())
                .append("nome", usuario.getNome())
                .append("email", usuario.getEmail())
                .append("endereco", usuario.getEndereco());

        collection.insertOne(doc);
        System.out.println("Usuário cadastrado com sucesso.");
    }

    public Usuario read(int id) {
        Document doc = collection.find(Filters.eq("_id", id)).first();
        if (doc != null) {
            return new Usuario(
                    doc.getInteger("_id"),
                    doc.getString("nome"),
                    doc.getString("email"),
                    doc.getString("endereco")
            );
        }
        return null;
    }

    public List<Usuario> readAll() {
        List<Usuario> usuarios = new ArrayList<>();
        for (Document doc : collection.find()) {
            usuarios.add(new Usuario(
                    doc.getInteger("_id"),
                    doc.getString("nome"),
                    doc.getString("email"),
                    doc.getString("endereco")
            ));
        }
        return usuarios;
    }

    public void update(Usuario usuario) {
        collection.updateOne(Filters.eq("_id", usuario.getId()),
                Updates.combine(
                        Updates.set("nome", usuario.getNome()),
                        Updates.set("email", usuario.getEmail()),
                        Updates.set("endereco", usuario.getEndereco())
                ));
        System.out.println("Usuário atualizado com sucesso.");
    }

    public void delete(int id) {
        collection.deleteOne(Filters.eq("_id", id));
        System.out.println("Usuário removido com sucesso.");
    }
}