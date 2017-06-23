package br.com.selecaoglobo.votacaoapi.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContestVotesDTO implements Serializable {

    private static final long serialVersionUID = -5121114819326496147L;
    
    private Integer result;
    private String contestSlug; 
}
