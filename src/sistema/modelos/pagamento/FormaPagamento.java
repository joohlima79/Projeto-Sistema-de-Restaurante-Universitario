package sistema.modelos.pagamento;

import sistema.interfaces.Pagavel;

/**
 * @author Joanderson Borges de Lima
 * @version 1.0
 */
public abstract class FormaPagamento implements Pagavel{

    protected double valor;
    protected boolean processado;
    protected String identificador;

    public FormaPagamento(double valor, String identificador) {
        this.valor = valor;
        this.identificador = identificador;
        this.processado = false;
    }

    public FormaPagamento(double valor) {
        this(valor, null);
    }

    @Override
    public double obterValorTotal() {
        return valor;
    }

    public abstract void exibirComprovante();

    public boolean isProcessado() { return processado; }
    public String getIdentificador() { return identificador; }
}