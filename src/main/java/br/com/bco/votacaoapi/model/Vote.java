package br.com.bco.votacaoapi.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "vote")
@CompoundIndexes({
    @CompoundIndex(name = "index_vote_contest", def = "{'contest_slug' : 1}"),
    @CompoundIndex(name = "index_vote_contest_candidate", def = "{'contest_slug' : 1, 'id_candidate' : 1}", unique=true)
})    
public class Vote implements Serializable {
    
    private static final long serialVersionUID = -2720782253962835814L;

    @Id
    private String id;
    
    @NotNull(message = "A propriedade 'result' é obrigatória.")
    private Integer result;
    
    @NotNull(message = "A propriedade 'contest_slug' é obrigatória.")
    @Field("contest_slug")
    private String contestSlug; 
    
    @NotNull(message = "A propriedade 'id_candidate' é obrigatória.")
    @Field("id_candidate")
    private Integer idCandidate; 
    
    
    public Vote(String contestSlug, Integer idCandidate) {
        this.setContestSlug(contestSlug);
        this.setIdCandidate(idCandidate);
    }
    
    public Vote(Integer result, String contestSlug, Integer idCandidate) {
        this.setResult(result);
        this.setContestSlug(contestSlug);
        this.setIdCandidate(idCandidate);
    }
    
}
