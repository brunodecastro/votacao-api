package br.com.selecaoglobo.votacaoapi.repository.impl;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import br.com.selecaoglobo.votacaoapi.model.Vote;

@Repository
public class VoteRepositoryImpl {
    
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    
    private final static AtomicInteger count = new AtomicInteger(0); 
    
    @Autowired
    private MongoTemplate mongoTemplate;
    
    public void votar(String contestSlug, Integer idCandidate) throws Exception {
       
        Query query = new Query(
            Criteria.where("contestSlug").is(contestSlug)
                    .and("idCandidate").is(idCandidate));
        
        Update update = new Update().inc("result", 1);

        LOG.info("Vai inserir: " + count.incrementAndGet());
        
        this.mongoTemplate.upsert(query, update, Vote.class);
        
//        this.mongoTemplate.findAndModify(query, update, FindAndModifyOptions.options().upsert(true), Vote.class);
    }
}
