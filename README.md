
# API RESTful desenvolvida em **Java 25** com **Spring Boot** para o gerenciamento de produtos, clientes e pedidos de uma loja especializada em pão chinês (*Bao* / *Baozi*).

## Tecnologias Utilizadas

* **Java 25**

* **Spring Boot** (Spring Web, Spring Data JPA, Bean Validation)

* **MySQL** (Banco de dados relacional)

* **Docker & Docker Compose** (Containerização do banco de dados)

* **Maven** (Gerenciamento de dependências e build)

## Pré-requisitos

Para executar o projeto localmente, você precisa ter instalado em sua máquina:

* **JDK 25** (Java Development Kit 25)

* **Docker** e **Docker Compose**

## Como Configurar e Executar o Projeto

### 1. Clonar o repositório

```
git clone https://github.com/Devini0101/Baozi-Store
cd baozi
```

### 2. Subir o banco de dados com Docker Compose

Na raiz do projeto (onde está localizado o arquivo `docker-compose.yml`), execute o comando abaixo para iniciar o container do MySQL:

```
docker compose up -d

```

> **Nota:** O container criará automaticamente o banco de dados `baozi_store` na porta `3306` com usuário `root` e senha `root`. Certifique-se de que não há nenhum serviço local do MySQL ocupando a porta `3306`, ou altere a porta do container e a obtida pela URL de conexão com o banco em application.properties.

### 3. Executar a aplicação Spring Boot

Utilizando o Maven Wrapper incluído no projeto:

**No Linux / macOS:**

```
./mvnw spring-boot:run

```

**No Windows:**

```
mvnw.cmd spring-boot:run

```

A API estará disponível em: `http://localhost:8080`

## Modelo de Dados (Entidades)

* **`Product` (Produto):**

  * `id` (`Long`): Identificador único gerado automaticamente.

  * `name` (`String`): Nome do produto (obrigatório, mínimo de 3 caracteres).

  * `price` (`BigDecimal`): Valor unitário (obrigatório, valor positivo).

  * `stock` (`Boolean`): Disponibilidade em estoque (`true` ou `false`).

* **`Client` (Cliente):**

  * `id` (`Long`): Identificador único gerado automaticamente.

  * `name` (`String`): Nome completo do cliente (obrigatório).

  * `clientSince` (`LocalDate`): Data de início do relacionamento com a loja (`yyyy-MM-dd`).

* **`Order` (Pedido):**

  * `id` (`Long`): Identificador único gerado automaticamente.

  * `clientId` (`Long`): Identificador do cliente vinculado.

  * `productId` (`Long`): Identificador do produto vinculado.

  * `amount` (`Integer`): Quantidade solicitada no pedido.

## Documentação dos Endpoints

### 1. Produtos (`/api/products`)

| Método | Rota | Descrição | Status de Retorno | 
| ----- | ----- | ----- | ----- | 
| `POST` | `/api/products` | Cadastra um novo produto | `201 Created` / `400 Bad Request` | 
| `GET` | `/api/products` | Lista todos os produtos cadastrados | `200 OK` / `404 Not Found`| 
| `GET` | `/api/products/{id}` | Busca um produto específico pelo ID | `200 OK` / `404 Not Found` | 
| `PUT` | `/api/products/{id}` | Atualiza os dados de um produto existente | `200 OK` / `404 Not Found` | 
| `DELETE` | `/api/products/{id}` | Remove um produto pelo ID | `200 OK` / `404 Not Found` | 

#### Exemplo de Payload (`POST /api/products` e `PUT /api/products/{id}`):

```
{
  "name": "Baozi",
  "price": 12.50,
  "stock": true
}

```

#### Exemplo de Resposta (`POST /api/products` - `201 Created`):

```
{
  "message": "Produto criado com sucesso!"
}

```

### 2. Clientes (`/api/clients`)

| Método | Rota | Descrição | Status de Retorno | 
| ----- | ----- | ----- | ----- | 
| `POST` | `/api/clients` | Cadastra um novo cliente | `201 Created` / `400 Bad Request` | 
| `GET` | `/api/clients` | Lista todos os clientes ordenados por ID | `200 OK` / `404 Not Found`| 
| `GET` | `/api/clients/{id}` | Busca um cliente específico pelo ID | `200 OK` / `404 Not Found` | 
| `PUT` | `/api/clients/{id}` | Atualiza os dados de um cliente existente | `200 OK` / `404 Not Found` | 
| `DELETE` | `/api/clients/{id}` | Remove um cliente pelo ID | `200 OK` / `404 Not Found` | 

#### Exemplo de Payload (`POST /api/clients` e `PUT /api/clients/{id}`):

```
{
  "name": "Nome cliente",
  "clientSince": "2026-11-14"
}

```

#### Exemplo de Resposta (`GET /api/clients/{id}` - `200 OK`):

```
{
  "id": 3,
  "name": "Nome cliente",
  "clientSince": "2026-11-14"
}

```

### 3. Pedidos (`/api/orders`)

> **Regra de Negócio:** Ao criar ou atualizar um pedido, a API valida automaticamente se o `clientId` existe e se o `productId` está cadastrado e com estoque disponível (`stock = true`).

| Método | Rota | Descrição | Status de Retorno | 
| ----- | ----- | ----- | ----- | 
| `POST` | `/api/orders` | Cria um novo pedido | `201 Created` / `400 Bad Request` / `404 Not Found` | 
| `GET` | `/api/orders` | Lista todos os pedidos registrados | `200 OK` / `404 Not Found`| 
| `GET` | `/api/orders/{id}` | Busca um pedido específico pelo ID | `200 OK` / `404 Not Found` | 
| `PUT` | `/api/orders/{id}` | Atualiza os dados de um pedido existente | `200 OK` / `404 Not Found` | 
| `DELETE` | `/api/orders/{id}` | Remove/cancela um pedido pelo ID | `200 OK` / `404 Not Found` | 

#### Exemplo de Payload (`POST /api/orders` e `PUT /api/orders/{id}`):

```
{
  "clientId": 3,
  "productId": 5,
  "amount": 12
}

```

#### Exemplo de Resposta (`GET /api/orders/{id}` - `200 OK`):

```
{
  "id": 2,
  "clientId": 3,
  "productId": 5,
  "amount": 12
}

```