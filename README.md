### **INSTRUÇÕES DE COMPILAÇÃO E EXECUÇÃO — PROJETO BIBLIOTECA (Fase 2 - MongoDB)**

Este documento detalha os pré-requisitos e os passos necessários para compilar e executar a aplicação de gerenciamento da biblioteca (versão NoSQL com MongoDB) a partir do código-fonte.

#### **1. Pré-Requisitos**

Antes de iniciar, certifique-se de que os seguintes componentes estão instalados e configurados em seu sistema:

  * **Java Development Kit (JDK) - Versão 17 ou superior:**

      * **Como verificar se está instalado?** Abra um terminal ou prompt de comando e digite:
        ```sh
        java -version
        ```
      * Se o comando não for reconhecido ou a versão for inferior à 17, você precisará instalar ou atualizar o JDK.

  * **MongoDB Server (Community Edition):**
      * **Como verificar?** Garanta que o serviço do MongoDB esteja rodando (geralmente na porta `27017`). Você pode testar conectando-se via MongoDB Compass.

  * **Drivers do MongoDB para Java:**
      A pasta `lib` deve conter os arquivos `.jar` necessários para a conexão. Diferente do SQLite, o MongoDB requer três arquivos principais (ou um "uber-jar"):
      * `mongodb-driver-sync-4.11.1.jar`
      * `mongodb-driver-core-4.11.1.jar`
      * `bson-4.11.1.jar`
      * **Onde obter?** Repositório Maven Central.

#### **2. Estrutura de Arquivos**

Para que os comandos funcionem corretamente, organize seus arquivos da seguinte forma. Verifique se as subpastas estão organizadas como abaixo:

````

bibliotecaNoSQL/
|
├── create\_database.js            \<-- Script de criação/população do banco
|
├── lib/
|   ├── mongodb-driver-sync-*.jar \<-- Drivers do MongoDB
|   ├── mongodb-driver-core-*.jar
|   └── bson-\*.jar
|
├── src/                          \<-- Coloque seus arquivos .java aqui
|   └── com/
|       └── biblioteca/
|           ├── Main.java
|           ├── MongoDatabaseManager.java
|           ├── Livro.java
|           ├── ... (outras classes e DAOs)
|           └── ReservaDAO.java
|
└── (a pasta 'out' ou 'bin' será criada automaticamente aqui após compilar)

````

#### **3. Passos para Compilação (Via Terminal)**

A compilação transformará seus arquivos de código-fonte (`.java`) em bytecode executável (`.class`).

1.  **Abra o terminal** ou prompt de comando.

2.  **Navegue até a pasta raiz do seu projeto**.

    ```sh
    cd caminho/para/bibliotecaNoSQL
    ```

3.  **Execute o comando de compilação.** Este comando cria os arquivos `.class` em uma nova pasta `bin`, usando os drivers que estão na pasta `lib`.

      * **No Windows:**
        ```cmd
        javac -d bin -cp "lib\*" src\com\biblioteca\*.java
        ```
      * **No Linux ou macOS:**
        ```sh
        javac -d bin -cp "lib/*" src/com/biblioteca/*.java
        ```
    > **Nota:** O uso de `lib\*` ou `lib/*` inclui automaticamente todos os JARs da pasta.

#### **4. Passos para Execução (Via Terminal)**

Agora que o projeto está compilado, você pode executá-lo.

1.  **No mesmo terminal**, na pasta raiz do projeto, execute o seguinte comando. Ele instrui o Java a rodar a classe `Main`, incluindo as bibliotecas no caminho (classpath).

      * **No Windows:**
        ```cmd
        java -cp "bin;lib\*" com.biblioteca.Main
        ```
      * **No Linux ou macOS:**
        ```sh
        java -cp "bin:lib/*" com.biblioteca.Main
        ```
    > **Atenção:** No Windows usa-se ponto-e-vírgula `;` e no Linux/Mac usa-se dois-pontos `:` para separar caminhos.

#### **5. O Que Esperar**

Se tudo ocorreu bem, você verá o seguinte no terminal:

1.  **Mensagens de conexão e reinicialização do banco:**
    ```
    Conexão com MongoDB estabelecida.
    Reiniciando banco de dados (Drop & Seed)...
    Banco de dados MongoDB inicializado com sucesso.
    ```
2.  **O menu principal da aplicação:**
    ```
    --- MENU PRINCIPAL ---
    1. Operações CRUD
    2. Processos de Negócio
    3. Relatórios do Sistema
    0. Sair
    Escolha uma opção:
    ```



**EXECUTANDO O PROJETO BIBLIOTECA NO INTELLIJ IDEA**

#### **1. Pré-Requisitos**

1.  **IntelliJ IDEA:** A IDE instalada.
2.  **JDK 17+:** Configurado na IDE.
3.  **MongoDB:** O serviço do banco de dados deve estar rodando.

#### **2. Passo a Passo no IntelliJ IDEA**

**Passo 1: Abrir o Projeto**

1.  Abra o IntelliJ IDEA e clique em **"Open"**.
2.  Selecione a pasta raiz do projeto (`bibliotecaNoSQL`) e clique em **"OK"**.

**Passo 2: Adicionar os Drivers do MongoDB**

O IntelliJ precisa reconhecer as bibliotecas externas.

1.  Vá em `File` > `Project Structure...` > `Libraries`.
2.  Clique no ícone **`+`** (New Project Library).
3.  Escolha **"From Maven..."** para baixar automaticamente.
4.  Digite na busca: `org.mongodb:mongodb-driver-sync:4.11.1`
5.  Marque a opção **"Transitive Dependencies"** (Importante!).
6.  Clique em **"OK"**.
    * *Alternativamente, se já tiver os arquivos `.jar` na pasta `lib`, escolha "Java" e selecione a pasta `lib`.*

**Passo 3: Executar a Aplicação**

1.  No painel do projeto (lado esquerdo), navegue até `src/com/biblioteca/Main.java`.
2.  Clique com o botão direito no arquivo `Main.java`.
3.  Selecione a opção **"Run 'Main.main()'"** (ícone verde de Play).

#### **3. Criação Manual do Banco (Opcional)**

A aplicação recria o banco automaticamente ao iniciar. Porém, se precisar rodar o script manualmente para testes externos:

1.  Localize o arquivo `create_database.js` na raiz do projeto.
2.  Abra o **MongoDB Compass** ou terminal.
3.  Execute o script ou copie/cole seu conteúdo no console do Mongo.

#### **4. Conclusão**

  * A janela **"Run"** do IntelliJ abrirá na parte inferior.
  * Você verá as mensagens de "Conexão estabelecida" seguidas pelo menu interativo.
  * Os dados serão persistidos no seu banco de dados MongoDB local.
```` 
