package br.com.feira.api.exception;

/**
 * Exceção lançada quando um recurso não é encontrado no banco de dados.
 * Mapeada para HTTP 404 (Not Found) pelo GlobalExceptionHandler.
 */
public class RecursoNaoEncontradoException extends RuntimeException {

    private final String recurso;
    private final Object identificador;

    public RecursoNaoEncontradoException(String recurso, Object identificador) {
        super(recurso + " não encontrado(a) com id: " + identificador);
        this.recurso = recurso;
        this.identificador = identificador;
    }

    public String getRecurso() { return recurso; }
    public Object getIdentificador() { return identificador; }
}
