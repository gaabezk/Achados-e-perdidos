# API Achados e Perdidos

API para gerenciamento de usuários e posts do sistema Achados e Perdidos.

## Tecnologias Utilizadas

- Java 17
- Spring Boot 3.0.5
- Spring Data JPA
- Spring Validation
- Spring Security 6
- JWT
- MySQL
- Lombok
- ModelMapper
- SpringDoc

## Endpoints

### Usuários

| Endpoint               | Método HTTP  | Descrição                                    | Permissão mínima |
|------------------------|--------------|----------------------------------------------|------------------|
| /api/user	          | GET	         | Lista todos os usuários                      | Administrador    |
| /api/user/allByRole    | GET	         | Lista todos os usuários por perfil           | Administrador    |
| /api/user/byId	      | GET          | Retorna um usuário pelo ID                   | Administrador    |
| /api/user/byEmail      | GET	         | Retorna um usuário pelo e-mail               | Administrador    |
| /api/user/byPhone      | GET	         | Retorna um usuário pelo telefone             | Administrador    |
| /api/user/exists/id    | GET	         | Verifica se existe um usuário pelo id        | Administrador    |
| /api/user/exists/email | GET	         | Verifica se existe um usuário pelo email     | Administrador    |
| /api/user/exists/phone | GET	         | Verifica se existe um usuário pelo telefone  | Administrador    |
| /api/user	          | POST	     | Cria um novo usuário                         | Público          |
| /api/user	          | PUT	         | Atualiza informações do usuário              | Administrador    |
| /api/user/byPass	      | PUT	         | Atualiza informações do usuário usando senha | Autenticado      |
| /api/user/role	      | PUT	         | Atualiza perfil do usuário                   | Administrador    |
| /api/user/pass	      | PUT	         | Atualiza a senha de um usuário               | Autenticado      |
| /api/user	          | DELETE	     | Exclui um usuário                            | Administrador    |


### Posts

| Endpoint                     | Método HTTP | Descrição                                                                                       | Permissão mínima |
|------------------------------|-------------|-------------------------------------------------------------------------------------------------|------------------|
| /api/post                    | GET         | Recupera todos os posts existentes                                                              | Administrador    |
| /api/post/allByStatus        | GET         | Recupera todos os posts existentes com um status específico                                     | Administrador    |
| /api/post/allApproved        | GET         | Recupera todos os posts aprovados existentes                                                    | Público          |
| /api/post/allByUserAndStatus | GET         | Recupera todos os posts existentes criados por um usuário específico e com um status específico | Administrador    |
| /api/post/allByUserId        | GET         | Recupera todos os posts existentes criados por um usuário específico                            | Administrador    |
| /api/post/allByCity          | GET         | Recupera todos os posts existentes criados em uma cidade específica                             | Público          |
| /api/post/byId               | GET         | Recupera um post com um ID específico                                                           | Público          |
| /api/post                    | POST        | Cria um novo post com os dados fornecidos                                                       | Autenticado      |
| /api/post                    | PUT         | Atualiza um post existente pelo Id do post e Id do usuário autenticado                          | Autenticado      |
| /api/post                    | DELETE      | Deleta um post pelo Id informado                                                                | Administrador    |
| /api/post/byUserId           | DELETE      | Deleta um post pelo Id do usuario informado                                                     | Autenticado      |
| /api/post/status             | PUT         | Atualiza o status de um post pelo Id informado                                                  | Administrador    |


### Autenticação

| Endpoint              | Método HTTP | Descrição                                        |
|-----------------------|-------------|--------------------------------------------------|
| api/auth/authenticate | POST        | Faz o login de um usuário e retorna um token JWT |
| api/auth/register     | POST        | Cria um novo usuário e retorna um token JWT      |


### Autorização

A API utiliza autenticação e autorização via token JWT. Alguns endpoints são protegidos apenas para usuários autenticados e outros apenas para usuários com permissão específica.



## Como Executar

1. Necessario ter um banco MySQL.
2. Clone o repositório: `git clone https://github.com/gaabezk/Achados-e-perdidos.git`
3. Entre na pasta do projeto: `cd Achados-e-Perdidos`
4. Crie o arquivo `secrets.yml` na pasta resources e informe as propriedas: 

```yaml
config:
   jwtSecret: "chave-secreta"
   dbUrl: "jdbc:mysql://localhost:porta/seu-banco"
   dbUser: "usuario-banco"
   dbPassword: "senha-banco"
   ```
5. Execute a aplicação: `./mvnw spring-boot:run`

## Licença

Este projeto está licenciado sob a licença MIT. Consulte o arquivo LICENSE para obter mais informações.
