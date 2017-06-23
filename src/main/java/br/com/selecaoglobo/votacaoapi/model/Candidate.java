package br.com.selecaoglobo.votacaoapi.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Document(collection = "candidate")
@CompoundIndex(name = "index_candidate_contest", def = "{'contest_slug' : 1}")
public class Candidate {
    
    @Id
    private Integer id;
    
    @NotNull(message = "A propriedade 'name' é obrigatória.")
    @Size(min=3, max=10, message="A propriedade 'name' deve ter de 3 a 10 caracteres.")
    private String name;
    
    @Size(min=0, max=255, message="A propriedade 'description' deve ter de 0 a 255 caracteres.")
    private String avatar;

    @JsonIgnore
    @Field("contest_slug")
    private String contestSlug; 

}
