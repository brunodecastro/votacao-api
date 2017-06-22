package br.com.selecaoglobo.votacaoapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import br.com.selecaoglobo.votacaoapi.dto.VoteDTO;
import br.com.selecaoglobo.votacaoapi.queue.MessageProducer;
import br.com.selecaoglobo.votacaoapi.queue.VoteRedisSubscriber;
import br.com.selecaoglobo.votacaoapi.queue.VoteRedisPublisher;

@Configuration
public class RedisConfig extends CachingConfigurerSupport {
    
    private static final String VOTE_MESSAGE_QUEUE = "vote.queue";

    @Value(value = "${spring.redis.host}")
    private String hostName;

    @Value(value = "${spring.redis.port}")
    private String port;

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(hostName);
        factory.setPort(Integer.parseInt(port));
        factory.setUsePool(true);
        return factory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(this.jedisConnectionFactory());
        return redisTemplate;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate() {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(this.jedisConnectionFactory());
        return stringRedisTemplate;
    }
    
    @Bean
    public RedisTemplate<String, VoteDTO> voteRedisTemplate() {
        RedisTemplate<String, VoteDTO> voteRedisTemplate = new RedisTemplate<String, VoteDTO>();
        voteRedisTemplate.setConnectionFactory(this.jedisConnectionFactory());
        voteRedisTemplate.setKeySerializer(new StringRedisSerializer()); 
        voteRedisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(VoteDTO.class));
        voteRedisTemplate.afterPropertiesSet();
        return voteRedisTemplate;
    }

    @Bean
    @Override
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager(this.stringRedisTemplate());
        redisCacheManager.setTransactionAware(true);
        redisCacheManager.setLoadRemoteCachesOnStartup(true);
        return redisCacheManager;
    }

    @Bean
    public VoteRedisSubscriber voteRedisSubscriber() {
        return new VoteRedisSubscriber();
    }

    @Bean
    public MessageListenerAdapter voteMessageListenerAdapter(VoteRedisSubscriber voteMessageListener) {
        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(voteMessageListener);
        messageListenerAdapter.setSerializer(new Jackson2JsonRedisSerializer<>(VoteDTO.class));
        return messageListenerAdapter;
    }
    
    @Bean
    public RedisMessageListenerContainer redisContainer(RedisConnectionFactory connectionFactory, MessageListenerAdapter messageListenerAdapter) {
        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(messageListenerAdapter, this.topic());
        return container;
    }

    @Bean
    public MessageProducer redisPublisher() {
        return new VoteRedisPublisher(this.voteRedisTemplate(), this.topic());
    }

    @Bean
    public ChannelTopic topic() {
        return new ChannelTopic(VOTE_MESSAGE_QUEUE);
    }
}
