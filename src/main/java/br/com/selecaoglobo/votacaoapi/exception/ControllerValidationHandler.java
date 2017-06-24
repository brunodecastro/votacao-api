package br.com.selecaoglobo.votacaoapi.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.selecaoglobo.votacaoapi.dto.ErrorDTO;

/**
 * Captura os erros de argumentos dos Rest Controllers
 * @author bruno.oliveira
 */
@ControllerAdvice
public class ControllerValidationHandler {
    
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorDTO processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        FieldError error = result.getFieldError();

        return this.processError(error);
    }

    /**
     * Processa o erro.
     * @param error
     * @return
     */
    private ErrorDTO processError(FieldError error) {
        ErrorDTO message = null;
        if (error != null) {
            String msg = error.getDefaultMessage();
            message = new ErrorDTO(msg, ErrorMessageType.RestArgumentNotValid);
        }
        LOG.error("Erro de validação: " + message);
        return message;
    }
}
