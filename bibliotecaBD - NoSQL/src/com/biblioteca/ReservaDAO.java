package com.biblioteca;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO {
    private final MongoDatabase db = MongoDatabaseManager.getDatabase();
    private final MongoCollection<Document> collection = db.getCollection("reserva");
    private final LivroDAO livroDAO = new LivroDAO();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public void create(Reserva reserva) {
        int nextId = MongoDatabaseManager.getNextId("reserva");
        reserva.setId(nextId);

        Document doc = new Document("_id", nextId)
                .append("id_livro_fk", reserva.getLivro().getId())
                .append("id_usuario_fk", reserva.getUsuario().getId())
                .append("data_reserva", reserva.getDataReserva().toString());

        collection.insertOne(doc);
        System.out.println("Reserva realizada com sucesso!");
    }

    public Reserva read(int id) {
        Document doc = collection.find(Filters.eq("_id", id)).first();
        if (doc != null) {
            Livro livro = livroDAO.read(doc.getInteger("id_livro_fk"));
            Usuario usuario = usuarioDAO.read(doc.getInteger("id_usuario_fk"));
            return new Reserva(
                    doc.getInteger("_id"),
                    livro,
                    usuario,
                    Date.valueOf(doc.getString("data_reserva"))
            );
        }
        return null;
    }

    public List<Reserva> readAll() {
        List<Reserva> reservas = new ArrayList<>();
        for (Document doc : collection.find()) {
            Livro livro = livroDAO.read(doc.getInteger("id_livro_fk"));
            Usuario usuario = usuarioDAO.read(doc.getInteger("id_usuario_fk"));
            reservas.add(new Reserva(
                    doc.getInteger("_id"),
                    livro,
                    usuario,
                    Date.valueOf(doc.getString("data_reserva"))
            ));
        }
        return reservas;
    }

    public void delete(int id) {
        collection.deleteOne(Filters.eq("_id", id));
        System.out.println("Reserva removida.");
    }
}