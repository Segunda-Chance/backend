# Segunda Chance e-commerce - Projeto Integrador Generation Brasil

## Descrição do Projeto
Este é um projeto integrador desenvolvido como parte do programa Generation Brasil. A API oferece operações como criar, atualizar e excluir para as entidades de Produto, Categoria e Usuário. A segurança foi feita com Spring Security e uso de DTO's para proteger informações sensíveis dos usuários.

## Swagger
A documentação da API está disponível via Swagger.

### Configuração do Swagger
A configuração do Swagger pode ser encontrada na classe `SwaggerConfig`. Ela define informações sobre a API, como título, descrição, versão e licença. Além disso, são fornecidos links para documentação externa e informações de contato.

## Tratamento de Exceções
Há um tratamento global de exceções implementado na classe `GlobalExceptionHandler`. Essa classe padroniza o retorno de erros comuns como erros de validação de dados, acesso não autorizado e objetos não encontrados.

## Tecnologias usadas
- Java 17
- SpringBoot 3.2.0
- MySQL 8.0
- Maven 4.0.0
- Hibernate
- Spring Security
- Spring Data JPA

## Executando o projeto localmente

### Pré-requisitos
Antes de começar, certifique-se de ter instalado:

- Java JDK 11 ou superior
- MySQL
- Maven

### Configuração

1. Clone o repositório:

```
git clone https://github.com/Segunda-Chance/backend
```

2. Configure o banco de dados no arquivo src/main/resources/application-dev.properties.

```properties
spring.datasource.url=jdbc:mysql://localhost/nome_do_banco?createDatabaseIfNotExist=true&serverTimezone=America/Sao_Paulo&useSSl=false
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```
**Não esqueça de alterar o modo para `dev` no arquivo `application.properties`**

3. Execute o aplicativo:

```
mvn spring-boot:run
```

O aplicativo estará acessível em http://localhost:8080.

## Testando a aplicação

Para realização de testes foi usado o Insomnia, mas as funcionalidades podem ser testadas na ferramenta de sua preferência. Para verificar os endpoints disponíveis para teste, acesse as classes no pacote 'com.generation.cultdrugstore.controller'.

## Contribuindo
Se você estiver interessado em contribuir, siga estes passos:

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/nova-feature`)
3. Faça commit das suas mudanças (`git commit -m 'Adiciona nova feature'`)
4. Faça push para a branch (`git push origin feature/nova-feature`)
5. Abra um Pull Request
