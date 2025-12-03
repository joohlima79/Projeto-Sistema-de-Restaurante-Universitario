package sistema.interfaces;

/**
 * @author Joanderson Borges de lima
 * @version 1.0
 * @since 2025-11-04
 */
public interface Pagavel {
    /**
     * @return true se o pagamento foi processado com sucesso, false caso contrário
     */
    boolean processar();

    /**
     * @return true se os dados são válidos, false caso contrário
     */
    boolean validarPagamento();

    /**
     * @return valor total do pagamento
     */
    double obterValorTotal();
}
