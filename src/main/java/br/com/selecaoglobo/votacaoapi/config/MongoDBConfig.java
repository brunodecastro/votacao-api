package br.com.selecaoglobo.votacaoapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.MongoClientOptions;
import com.mongodb.WriteConcern;

@Configuration
@EnableMongoAuditing
@EnableMongoRepositories(basePackages="br.com.selecaoglobo.votacaoapi.repository")
public class MongoDBConfig {

    @Value(value = "${spring.data.mongodb.host}")
    private String hostName;

    @Value(value = "${spring.data.mongodb.port}")
    private String port;
    
    /*
     * Factory bean that creates the com.mongodb.Mongo instance
     */
    @Bean
     public MongoClientFactoryBean mongo() {
          MongoClientFactoryBean mongo = new MongoClientFactoryBean();
          mongo.setHost(hostName);
          mongo.setPort(Integer.parseInt(port));
          
          MongoClientOptions mongoClientOptions = MongoClientOptions.builder()
                  .maxWaitTime(1500)
                  .socketKeepAlive(true)
                  .socketTimeout(3000)
                  .minHeartbeatFrequency(25)
                  .heartbeatSocketTimeout(3000)
                  .writeConcern(WriteConcern.ACKNOWLEDGED)
                  .build();
          mongo.setMongoClientOptions(mongoClientOptions);
          
          return mongo;
     }
     
//     @Bean
//     public WriteConcernResolver writeConcernResolver() {
//         return action -> {
//             System.out.println("Using Write Concern of Acknowledged");
//             return WriteConcern.FSYNC_SAFE;
//         };
//     }
}
