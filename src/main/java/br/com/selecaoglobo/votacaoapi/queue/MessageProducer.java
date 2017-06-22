package br.com.selecaoglobo.votacaoapi.queue;

import br.com.selecaoglobo.votacaoapi.dto.VoteDTO;

public interface MessageProducer {
    
    void sendVote(VoteDTO voteDTO);
}
