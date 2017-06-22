package br.com.selecaoglobo.votacaoapi.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import br.com.selecaoglobo.votacaoapi.model.Vote;

/**
 * Repository Vote.
 * 
 * @author bruno.oliveira
 */
@Repository
public class VoteRepositoryImpl {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * Incrementa o field 'result' a cada voto.
     * 
     * @param contestSlug
     * @param idCandidate
     * @throws Exception
     */
    public void vote(String contestSlug, Integer idCandidate) throws Exception {

        Query query = new Query(Criteria.where("contestSlug").is(contestSlug).and("idCandidate").is(idCandidate));
        Update update = new Update().inc("result", 1);

        try {
            this.mongoTemplate.upsert(query, update, Vote.class);
        } catch (DuplicateKeyException e) {
            this.mongoTemplate.updateFirst(query, update, Vote.class);
        }
    }
    
    
}
