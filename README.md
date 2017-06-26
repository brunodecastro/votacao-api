**API de Votação**
---------------

* Api para votação.


#### Iniciar a Aplicação
---------------

Se quiser rodar com Docker:

Faça o download do [Docker](https://www.docker.com/products/overview) e do [Docker Compose](https://docs.docker.com/compose), verifique se está com a última versão do [Compose](https://docs.docker.com/compose/install/).

Rode nesse diretório:

```
docker-compose up
```

 Acesse a documentação da api na url: [http://localhost:8080](http://localhost:8080)

Obs: Não necessita da instalação do MongoDB nem do Redis. Ambos subirão como containers docker. 


 #### Alternativa para rodar a api: 
 ---------------
 
 Faça o download do [Maven](https://maven.apache.org/download.cgi?Preferred=ftp%3A%2F%2Fmirror.reverse.net%2Fpub%2Fapache%2F) e faça sua instalação: [Maven Install](https://maven.apache.org/install.html)
 Necessita da instalação do [MondoDB](https://www.mongodb.com/download-center#community) e do [Redis](https://redis.io/download). 
 Estão configurados nas portas padrões e no host: `localhost`. 
 
Rode nesse diretório:
 
 ```
mvn clean install
```
 
Depois rode a api:
 
```
java -jar target/votacao-api-dev.jar
```
 
 Acesse a documentação da api na url: [http://localhost:9000](http://localhost:9000)



#### Arquitetura e Tecnologias Utilizadas
---------------

* Java 8
* Spring Boot 1.5.4
* Maven 3.1.1
* Redis 3.2.9
* MongoDB 3.4.4
* Swagger 2.6.1


#### Spring Boot

Utilizado para geração da api como micro serviço e aplicação de testes.



#### Swagger

Utilizado para documentação da api.



#### Redis

* Host: localhost
* Port: 6379

Utilizado para cache de dados e fila de requisições.



#### MongoDB

* Host: localhost
* Port: 27017
* Database: votacao_db

Utilizado como banco de dados.



  
  