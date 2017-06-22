package br.com.selecaoglobo.votacaoapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

import br.com.selecaoglobo.votacaoapi.model.Contest;

@Transactional
public interface ContestRepository extends MongoRepository<Contest, String> {

}
