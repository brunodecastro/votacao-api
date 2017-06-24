package br.com.bco.votacaoapi.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidateContestVotesDTO implements Serializable {
    
    private static final long serialVersionUID = -571231284314648050L;
    
    private Integer result;
    private String contestSlug; 
    private Integer idCandidate; 
}
