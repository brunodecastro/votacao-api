package br.com.selecaoglobo.votacaoapi.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import br.com.selecaoglobo.votacaoapi.model.Sequence;

@Repository
public class SequenceGeneratorRepositoryImpl {
    
    public final static String SEQUENCE_CANDIDATE = "sequenceCandidate";
    
    @Autowired
    private MongoTemplate mongoTemplate;
    

    @Autowired
    public SequenceGeneratorRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;

        if (mongoTemplate.findById(SEQUENCE_CANDIDATE, Sequence.class) == null) {
            this.mongoTemplate.insert(new Sequence(SEQUENCE_CANDIDATE, 0));
        }
    }
    
    public Integer getNextCandidateSequence() {
        
        Query query = new Query(Criteria.where("name").is(SEQUENCE_CANDIDATE));
        Update update = new Update().inc("counter", 1);
        
        int counter = mongoTemplate.findAndModify(query, update, FindAndModifyOptions.options().upsert(true).returnNew(true), Sequence.class).getCounter();
        return counter;
    }
}
