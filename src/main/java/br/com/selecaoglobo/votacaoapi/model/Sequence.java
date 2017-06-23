package br.com.selecaoglobo.votacaoapi.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Document(collection = "sequence")
@CompoundIndex(name = "index_sequence_name", def = "{'name' : 1}", unique=true)
public class Sequence {

    @Id
    private String name;

    private int counter;

}
