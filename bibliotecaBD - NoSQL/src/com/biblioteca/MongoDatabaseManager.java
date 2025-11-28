package com.biblioteca;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.Document;

import java.time.LocalDate;
import java.util.Arrays;

public class MongoDatabaseManager {
    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    private static final String DB_NAME = "biblioteca_db";

    private static MongoClient mongoClient;
    private static MongoDatabase database;

    public static MongoDatabase getDatabase() {
        if (database == null) {
            try {
                mongoClient = MongoClients.create(CONNECTION_STRING);
                database = mongoClient.getDatabase(DB_NAME);
                System.out.println("Conexão com MongoDB estabelecida.");
            } catch (Exception e) {
                System.err.println("Erro ao conectar no MongoDB: " + e.getMessage());
                throw e;
            }
        }
        return database;
    }

    public static void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }

    public static int getNextId(String collectionName) {
        MongoCollection<Document> collection = getDatabase().getCollection(collectionName);
        Document doc = collection.find().sort(Sorts.descending("_id")).first();
        if (doc != null) {
            return doc.getInteger("_id") + 1;
        }
        return 1;
    }

    public static void initializeDatabase() {
        MongoDatabase db = getDatabase();

        System.out.println("Reiniciando banco de dados (Drop & Seed)...");
        db.getCollection("emprestimo").drop();
        db.getCollection("reserva").drop();
        db.getCollection("exemplar").drop();
        db.getCollection("livro_autor").drop();
        db.getCollection("livro").drop();
        db.getCollection("autor").drop();
        db.getCollection("usuario").drop();
        db.getCollection("editora").drop();
        db.getCollection("categoria").drop();

        insertInitialData(db);
        System.out.println("Banco de dados MongoDB inicializado com sucesso.");
    }

    private static void insertInitialData(MongoDatabase db) {
        MongoCollection<Document> autores = db.getCollection("autor");
        autores.insertMany(Arrays.asList(
                new Document("_id", 1).append("nome", "Stephen King"),
                new Document("_id", 2).append("nome", "J.R.R. Tolkien"),
                new Document("_id", 3).append("nome", "Frank Herbert"),
                new Document("_id", 4).append("nome", "Charles Duhigg")
        ));

        MongoCollection<Document> editoras = db.getCollection("editora");
        editoras.insertMany(Arrays.asList(
                new Document("_id", 1).append("nome", "Viking Press"),
                new Document("_id", 2).append("nome", "Allen & Unwin"),
                new Document("_id", 3).append("nome", "Aleph"),
                new Document("_id", 4).append("nome", "Objetiva")
        ));

        MongoCollection<Document> categorias = db.getCollection("categoria");
        categorias.insertMany(Arrays.asList(
                new Document("_id", 1).append("nome", "Terror"),
                new Document("_id", 2).append("nome", "Fantasia"),
                new Document("_id", 3).append("nome", "Ficção Científica"),
                new Document("_id", 4).append("nome", "Auto-ajuda")
        ));

        MongoCollection<Document> usuarios = db.getCollection("usuario");
        usuarios.insertMany(Arrays.asList(
                new Document("_id", 1).append("nome", "João Silva").append("email", "joao@email.com").append("endereco", "Rua A"),
                new Document("_id", 2).append("nome", "Maria Souza").append("email", "maria@email.com").append("endereco", "Av B"),
                new Document("_id", 3).append("nome", "Pedro Santos").append("email", "pedro@email.com").append("endereco", "Rua C"),
                new Document("_id", 4).append("nome", "Ana Pereira").append("email", "ana@email.com").append("endereco", "Travessa D")
        ));

        MongoCollection<Document> livros = db.getCollection("livro");
        livros.insertMany(Arrays.asList(
                new Document("_id", 1).append("titulo", "IT: A coisa").append("ano_publicacao", 1986).append("id_editora_fk", 1).append("id_categoria_fk", 1),
                new Document("_id", 2).append("titulo", "O Senhor dos Anéis").append("ano_publicacao", 1954).append("id_editora_fk", 2).append("id_categoria_fk", 2),
                new Document("_id", 3).append("titulo", "Duna").append("ano_publicacao", 1965).append("id_editora_fk", 3).append("id_categoria_fk", 3),
                new Document("_id", 4).append("titulo", "Poder do Hábito").append("ano_publicacao", 2012).append("id_editora_fk", 4).append("id_categoria_fk", 4),
                new Document("_id", 5).append("titulo", "O Hobbit").append("ano_publicacao", 1937).append("id_editora_fk", 2).append("id_categoria_fk", 2)
        ));

        MongoCollection<Document> livroAutor = db.getCollection("livro_autor");
        livroAutor.insertMany(Arrays.asList(
                new Document("id_livro_fk", 1).append("id_autor_fk", 1),
                new Document("id_livro_fk", 2).append("id_autor_fk", 2),
                new Document("id_livro_fk", 3).append("id_autor_fk", 3),
                new Document("id_livro_fk", 4).append("id_autor_fk", 4),
                new Document("id_livro_fk", 5).append("id_autor_fk", 2)
        ));

        MongoCollection<Document> exemplares = db.getCollection("exemplar");
        exemplares.insertMany(Arrays.asList(
                new Document("_id", 1).append("id_livro_fk", 1).append("status", "Disponível"),
                new Document("_id", 2).append("id_livro_fk", 1).append("status", "Disponível"),
                new Document("_id", 3).append("id_livro_fk", 2).append("status", "Disponível"),
                new Document("_id", 4).append("id_livro_fk", 2).append("status", "Disponível"),
                new Document("_id", 5).append("id_livro_fk", 2).append("status", "Emprestado"),
                new Document("_id", 6).append("id_livro_fk", 3).append("status", "Disponível"),
                new Document("_id", 7).append("id_livro_fk", 4).append("status", "Disponível"),
                new Document("_id", 8).append("id_livro_fk", 5).append("status", "Disponível")
        ));

        String today = LocalDate.now().toString();
        String pastDate = LocalDate.now().minusDays(25).toString();
        String futureDate = LocalDate.now().plusDays(5).toString();
        String pastDue = LocalDate.now().minusDays(10).toString();

        MongoCollection<Document> emprestimos = db.getCollection("emprestimo");
        emprestimos.insertMany(Arrays.asList(
                new Document("_id", 1).append("id_exemplar_fk", 1).append("id_usuario_fk", 1)
                        .append("data_emprestimo", pastDate).append("data_prevista_devolucao", futureDate).append("data_devolucao", null),

                new Document("_id", 2).append("id_exemplar_fk", 5).append("id_usuario_fk", 2)
                        .append("data_emprestimo", pastDate).append("data_prevista_devolucao", pastDue).append("data_devolucao", null)
        ));

        MongoCollection<Document> reservas = db.getCollection("reserva");
        reservas.insertOne(
                new Document("_id", 1).append("id_livro_fk", 3).append("id_usuario_fk", 3).append("data_reserva", today)
        );
    }
}