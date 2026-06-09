package br.com.feira.api.exception;

/**
 * Exceção de negócio genérica da aplicação.
 * Lançada quando uma regra de negócio é violada.
 * Mapeada para HTTP 422 (Unprocessable Entity) pelo GlobalExceptionHandler.
 */
public class RegraNegocioException extends RuntimeException {

    public RegraNegocioException(String mensagem) {
        super(mensagem);
    }

    public RegraNegocioException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
