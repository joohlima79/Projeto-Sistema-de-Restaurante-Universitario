package sistema.servicos;

import sistema.interfaces.Gerenciavel;
import sistema.modelos.produto.Produto;
import java.util.*;

/**
 * @author Joanderson Borges de Lima
 * @version 1.0
 */
public class EstoqueServicos implements Gerenciavel<Produto> {

    private Map<String, Produto> produtos;

    public EstoqueServicos() {
        this.produtos = new HashMap<>();
    }

    @Override
    public boolean adicionar(Produto produto) {
        if (produto == null || produto.getCodigo() == null) {
            return false;
        }

        if (produtos.containsKey(produto.getCodigo())) {
            System.out.println("Produto já existe no estoque!");
            return false;
        }

        produtos.put(produto.getCodigo(), produto);
        System.out.println(" Produto adicionado: " + produto.getNome());
        return true;
    }

    @Override
    public boolean remover(String codigo) {
        if (produtos.remove(codigo) != null) {
            System.out.println("Produto removido com sucesso!");
            return true;
        }
        System.out.println("Produto não encontrado!");
        return false;
    }

    @Override
    public Produto buscar(String codigo) {
        return produtos.get(codigo);
    }

    @Override
    public boolean atualizar(Produto produto) {
        if (produto == null || !produtos.containsKey(produto.getCodigo())) {
            System.out.println("Produto não encontrado." +
                    "Não foi possível atualizar o produto.");
            return false;
        }

        produtos.put(produto.getCodigo(), produto);
        System.out.println("Produto atualizado: " + produto.getNome());
        return true;
    }

    @Override
    public List<Produto> listarTodos() {
        return new ArrayList<>(produtos.values());
    }

    public void listarDisponiveis() {
        System.out.println("\n PRODUTOS DISPONÍVEIS ");
        produtos.values().stream()
                .filter(p -> p.getQuantidadeEstoque() > 0)
                .forEach(System.out::println);
        System.out.println("__________________________________________\n");
    }

    /**
     * @param categoria categoria desejada
     * @return lista de produtos
     */
    public List<Produto> buscarPorCategoria(String categoria) {
        return produtos.values().stream()
                .filter(p -> categoria.equals(p.getCategoria()))
                .toList();
    }
}