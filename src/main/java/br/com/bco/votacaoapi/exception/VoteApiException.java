package br.com.bco.votacaoapi.exception;

public class VoteApiException extends Exception {

    private static final long serialVersionUID = -7145713755218882254L;

    public VoteApiException() {
    }

    public VoteApiException(String message) {
        super(message);
    }

    public VoteApiException(Throwable cause) {
        super(cause);
    }

    public VoteApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
