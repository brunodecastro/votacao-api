package br.com.bco.votacaoapi.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

import br.com.bco.votacaoapi.model.Candidate;

@Transactional
public interface CandidateRepository extends MongoRepository<Candidate, String> {
    
    
    List<Candidate> findByContestSlug(String contestSlug);

}
