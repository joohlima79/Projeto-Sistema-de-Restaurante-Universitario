package sistema.modelos.compra;

/**
 * @author Joanderson Borges de Lima
 * @version 1.0
 */
public enum StatusCompra {

    PENDENTE("Pendente"),
    CONCLUIDA("Concluída"),
    CANCELADA("Cancelada");

    private final String descricao;

    StatusCompra(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}