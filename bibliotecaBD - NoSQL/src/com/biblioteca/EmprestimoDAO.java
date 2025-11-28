package com.biblioteca;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmprestimoDAO {
    private final MongoDatabase db = MongoDatabaseManager.getDatabase();
    private final MongoCollection<Document> collection = db.getCollection("emprestimo");

    private final ExemplarDAO exemplarDAO = new ExemplarDAO();
    private final LivroDAO livroDAO = new LivroDAO();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final ReservaDAO reservaDAO = new ReservaDAO();

    public void realizarEmprestimo(int idLivro, int idUsuario) {
        Exemplar exemplarDisponivel = exemplarDAO.buscarExemplarDisponivel(idLivro);

        if (exemplarDisponivel != null) {
            exemplarDisponivel.setStatus("Emprestado");
            exemplarDAO.update(exemplarDisponivel);

            int nextId = MongoDatabaseManager.getNextId("emprestimo");
            Document doc = new Document("_id", nextId)
                    .append("id_exemplar_fk", exemplarDisponivel.getId())
                    .append("id_usuario_fk", idUsuario)
                    .append("data_emprestimo", LocalDate.now().toString())
                    .append("data_prevista_devolucao", LocalDate.now().plusDays(15).toString())
                    .append("data_devolucao", null);

            collection.insertOne(doc);
            System.out.println("Empréstimo realizado. Exemplar ID: " + exemplarDisponivel.getId());
        } else {
            System.out.println("Sem exemplares. Deseja reservar? (s/n)");
            Scanner scanner = new Scanner(System.in);
            if (scanner.nextLine().equalsIgnoreCase("s")) {
                Livro livro = livroDAO.read(idLivro);
                Usuario usuario = usuarioDAO.read(idUsuario);
                if (livro != null && usuario != null) {
                    reservaDAO.create(new Reserva(0, livro, usuario, Date.valueOf(LocalDate.now())));
                }
            }
        }
    }

    public void devolverLivro(int idEmprestimo) {
        Document emprestimo = collection.find(Filters.eq("_id", idEmprestimo)).first();
        if (emprestimo != null) {
            int idExemplar = emprestimo.getInteger("id_exemplar_fk");
            Exemplar ex = exemplarDAO.read(idExemplar);
            if (ex != null) {
                ex.setStatus("Disponível");
                exemplarDAO.update(ex);
            }

            collection.updateOne(Filters.eq("_id", idEmprestimo),
                    Updates.set("data_devolucao", LocalDate.now().toString()));
            System.out.println("Livro devolvido com sucesso.");
        } else {
            System.out.println("Empréstimo não encontrado.");
        }
    }

    public void renovarEmprestimo(int idEmprestimo) {
        Document emprestimo = collection.find(Filters.and(
                Filters.eq("_id", idEmprestimo),
                Filters.eq("data_devolucao", null)
        )).first();

        if (emprestimo != null) {
            String dataPrevistaStr = emprestimo.getString("data_prevista_devolucao");
            LocalDate dataPrevista = LocalDate.parse(dataPrevistaStr);

            int idExemplar = emprestimo.getInteger("id_exemplar_fk");
            int idLivro = exemplarDAO.read(idExemplar).getLivro().getId();
            MongoCollection<Document> reservasColl = db.getCollection("reserva");
            long count = reservasColl.countDocuments(Filters.eq("id_livro_fk", idLivro));

            if (count > 0) {
                System.out.println("Não é possível renovar. Existem reservas para este livro.");
            } else {
                LocalDate novaData = dataPrevista.plusDays(15);
                collection.updateOne(Filters.eq("_id", idEmprestimo),
                        Updates.set("data_prevista_devolucao", novaData.toString()));
                System.out.println("Renovado! Nova data: " + novaData);
            }
        } else {
            System.out.println("Empréstimo não encontrado ou já devolvido.");
        }
    }

    public List<Emprestimo> relatorioEmprestimosAtivos() {
        List<Emprestimo> lista = new ArrayList<>();
        for (Document doc : collection.find(Filters.eq("data_devolucao", null))) {
            lista.add(new Emprestimo(
                    doc.getInteger("_id"),
                    doc.getInteger("id_exemplar_fk"),
                    doc.getInteger("id_usuario_fk"),
                    Date.valueOf(doc.getString("data_emprestimo")),
                    Date.valueOf(doc.getString("data_prevista_devolucao")),
                    null
            ));
        }
        return lista;
    }

    public List<Usuario> relatorioUsuariosComAtraso() {
        List<Usuario> atrasados = new ArrayList<>();
        String hoje = LocalDate.now().toString();

        for (Document doc : collection.find(Filters.and(
                Filters.eq("data_devolucao", null),
                Filters.lt("data_prevista_devolucao", hoje)
        ))) {
            Usuario u = usuarioDAO.read(doc.getInteger("id_usuario_fk"));
            boolean jaExiste = false;
            for(Usuario user : atrasados) if(user.getId() == u.getId()) jaExiste = true;

            if(!jaExiste) atrasados.add(u);
        }
        return atrasados;
    }
}