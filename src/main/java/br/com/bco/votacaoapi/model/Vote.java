package br.com.bco.votacaoapi.model;

import javax.validation.constraints.NotNull;

import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.domain.Persistable;
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
public class Vote implements Persistable<String> {
    
    private static final long serialVersionUID = -2720782253962835814L;

    @Id
    private String id;
    
    @NotNull(message = "A propriedade 'result' é obrigatória.")
    private Integer result;
    
//    @JsonIgnore
    @NotNull(message = "A propriedade 'contest_slug' é obrigatória.")
    @Field("contest_slug")
    private String contestSlug; 
    
//    @JsonIgnore
    @NotNull(message = "A propriedade 'id_candidate' é obrigatória.")
    @Field("id_candidate")
    private Integer idCandidate; 
    
    @Version
    private Long version;
    
    @CreatedDate
    private DateTime createdDate;
    
    @LastModifiedDate
    private DateTime lastModifiedDate;
    
    public Vote(Integer result, String contestSlug, Integer idCandidate) {
        this.setResult(result);
        this.setContestSlug(contestSlug);
        this.setIdCandidate(idCandidate);
    }

    @Override
    public boolean isNew() {
        return createdDate == null;
    }
    
}
