# Sistema de Vendas – Spring Boot

Este projeto é um sistema backend feito com Spring Boot, uma ferramenta Java que ajuda a construir aplicações web de forma organizada e rápida.

Ele permite:

- Cadastrar produtos com nome, código, preço e estoque;
- Fazer vendas desses produtos, atualizando o estoque automaticamente;
- Evitar vendas quando não há estoque suficiente;
- Tratar erros, como dados inválidos, para evitar problemas;
- Testar tudo isso via requisições HTTP, que são como “pedidos” ao sistema.

### Como o sistema está organizado?

Arquitetura estruturada em camadas (Engenharia de Software, Sommerville)


- **Model (Modelo):** Representa as informações importantes, como Produto e Venda.
- **Repository (Repositório):** Guarda e recupera essas informações do banco de dados.
- **Service (Serviço):** Contém as regras do sistema, validação e lógica.
- **Controller (Controlador):** Recebe e responde os pedidos que chegam.
- **Exception (Exceções):** Trata os erros de forma amigável e clara.

### Papel dos Services no Sistema
Os Services possuem a responsabilidade central no sistema, pois contêm toda a lógica de negócio. Eles servem como uma camada intermediária entre o armazenamento dos dados (Repository) e a exposição das funcionalidades para o cliente (Controller).

Funções principais dos Services:
Validação: Os services verificam se os dados recebidos são válidos antes de prosseguir, por exemplo:

Produto com nome vazio ou código duplicado não pode ser salvo;

Venda com quantidade inválida ou estoque insuficiente é rejeitada.

Regras de negócio: Eles implementam as regras específicas do sistema, como descontar o estoque após a venda, calcular o valor total da venda, garantir integridade dos dados.

Lançamento de exceções: Quando ocorre uma situação que impede a continuidade da operação (ex: produto inválido, estoque insuficiente), os services lançam exceções personalizadas com mensagens claras que serão tratadas pelos controllers.

Transações: Operações que envolvem várias etapas críticas (ex: salvar venda e atualizar estoque) são marcadas com @Transactional, garantindo que tudo ocorra corretamente ou que nada seja alterado em caso de erro.

Acesso a dados via Repository: Os services não acessam diretamente o banco, mas usam os repositories para obter, salvar, atualizar e deletar registros.

Isolamento do Controller: Colocando a lógica no service, o controller fica focado apenas em comunicação HTTP e tratamento de exceções, facilitando manutenção e testes.

Especificamente no Projeto
ProdutoService: cuida de todas as operações relacionadas a produtos, como cadastro, atualização, exclusão e validações rigorosas para evitar dados incorretos.

VendaService: centraliza o processo de venda, validando se o produto existe, a quantidade é válida, se há estoque suficiente e depois cria o registro da venda e atualiza o estoque.


### Papel do Controller no Tratamento de Exceções
No sistema, o Controller é responsável por receber as requisições externas (do usuário, frontend ou de outra aplicação) e enviar as respostas apropriadas.

Quando o Controller chama os métodos do Service, pode acontecer de esses métodos lançarem exceções (usando throw) para indicar que algo está errado, como um produto inválido ou estoque insuficiente.

Para lidar com essas situações, o Controller envolve essas chamadas em blocos try-catch:

O código dentro do try é o que pode lançar uma exceção.

Se uma exceção for lançada, o fluxo é desviado para o bloco catch correspondente, que captura a exceção.

No catch, o Controller monta uma resposta HTTP clara e informativa para o cliente, com um código de erro e mensagem que explica o problema.

Dessa forma, o Controller garante que o usuário receba uma resposta amigável, mesmo quando o sistema detecta algum erro, o que melhora muito a experiência e a manutenção no projeto.

### Anotações (@Annotations)

São comandos especiais que indicam para o Spring Boot o papel das classes e métodos, facilitando o funcionamento do sistema.

## Sobre o Banco de Dados H2

- Banco de dados simples, que roda dentro do programa (não precisa instalar).
- Funciona na memória, então os dados desaparecem ao desligar o sistema.
- Pode ser acessado pelo navegador pelo endereço: `http://localhost:8080/h2-console`
- Lá você pode ver os dados e estrutura das tabelas criadas automaticamente.

---

## Passo a Passo para Usar o Projeto

### 1. Abrir o projeto na IDE

- Importe o projeto para uma IDE Java (IntelliJ, Eclipse, etc).
- Verifique se está usando JDK 17.
- Atualize as dependências do Maven.
- Recompile o projeto.

### 2. Rodar o sistema

- Rode a classe `SistemavendasApplication` na IDE, ou use no terminal:
mvn spring-boot:run

- Aguarde a mensagem que informa que o servidor iniciou (geralmente na porta 8080).

### 3. Usar o Console H2

- Acesse `http://localhost:8080/h2-console`
- Configure a conexão com:
- Driver: `org.h2.Driver`
- JDBC URL: `jdbc:h2:mem:testdb`
- Usuário: `sa`
- Senha: (vazio)
- Clique em conectar para ver as tabelas e dados.

### 4. Testar os Endpoints REST

- Usar Postman, Insomnia, ou cURL.
- Exemplo para cadastrar produto:

POST http://localhost:8080/api/produtos

{
"nome": "Produto A",
"codigo": "A001",
"preco": 10.0,
"estoqueDisponivel": 100
}

- Exemplo para registrar venda:

POST http://localhost:8080/api/vendas?codigoProduto=A001&quantidade=2


### 5. Compreender erros

- O sistema retorna mensagens claras quando algo não está certo, como preço inválido ou estoque insuficiente.

---



