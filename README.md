## Serviço REST para gestão de pautas com sessões de votação

### Funcionalidades:

- Cadastrar uma nova pauta
- Abrir sessão de votação na pauta
- Receber votos dos associados
- Obter resultado da pauta (conforme os filtros)


### Tecnologias utilizadas
- [Java 11](https://www.oracle.com/br/java/technologies/javase/jdk11-archive-downloads.html)
- [Gradle 7.0+](https://docs.gradle.org/7.0/userguide/userguide.html)
- [Spring Boot (2.7.5)](https://spring.io/projects/spring-boot)
- [JUnit5](https://junit.org/junit5/docs/current/user-guide/), [Mockito](https://site.mockito.org/)
- [Swagger (openapi v1.6.14)](https://springdoc.org/)
- [Docker](https://www.docker.com/), [Docker Compose](https://docs.docker.com/compose/)
- [MongoDB](https://www.mongodb.com/)
- [RabbitMQ](https://www.rabbitmq.com/)

A maioria das tecnologias foi integrada ao ecossistema do Spring Boot.

### Descrições técnicas

A aplicação foi dividida em camadas, sendo elas: domínio (_domain_), serviço (_service_) e controle (_controller_). Utilizando o padrão do serviço REST.

O domínio consiste das classes Ruling, Session, Vote e Cpf. As regras de negócio estão nas classes _Service_ e juntas definem uma _collection_ **não** estrutura no mongodb.

### Exemplo de reposta do serviço (Api)
```
{
    "rulingId": "507f1f77bcf86cd799439011",
    "name": "Name of ruling",
    "numberYesVotes": 2,
    "numberNoVotes": 1,
    "result": "YES"
}
```

Uma Pauta pode ter os seguintes status:
- NOT_STARTED: Indica que essa pauta ainda não foi aberta para votação
- OPEN: Indica que a pauta foi aberta para votação
- CLOSED: Indica que a sessão de votação para a pauta foi encerrada

Se a pauta não for encerrada, foi definido um agendador de tarefas (schedule) que por segundo busca 
as pautas abertas e verifica se o tempo limite de votação foi atingido. Se encontra, o status da pauta é marcado como CLOSED e o resultado da votação é calculado e transmitido para a fila de mensagens pelo RabbitMQ.


### Executando o projeto

#### Serviços
- Servidor de mensageria RabbitMq
- Mongodb
- API

#### Execução dos testes

```
gradle test
```

#### Execução da aplicação
```
docker-compose build
docker-compose up
```

### Endereço da API

http://localhost:8080/api/v1

### Documentação no Swagger

http://localhost:8080/swagger-ui/index.html

### RabbitMQ UI

http://localhost:15672/#/


### Teste de carga utilizando Apache JMeter
```
| Endpoint                 | # Amostras | Média | Mín. | Máx. | Desvio Padrão | % de Erro | Vazão     | KB/s  | Sent KB/s | Média de Bytes |
|--------------------------|------------|-------|------|------|---------------|-----------|-----------|-------|-----------|----------------|
| /api/v1/ruling           | 100        | 1     | 1    | 4    | 0,53          | 0,00%     | 108,9/sec | 19,47 | 29,57     | 183,0          |
| /api/v1/ruling/vote     | 100        | 214   | 153  | 823  | 156,64        | 49,0%     | 34,0/sec  | 13,49 | 216,0     | 216,0          |
| /api/v1/ruling/vote *   | 100        | 23    | 6    | 318  | 42,39         | 0,00%     | 102,0/sec | 23,92 | 39,76     | 240,0          |
| /api/v1/ruling/result | 100        | 3     | 2    | 9    | 0,87          | 0,00%     | 107,7/sec | 31,02 | 27,45     | 296,0          |
```

O teste de carga foi feito sem validação pela API externa de CPF.
