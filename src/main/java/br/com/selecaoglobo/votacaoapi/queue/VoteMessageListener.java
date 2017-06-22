package br.com.selecaoglobo.votacaoapi.queue;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.selecaoglobo.votacaoapi.dto.VoteDTO;
import br.com.selecaoglobo.votacaoapi.repository.impl.VoteRepositoryImpl;

@Service
public class VoteMessageListener {
    
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private VoteRepositoryImpl voteRepositoryImpl;

    public void handleMessage(VoteDTO voteDTO) {
        String contestSlug = voteDTO.getContestSlug();
        Integer idCandidate = voteDTO.getIdCandidate(); 
        
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            String data = sdf.format(new Date());
            LOG.info("Mensagem recebida, hora: " + data + ", contestSlug: "  + contestSlug + ", idCandidate: " + idCandidate);
            
            this.voteRepositoryImpl.votar(contestSlug, idCandidate);
        } catch (Exception e) {
            LOG.error("Problemas ao votar.", e);
        }
    }
}
