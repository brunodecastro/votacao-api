package br.com.bco.votacaoapi.dto;

import br.com.bco.votacaoapi.exception.ErrorMessageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDTO {
    
    private String message;
    private ErrorMessageType type;

}
