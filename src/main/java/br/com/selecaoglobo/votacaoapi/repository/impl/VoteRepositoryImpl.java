package br.com.selecaoglobo.votacaoapi.repository.impl;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.WriteResult;

import br.com.selecaoglobo.votacaoapi.cache.VoteCache;
import br.com.selecaoglobo.votacaoapi.dto.ContestVotesDTO;
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
//            this.mongoTemplate.upsert(query, update, Vote.class);
            
            Vote vote = this.mongoTemplate.findAndModify(query, update, FindAndModifyOptions.options().upsert(true).returnNew(true), Vote.class);
            
            // Adiciona os dados do voto no cache
//            VoteCache.putVoteResultsByContestInCache(vote.getContestSlug(), vote.getResult());
//            VoteCache.putVoteResultsByContestInCacheAndCandidate(vote.getContestSlug(), vote.getIdCandidate(), vote.getResult());
            VoteCache.incrementVotesDataInCache(contestSlug, idCandidate);
        } catch (DuplicateKeyException e) {
            this.mongoTemplate.updateFirst(query, update, Vote.class);
            VoteCache.incrementVotesDataInCache(contestSlug, idCandidate);
        }
        
    }
    
    /**
     * Busca os votos do contest.
     * 
     * @param contestSlug
     * @return ContestVotesDTO
     */
    public ContestVotesDTO findVotesResultForContest(String contestSlug) {
        
        TypedAggregation<Vote> aggregation = newAggregation(Vote.class, 
                                                            match(Criteria.where("contestSlug").is(contestSlug)),
                                                            group("contestSlug").sum("result").as("result"),
                                                            project("result").and("contestSlug").previousOperation());
        
        AggregationResults<ContestVotesDTO> results = this.mongoTemplate.aggregate(aggregation, Vote.class, ContestVotesDTO.class);
        
        return results.getUniqueMappedResult();
    }
    
    /**
     * Busca a votação geral agrupado por contest.
     * 
     * @return ContestVotesDTO
     */
    public List<ContestVotesDTO> findVotesResultGroupedByContest() {
        
        TypedAggregation<Vote> aggregation = newAggregation(Vote.class, 
                                                            group("contestSlug").sum("result").as("result"),
                                                            project("result").and("contestSlug").previousOperation());
        
        AggregationResults<ContestVotesDTO> results = this.mongoTemplate.aggregate(aggregation, Vote.class, ContestVotesDTO.class);
        
        return results.getMappedResults();
    }
    
}
