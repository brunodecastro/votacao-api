**API de Votação**

* Api para votação.


#### Tecnologias Utilizadas

* Java 8
* Spring Boot 1.5.4
* Maven 3.1.1
* Redis 3.2.9
* MongoDB 3.4.4
* Swagger 2.6.1


====================

#### Iniciar a Aplicação

Se quiser rodar com Docker:

Faça o download do [Docker](https://www.docker.com/products/overview) e do [Docker Compose](https://docs.docker.com/compose), verifique se está com a última versão do [Compose](https://docs.docker.com/compose/install/).

Rode nesse diretório:

```
docker-compose up
```

A aplicação irá rodar na url [http://localhost:8080](http://localhost:8080)


 #### Alternativa para rodar a api: 
 
 Gerar o pacote:
 
 ##### `mvn clean install` na raiz da aplicação.
 
Depois rode a api:
 
 ##### `java -jar target/votacao-api.jar` na raiz da aplicação.
 
 Acesse a documentação da api:
 
 ##### `http://localhost:9000`


#### Arquitetura e Tecnologias Utilizadas
---------------

* Java 8
* Spring Boot 1.5.4
* Maven 3.1.1
* Redis 3.2.9
* MongoDB 3.4.4
* Swagger 2.6.1


====================

#### Spring Boot

Utilizado para geração da api como micro serviço e aplicação de testes.

====================

#### Swagger

Utilizado para documentação da api.

====================

#### Redis

* Host: localhost
* Port: 6379

Utilizado para cache de dados e fila de requisições.

====================

#### MongoDB

* Host: localhost
* Port: 27017
* Database: votacao_db

Utilizado como banco de dados.

====================


  
  