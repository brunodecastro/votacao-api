package br.com.bco.votacaoapi.queue;

import br.com.bco.votacaoapi.dto.VoteDTO;

public interface MessageProducer {
    
    void sendVote(VoteDTO voteDTO);
}
