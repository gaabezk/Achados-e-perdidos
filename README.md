# API Achados e Perdidos

API para gerenciamento de usuários e posts do sistema Achados e Perdidos.

## Tecnologias Utilizadas

- Java 17
- Spring Boot 3.0.2
- Spring Data JPA
- Spring Validation
- Spring Security
- JWT
- MySQL
- Lombok
- ModelMapper
- SpringDoc

## Endpoints

### Usuários

| Método | Endpoint | Descrição | Autenticação |
| --- | --- | --- | --- |
| POST | /api/user | Cria um novo usuário | Não |
| GET | /api/user | Lista todos os usuários | Sim |
| GET | /api/user/allByRole | Lista todos os usuários por perfil | Não |
| GET | /api/byId | Retorna um usuário pelo ID | Não |
| GET | /api/user/byEmail | Retorna um usuário pelo e-mail | Não |
| GET | /api/user/byPhone | Retorna um usuário pelo telefone | Não |
| GET | /api/user/exists/id | Procura um usuário pelo ID e retorna um boleano | Não |
| GET | /api/user/exists/email | Procura um usuário pelo E-MAIL e retorna um boleano | Não |
| GET | /api/user/exists/phone | Procura um usuário pelo TELEFONE e retorna um boleano | Não |
| PUT | /api/user | Atualiza informações do usuário | Não |
| PUT | /api/user/role | Atualiza perfil do usuário | Não |
| PUT | /api/user/pass | Atualiza a senha de um usuário | Não |
| DELETE | /api/user | Exclui um usuário | Não |


<!-- ### Autenticação

| Método | Endpoint | Descrição | Autenticação |
| --- | --- | --- | --- |
| POST | /auth/login | Faz o login de um usuário e retorna um token JWT | Não |
| POST | /auth/refresh | Atualiza o token JWT de um usuário | Sim | -->


### Posts

| Método | Endpoint | Descrição | Autenticação |
| --- | --- | --- | --- |
| POST | /posts | Cria um novo post | Sim |
| GET | /posts | Retorna todos os posts | Sim |
| GET | /posts/{id} | Retorna um post pelo ID | Sim |
| PUT | /posts/{id} | Atualiza um post existente pelo ID | Sim |
| DELETE | /posts/{id} | Deleta um post pelo ID | Sim |

## Autenticação e Autorização

A API utiliza autenticação e autorização via token JWT. Alguns endpoints são protegidos apenas para usuários autenticados e outros apenas para usuários com permissão específica.

## Como Executar

1. Clone o repositório: `git clone https://github.com/seu-usuario/seu-fork.git`
2. Entre na pasta do projeto: `cd Achados-e-Perdidos`
3. Configure as propriedades do banco de dados no arquivo `application.properties`
4. Execute a aplicação: `./mvnw spring-boot:run`

## Licença

Este projeto está licenciado sob a licença MIT. Consulte o arquivo LICENSE para obter mais informações.
