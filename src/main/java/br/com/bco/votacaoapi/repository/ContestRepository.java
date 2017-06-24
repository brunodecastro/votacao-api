package br.com.bco.votacaoapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

import br.com.bco.votacaoapi.model.Contest;

@Transactional
public interface ContestRepository extends MongoRepository<Contest, String> {

}
