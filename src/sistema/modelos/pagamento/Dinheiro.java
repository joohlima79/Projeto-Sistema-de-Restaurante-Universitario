package sistema.modelos.pagamento;

/**
 * @author Joanderson Borges de Lima
 * @version 1.0
 */
public class Dinheiro extends FormaPagamento{

    private double valorPago;
    private double troco;

    public Dinheiro(double valor, double valorPago) {
        super(valor);
        this.valorPago = valorPago;
        calcularTroco();
    }

    public Dinheiro(double valor) {
        this(valor, valor);
    }

    private void calcularTroco() {
        this.troco = valorPago - valor;
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
        return valorPago >= valor;
    }

    @Override
    public void exibirComprovante() {
        System.out.println("\n COMPROVANTE DINHEIRO ");
        System.out.println("Valor Total: R$ " + String.format("%.2f", valor));
        System.out.println("Valor Pago: R$ " + String.format("%.2f", valorPago));
        System.out.println("Troco: R$ " + String.format("%.2f", troco));
        System.out.println("Status: PAGO");
        System.out.println("_____________________________________\n");
    }

    public double getTroco() { return troco; }
    public double getValorPago() { return valorPago; }
}
