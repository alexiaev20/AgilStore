AgilStore

AgilStore é um sistema simples de gerenciamento de produtos desenvolvido usando Spring Boot, MySQL e ferramentas como Swagger e Workbench para documentação e manipulação de banco de dados.

Tecnologias Utilizadas

Java 17

Spring Boot

MySQL com Workbench

Swagger para documentação da API

Funcionalidades

Cadastro de Produtos: Permite adicionar novos produtos ao sistema.

Consulta de Produtos: Busca todos os produtos com opções de filtro por:

Categoria

Ordenação por nome, quantidade ou preço

Paginação: Exibição de produtos em páginas com tamanhos configuráveis.

Busca por ID: Recupera um produto específico pelo seu identificador.

Atualização de Produto: Atualiza as informações de um produto existente.

Exclusão de Produto: Remove um produto do sistema.

Instalação e Configuração

Clone este repositório:

git clone https://github.com/usuario/agilstore.git

Configure o banco de dados MySQL no arquivo application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/agilstore
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update

Execute a aplicação:

mvn spring-boot:run

Acesse a documentação da API:

Swagger: http://localhost:8080/swagger-ui/index.html

Endpoints Principais

Método

Endpoint

Descrição

POST

/api/produtos

Adiciona um novo produto

GET

/api/produtos

Lista todos os produtos com filtros e ordenação

GET

/api/produtos/{id}

Busca um produto por ID

PUT

/api/produtos/{id}

Atualiza um produto existente

DELETE

/api/produtos/{id}

Exclui um produto

Exemplos de Requisições

Consulta com Categoria e Ordenação:

curl -X GET "http://localhost:8080/api/produtos?categoria=Periferico&ordenarPor=preco"

Paginação:

curl -X GET "http://localhost:8080/api/produtos/paginado?pagina=0&tamanho=5&ordenarPor=nome&direcao=asc"

Cadastro de Produto:

curl -X POST -H "Content-Type: application/json" -d '{"nome":"Teclado","categoria":"Periferico","quantidade":15,"preco":200.0}' http://localhost:8080/api/produtos

Observações

Certifique-se de que o MySQL esteja rodando antes de iniciar a aplicação.

Use o Swagger para interagir de forma intuitiva com os endpoints durante o desenvolvimento e teste.

Melhorias Futuras

Implementação de um relatório em PDF usando iText para exportar dados de produtos.

Autenticação e autorização usando Spring Security.

Licença

Este projeto é de uso livre para fins educacionais e demonstrações.