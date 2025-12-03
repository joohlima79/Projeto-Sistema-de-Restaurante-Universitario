package sistema.modelos.pagamento;

/**
 * @author Joanderson Borges de Lima
 * @version 1.0
 */
public class Cartao extends FormaPagamento  {

    private String numeroCartao;
    private String cvv;
    private int parcelas;
    private TipoCartao tipo;

    public enum TipoCartao { CREDITO, DEBITO }

    public Cartao(double valor, String numeroCartao, String cvv,
                  int parcelas, TipoCartao tipo) {
        super(valor);
        this.numeroCartao = numeroCartao;
        this.cvv = cvv;
        this.parcelas = parcelas;
        this.tipo = tipo;
    }

    public Cartao(double valor, String numeroCartao, String cvv) {
        this(valor, numeroCartao, cvv, 1, TipoCartao.DEBITO);
    }

    @Override
    public boolean processar() {
        if (validarPagamento()) {
            processado = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean validarPagamento() {
        boolean numeroValido = numeroCartao != null && numeroCartao.length() == 16;
        boolean cvvValido = cvv != null && cvv.length() == 3;
        boolean parcelasValidas = parcelas > 0 && parcelas <= 12;

        if (tipo == TipoCartao.DEBITO) {
            parcelasValidas = parcelas == 1;
        }

        return numeroValido && cvvValido && parcelasValidas;
    }

    @Override
    public double obterValorTotal() {
        if (tipo == TipoCartao.CREDITO && parcelas > 1) {
            double taxaJuros = (parcelas - 1) * 0.02;
            return valor * (1 + taxaJuros);
        }
        return valor;
    }

    /**
     * @return valor da parcela
     */
    public double calcularValorParcela() {
        return obterValorTotal() / parcelas;
    }

    @Override
    public void exibirComprovante() {
        System.out.println("\n COMPROVANTE CARTÃO ");
        System.out.println("Tipo: " + tipo);
        System.out.println("Número: **** **** **** " + numeroCartao.substring(12));
        System.out.println("Valor: R$ " + String.format("%.2f", valor));
        if (parcelas > 1) {
            System.out.println(String.format("%dx de R$ %.2f", parcelas, calcularValorParcela()));
            System.out.println("Total com juros: R$ " + String.format("%.2f", obterValorTotal()));
        }
        System.out.println("Status: APROVADO");
        System.out.println("_____________________________________\n");
    }

    public int getParcelas() { return parcelas; }
    public TipoCartao getTipo() { return tipo; }
}
