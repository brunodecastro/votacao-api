package br.com.bco.votacaoapi.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

import br.com.bco.votacaoapi.model.Vote;

@Transactional
public interface VoteRepository extends MongoRepository<Vote, String> {
    
    List<Vote> findByContestSlugAndIdCandidate(String contestSlug, Integer idCandidate);
}
