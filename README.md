# AgilStore

AgilStore é um sistema simples de gerenciamento de produtos desenvolvido usando Spring Boot, MySQL e ferramentas como Swagger e para documentação .

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot**
- **MySQL com Workbench**
- **Swagger para documentação da API**

## Funcionalidades

- **Cadastro de Produtos**: Permite adicionar novos produtos ao sistema.
- **Consulta de Produtos**: Busca todos os produtos com opções de filtro por:
  - Categoria
  - Ordenação por nome, quantidade ou preço
- **Paginação**: Exibição de produtos em páginas com tamanhos configuráveis.
- **Busca por ID**: Recupera um produto específico pelo seu identificador.
- **Atualização de Produto**: Atualiza as informações de um produto existente.
- **Exclusão de Produto**: Remove um produto do sistema.

## Instalação e Configuração

1. Clone este repositório:

   ```bash
   git clone https://github.com/usuario/agilstore.git

2. Configure o banco de dados MySQL no arquivo `application.properties`:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/agilstore
   spring.datasource.username=seu_usuario
   spring.datasource.password=sua_senha
   spring.jpa.hibernate.ddl-auto=update
3. Execute a aplicação:

   ```bash
   mvn spring-boot:run
4. Acesse a documentação da API:

   Swagger: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## Endpoints Principais

| Método | Endpoint              | Descrição                    |
|--------|-----------------------|------------------------------|
| POST   | /api/produtos          | Adiciona um novo produto     |
| GET    | /api/produtos          | Lista todos os produtos com filtros e ordenação |
| GET    | /api/produtos/{id}     | Busca um produto por ID      |
| PUT    | /api/produtos/{id}     | Atualiza um produto existente|
| DELETE | /api/produtos/{id}     | Exclui um produto            |


## Exemplos de Consultas com `curl`

### 1. **Consultar todos os produtos (GET)**

```bash
curl -X GET "http://localhost:8080/api/produtos"
```

 **Buscar produtos filtrando por categoria (GET)**

Aqui você pode adicionar o parâmetro `categoria` para buscar produtos em uma categoria específica.

```bash
curl -X GET "http://localhost:8080/api/produtos?categoria=Periferico"
```
## Observações

- Certifique-se de que o MySQL esteja rodando antes de iniciar a aplicação.
- Use o Swagger para interagir de forma intuitiva com os endpoints durante o desenvolvimento e teste.
