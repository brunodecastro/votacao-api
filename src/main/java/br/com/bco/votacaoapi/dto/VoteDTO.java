package br.com.bco.votacaoapi.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteDTO implements Serializable {
    
    private static final long serialVersionUID = 6593372797001084432L;
    
    private Integer result;
    private String contestSlug; 
    private Integer idCandidate; 
    private String userToken;
    
    public VoteDTO(String contestSlug, Integer idCandidate, String userToken) {
        this.setContestSlug(contestSlug);
        this.setIdCandidate(idCandidate);
        this.setUserToken(userToken);
    }
}
