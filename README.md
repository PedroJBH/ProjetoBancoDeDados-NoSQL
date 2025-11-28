INSTRUÇÕES DE COMPILAÇÃO E EXECUÇÃO — PROJETO BIBLIOTECA (Fase 2 - MongoDB)Este documento detalha os pré-requisitos e os passos necessários para compilar e executar a aplicação de gerenciamento da biblioteca (versão NoSQL com MongoDB) a partir do código-fonte.1. Pré-RequisitosAntes de iniciar, certifique-se de que os seguintes componentes estão instalados e configurados em seu sistema:Java Development Kit (JDK) - Versão 17 ou superior:Como verificar se está instalado? Abra um terminal ou prompt de comando e digite:java -version
Se o comando não for reconhecido ou a versão for inferior à 17, você precisará instalar ou atualizar o JDK.MongoDB Server (Community Edition):O que é? O banco de dados NoSQL onde os dados serão armazenados.Como verificar? Garanta que o serviço do MongoDB esteja rodando (geralmente na porta 27017). Você pode testar conectando-se via MongoDB Compass.Drivers do MongoDB para Java:A pasta lib deve conter os arquivos .jar necessários para a conexão. Diferente do SQLite, o MongoDB requer três arquivos principais (ou um "uber-jar"):mongodb-driver-sync-4.11.1.jarmongodb-driver-core-4.11.1.jarbson-4.11.1.jarOnde obter? Repositório Maven Central.2. Estrutura de ArquivosPara que os comandos funcionem corretamente, organize seus arquivos da seguinte forma. Verifique se as subpastas estão organizadas como abaixo:bibliotecaNoSQL/
|
├── create_database.js            <-- Script de criação/população do banco
|
├── lib/
|   ├── mongodb-driver-sync-*.jar <-- Drivers do MongoDB
|   ├── mongodb-driver-core-*.jar
|   └── bson-*.jar
|
├── src/                          <-- Coloque seus arquivos .java aqui
|   └── com/
|       └── biblioteca/
|           ├── Main.java
|           ├── MongoDatabaseManager.java
|           ├── Livro.java
|           ├── ... (outras classes e DAOs)
|           └── ReservaDAO.java
|
└── (a pasta 'out' ou 'bin' será criada automaticamente aqui após compilar)
3. Passos para Compilação (Via Terminal)A compilação transformará seus arquivos de código-fonte (.java) em bytecode executável (.class).Abra o terminal ou prompt de comando.Navegue até a pasta raiz do seu projeto.cd caminho/para/bibliotecaNoSQL
Execute o comando de compilação. Este comando cria os arquivos .class em uma nova pasta bin, usando os drivers que estão na pasta lib.No Windows:javac -d bin -cp "lib\*" src\com\biblioteca\*.java
No Linux ou macOS:javac -d bin -cp "lib/*" src/com/biblioteca/*.java
Nota: O uso de lib\* ou lib/* inclui automaticamente todos os JARs da pasta.4. Passos para Execução (Via Terminal)Agora que o projeto está compilado, você pode executá-lo.No mesmo terminal, na pasta raiz do projeto, execute o seguinte comando. Ele instrui o Java a rodar a classe Main, incluindo as bibliotecas no caminho (classpath).No Windows:java -cp "bin;lib\*" com.biblioteca.Main
No Linux ou macOS:java -cp "bin:lib/*" com.biblioteca.Main
(Atenção: No Windows usa-se ponto-e-vírgula ; e no Linux/Mac usa-se dois-pontos : para separar caminhos).5. O Que EsperarSe tudo ocorreu bem, você verá o seguinte no terminal:Mensagens de conexão e reinicialização do banco:Conexão com MongoDB estabelecida.
Reiniciando banco de dados (Drop & Seed)...
Banco de dados MongoDB inicializado com sucesso.
O menu principal da aplicação:--- MENU PRINCIPAL ---
1. Operações CRUD
2. Processos de Negócio
3. Relatórios do Sistema
0. Sair
Escolha uma opção:
EXECUTANDO O PROJETO BIBLIOTECA NO INTELLIJ IDEA1. Pré-RequisitosIntelliJ IDEA: A IDE instalada.JDK 17+: Configurado na IDE.MongoDB: O serviço do banco de dados deve estar rodando.2. Passo a Passo no IntelliJ IDEAPasso 1: Abrir o ProjetoAbra o IntelliJ IDEA e clique em "Open".Selecione a pasta raiz do projeto (bibliotecaNoSQL) e clique em "OK".Passo 2: Adicionar os Drivers do MongoDBO IntelliJ precisa reconhecer as bibliotecas externas.Vá em File > Project Structure... > Libraries.Clique no ícone + (New Project Library).Escolha "From Maven..." para baixar automaticamente.Digite na busca: org.mongodb:mongodb-driver-sync:4.11.1Marque a opção "Transitive Dependencies" (Importante!).Clique em OK.Alternativamente, se já tiver os arquivos .jar na pasta lib, escolha "Java" e selecione a pasta lib.Passo 3: Executar a AplicaçãoNo painel do projeto (lado esquerdo), navegue até src/com/biblioteca/Main.java.Clique com o botão direito no arquivo Main.java.Selecione a opção "Run 'Main.main()'" (ícone verde de Play).3. Criação Manual do Banco (Opcional)A aplicação recria o banco automaticamente ao iniciar. Porém, se precisar rodar o script manualmente para testes externos:Localize o arquivo create_database.js na raiz do projeto.Abra o MongoDB Compass ou terminal.Execute o script ou copie/cole seu conteúdo no console do Mongo.4. ConclusãoA janela "Run" do IntelliJ abrirá na parte inferior.Você verá as mensagens de "Conexão estabelecida" seguidas pelo menu interativo.Os dados serão persistidos no seu banco de dados MongoDB local.
