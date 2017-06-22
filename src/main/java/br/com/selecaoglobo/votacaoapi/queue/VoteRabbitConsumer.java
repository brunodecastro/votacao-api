package br.com.selecaoglobo.votacaoapi.queue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

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
    
    private final static AtomicInteger count = new AtomicInteger(0); 
    
    @Autowired
    private VoteRepositoryImpl voteRepositoryImpl;

    @Async
    @RabbitHandler
    public void handleMessage(VoteDTO voteDTO) {
        String contestSlug = voteDTO.getContestSlug();
        Integer idCandidate = voteDTO.getIdCandidate(); 
        
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            String data = sdf.format(new Date());
            LOG.info("Mensagem recebida, hora: " + data + ", Numero: " + count.incrementAndGet());
            
            this.voteRepositoryImpl.votar(contestSlug, idCandidate);
            
//            LOG.info("Terminou hora: " + data + " Numero: " + count.get());
        } catch (Exception e) {
        }
    }
}
