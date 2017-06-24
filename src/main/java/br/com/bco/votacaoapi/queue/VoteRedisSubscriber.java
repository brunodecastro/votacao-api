package br.com.bco.votacaoapi.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import br.com.bco.votacaoapi.dto.VoteDTO;
import br.com.bco.votacaoapi.repository.impl.VoteRepositoryImpl;

/**
 * Subscriber da fila do Redis.
 * 
 * @author bruno.oliveira
 */
@Component
public class VoteRedisSubscriber {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private VoteRepositoryImpl voteRepositoryImpl;

    /**
     * Consome a mensagem da fila.
     * 
     * @param voteDTO
     */
    @Async
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
