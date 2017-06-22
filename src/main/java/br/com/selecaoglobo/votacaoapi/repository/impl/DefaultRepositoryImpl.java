package br.com.selecaoglobo.votacaoapi.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

public class DefaultRepositoryImpl {

    @Autowired
    private MongoTemplate mongoTemplate;
}
