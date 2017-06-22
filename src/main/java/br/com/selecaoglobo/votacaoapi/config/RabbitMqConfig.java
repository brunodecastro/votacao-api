package br.com.selecaoglobo.votacaoapi.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import br.com.selecaoglobo.votacaoapi.queue.VoteRabbitConsumer;

@Configuration
@EnableRabbit
public class RabbitMqConfig {
    
    private static final String VOTE_MESSAGE_QUEUE = "vote.queue";
    
    @Value(value = "${spring.rabbitmq.host}")
    private String hostName;

    @Value(value = "${spring.rabbitmq.port}")
    private String port;
    
    @Value(value = "${spring.rabbitmq.username}")
    private String username;
    
    @Value(value = "${spring.rabbitmq.password}")
    private String password;
    
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        return connectionFactory;
    }

    @Bean
    public Queue voteQueue() {
        return new Queue(VOTE_MESSAGE_QUEUE);
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public VoteRabbitConsumer voteConsumer() {
        return new VoteRabbitConsumer();
    }
    
    @Bean
    public MessageConverter jsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(this.connectionFactory());
        template.setRoutingKey(VOTE_MESSAGE_QUEUE);
        template.setMessageConverter(this.jsonMessageConverter());
        return template;
    }
    
    @Bean
    public SimpleMessageListenerContainer listenerContainer() {
        SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer();
        listenerContainer.setConnectionFactory(this.connectionFactory());
        listenerContainer.setQueues(this.voteQueue());
        listenerContainer.setMessageConverter(this.jsonMessageConverter());
        listenerContainer.setAcknowledgeMode(AcknowledgeMode.AUTO);
        
        listenerContainer.setMaxConcurrentConsumers(5);  
        listenerContainer.setConcurrentConsumers(5);  
        listenerContainer.setAutoStartup(false);
        
        return listenerContainer;
    }
}
