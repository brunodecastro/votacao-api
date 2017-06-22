package br.com.selecaoglobo.votacaoapi.queue;

import br.com.selecaoglobo.votacaoapi.dto.VoteDTO;

public interface MessagePublisher {
    
    void publishVote(VoteDTO voteDTO);
}
