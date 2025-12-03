package sistema.servicos;

import sistema.modelos.compra.Compra;
import sistema.modelos.pessoa.usuario.Usuario;
import java.util.*;

/**
 * @author Joanderson Borges de Lima
 * @version 1.0
 */
public class CompraServicos {

    private Map<String, Compra> compras;

    public CompraServicos() {
        this.compras = new HashMap<>();
    }

    public boolean registrar(Compra compra) {
        compras.put(compra.getId(), compra);
        return true;
    }

    public List<Compra> buscarPorUsuario(Usuario usuario) {
        return compras.values().stream()
                .filter(c -> c.getUsuario().equals(usuario))
                .toList();
    }

    public List<Compra> listarTodas() {
        return new ArrayList<>(compras.values());
    }

    public void exibirHistorico(Usuario usuario) {
        List<Compra> historico = buscarPorUsuario(usuario);

        System.out.println("\n ___________________________________________ ");
        System.out.println("  |       HISTÓRICO DE COMPRAS                |");
        System.out.println("  |___________________________________________|");

        if (historico.isEmpty()) {
            System.out.println("Nenhuma compra realizada.");
        } else {
            historico.forEach(Compra::exibirResumo);
        }
        System.out.println("_____________________________________________\n");

    }
}