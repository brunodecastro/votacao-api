package br.com.selecaoglobo.votacaoapi.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

import br.com.selecaoglobo.votacaoapi.dto.VoteDTO;

@Component
public class VoteMessagePublisher implements MessagePublisher {
    
    @Autowired
    private RedisTemplate<String, VoteDTO> voteRedisTemplate;
    
    @Autowired
    private ChannelTopic topic;
 
    public VoteMessagePublisher() {
    }
 
    public VoteMessagePublisher(RedisTemplate<String, VoteDTO> voteRedisTemplate, ChannelTopic topic) {
      this.voteRedisTemplate = voteRedisTemplate;
      this.topic = topic;
    }
 
    public void publishVote(VoteDTO voteDTO) {
        voteRedisTemplate.convertAndSend(topic.getTopic(), voteDTO);
    }

}
