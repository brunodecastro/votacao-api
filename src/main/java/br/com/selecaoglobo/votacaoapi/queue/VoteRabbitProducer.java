package br.com.selecaoglobo.votacaoapi.queue;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.selecaoglobo.votacaoapi.dto.VoteDTO;

@Component
public class VoteRabbitProducer {
    
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    
    private final static AtomicInteger count = new AtomicInteger(0); 

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Queue queue;

    public void sendVote(VoteDTO voteDTO) {
        LOG.info("### MENSAGEM MANDADA, Numero: " + count.incrementAndGet());
        this.rabbitTemplate.convertAndSend(queue.getName(), voteDTO);
    }
}
