package br.com.selecaoglobo.votacaoapi.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

import br.com.selecaoglobo.votacaoapi.dto.VoteDTO;

/**
 * Publisher da fila do Redis.
 * 
 * @author bruno.oliveira
 */
@Component
public class VoteRedisPublisher implements MessageProducer {

    @Autowired
    private RedisTemplate<String, VoteDTO> voteRedisTemplate;

    @Autowired
    private ChannelTopic topic;

    public VoteRedisPublisher() {
    }

    public VoteRedisPublisher(RedisTemplate<String, VoteDTO> voteRedisTemplate, ChannelTopic topic) {
        this.voteRedisTemplate = voteRedisTemplate;
        this.topic = topic;
    }

    public void sendVote(VoteDTO voteDTO) {
        voteRedisTemplate.convertAndSend(topic.getTopic(), voteDTO);
    }

}
