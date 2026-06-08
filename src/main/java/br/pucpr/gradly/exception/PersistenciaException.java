package br.pucpr.gradly.exception;

public class PersistenciaException extends RuntimeException {
    public PersistenciaException(String message) {
        super(message);
    }
}
