package br.com.selecaoglobo.votacaoapi.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import br.com.selecaoglobo.votacaoapi.dto.VoteDTO;
import br.com.selecaoglobo.votacaoapi.repository.impl.VoteRepositoryImpl;

@Component
@RabbitListener(queues = "vote.queue")
public class VoteRabbitConsumer {
    
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private VoteRepositoryImpl voteRepositoryImpl;

    @Async
    @RabbitHandler
    public void handleMessage(VoteDTO voteDTO) {
        String contestSlug = voteDTO.getContestSlug();
        Integer idCandidate = voteDTO.getIdCandidate(); 
        
        try {
            this.voteRepositoryImpl.vote(contestSlug, idCandidate);
        } catch (Exception e) {
            LOG.error("Problemas ao votar.", e);
        }
    }
}
