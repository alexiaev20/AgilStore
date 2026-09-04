# AgilStore - API de Gestão de Inventário Corporativa

A AgilStore é uma API RESTful completa de nível de produção desenvolvida em Java com Spring Boot. Este sistema foi arquitetado para suportar operações de larga escala, contemplando recursos avançados de Engenharia de Software como Event-Driven Architecture (Mensageria), HATEOAS, Observabilidade (Prometheus) e Cache.

## Tecnologias Utilizadas

- **Linguagem:** Java 17
- **Framework:** Spring Boot 3
- **Banco de Dados:** MySQL 8
- **Persistência e ORM:** Spring Data JPA + Hibernate
- **Migrations:** Flyway
- **Monitoramento e Observabilidade:** Spring Boot Actuator + Prometheus
- **Performance:** Spring Cache
- **DevOps:** Docker & Docker Compose
- **Documentação:** Swagger (OpenAPI 3)
- **Geração de Relatórios:** iTextPDF

##  Arquitetura e Recursos Avançados

1. **HATEOAS e Filtros Dinâmicos:** A API utiliza a *Specification API* para cruzamento dinâmico de filtros na listagem de produtos. As respostas JSON implementam o Nível 3 de Maturidade REST de Richardson, guiando o cliente através de links de navegação (`_links`).
2. **Event-Driven Architecture:** Histórico contábil rigoroso através da entidade `MovimentacaoEstoque`. Rotinas em background (`@Scheduled`) publicam alertas assíncronos via *Spring ApplicationEvents* na detecção de quebras de estoque mínimo, não bloqueando a thread principal.
3. **Resiliência e Validação:** *Bean Validation* em todos os DTOs protegendo contra valores negativos. *ControllerAdvice* interceptando exceções de forma global para padronização de erros JSON.
4. **Infraestrutura Otimizada:** Cache em memória aliviando sobrecargas de leitura e geração transacional dinâmica de relatórios em PDF do estoque fechado.

## ⚙️ Como Executar o Projeto

Graças à conteinerização via Docker Compose, você não precisa configurar um banco de dados local.

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/alexiaev20/AgilStore.git
   ```
2. **Suba a infraestrutura (Banco MySQL + API + Prometheus):**
   ```bash
   docker-compose up -d --build
   ```
3. **Acesse a Documentação (Swagger):**
   Abra seu navegador em: `http://localhost:8080/swagger-ui.html`

##  Monitoramento
A aplicação está configurada para exportar métricas. Com o Docker rodando, o Prometheus estará acessível para monitorar a saúde da API e do banco na porta `9090`.

---
Desenvolvido aplicando os mais altos rigores de *Clean Code* e Design de Software.
