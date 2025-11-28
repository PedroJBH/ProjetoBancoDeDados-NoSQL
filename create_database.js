//Seleciona (ou cria) o banco de dados
use biblioteca_db;

//Remove coleções antigas para evitar duplicidade
db.emprestimo.drop();
db.reserva.drop();
db.exemplar.drop();
db.livro_autor.drop();
db.livro.drop();
db.autor.drop();
db.usuario.drop();
db.editora.drop();
db.categoria.drop();

print("Coleções antigas removidas. Iniciando inserção de dados...");

//Inserção de Dados

// AUTORES 
db.autor.insertMany([
    { _id: 1, nome: "Stephen King" },
    { _id: 2, nome: "J.R.R. Tolkien" },
    { _id: 3, nome: "Frank Herbert" },
    { _id: 4, nome: "Charles Duhigg" }
]);

// EDITORAS
db.editora.insertMany([
    { _id: 1, nome: "Viking Press" },
    { _id: 2, nome: "Allen & Unwin" },
    { _id: 3, nome: "Aleph" },
    { _id: 4, nome: "Objetiva" }
]);

// CATEGORIAS
db.categoria.insertMany([
    { _id: 1, nome: "Terror" },
    { _id: 2, nome: "Fantasia" },
    { _id: 3, nome: "Ficção Científica" },
    { _id: 4, nome: "Auto-ajuda" }
]);

// USUÁRIOS
db.usuario.insertMany([
    { _id: 1, nome: "João Silva", email: "joao@email.com", endereco: "Rua A, 123" },
    { _id: 2, nome: "Maria Souza", email: "maria@email.com", endereco: "Av B, 456" },
    { _id: 3, nome: "Pedro Santos", email: "pedro@email.com", endereco: "Rua C, 789" },
    { _id: 4, nome: "Ana Pereira", email: "ana@email.com", endereco: "Travessa D, 101" }
]);

// LIVROS (Títulos)
db.livro.insertMany([
    { _id: 1, titulo: "IT: A coisa", ano_publicacao: 1986, id_editora_fk: 1, id_categoria_fk: 1 },
    { _id: 2, titulo: "O Senhor dos Anéis", ano_publicacao: 1954, id_editora_fk: 2, id_categoria_fk: 2 },
    { _id: 3, titulo: "Duna", ano_publicacao: 1965, id_editora_fk: 3, id_categoria_fk: 3 },
    { _id: 4, titulo: "Poder do Hábito", ano_publicacao: 2012, id_editora_fk: 4, id_categoria_fk: 4 },
    { _id: 5, titulo: "O Hobbit", ano_publicacao: 1937, id_editora_fk: 2, id_categoria_fk: 2 }
]);

// LIVRO_AUTOR (Relacionamento N:M)
db.livro_autor.insertMany([
    { id_livro_fk: 1, id_autor_fk: 1 }, // IT - King
    { id_livro_fk: 2, id_autor_fk: 2 }, // SdA - Tolkien
    { id_livro_fk: 3, id_autor_fk: 3 }, // Duna - Herbert
    { id_livro_fk: 4, id_autor_fk: 4 }, // Hábito - Duhigg
    { id_livro_fk: 5, id_autor_fk: 2 }  // Hobbit - Tolkien
]);

// EXEMPLARES (Cópias Físicas)
db.exemplar.insertMany([
    { _id: 1, id_livro_fk: 1, status: "Disponível" },
    { _id: 2, id_livro_fk: 1, status: "Disponível" },
    { _id: 3, id_livro_fk: 2, status: "Disponível" },
    { _id: 4, id_livro_fk: 2, status: "Disponível" },
    { _id: 5, id_livro_fk: 2, status: "Emprestado" },
    { _id: 6, id_livro_fk: 3, status: "Disponível" },
    { _id: 7, id_livro_fk: 4, status: "Disponível" },
    { _id: 8, id_livro_fk: 5, status: "Disponível" }
]);

// EMPRÉSTIMOS E RESERVAS
var hoje = new Date().toISOString().slice(0, 10);
var passado = "2023-10-01";
var futuro = "2023-10-15"; 

db.emprestimo.insertMany([
    { 
        _id: 1, 
        id_exemplar_fk: 1, 
        id_usuario_fk: 1, 
        data_emprestimo: passado, 
        data_prevista_devolucao: futuro, 
        data_devolucao: null 
    },
    { 
        _id: 2, 
        id_exemplar_fk: 5, 
        id_usuario_fk: 2, 
        data_emprestimo: passado, 
        data_prevista_devolucao: passado, // Atrasado
        data_devolucao: null 
    }
]);

db.reserva.insertOne({
    _id: 1,
    id_livro_fk: 3,
    id_usuario_fk: 3,
    data_reserva: hoje
});

print("Banco de dados 'biblioteca_db' criado e populado com sucesso!");
