package sistema.interfaces;

import java.util.List;

/**
 * @author Joanderson Borges de lima
 * @version 1.0
 */
public interface Gerenciavel<T> {

    /**
    * @param <T> tipo da entidade a ser gerenciada
    */

    /**
     * @param item entidade a ser adicionada
     * @return true se adicionado com sucesso
     */
    boolean adicionar(T item);

    /**
     * @param id identificador da entidade
     * @return true se removido com sucesso
     */
    boolean remover(String id);

    /**
     * @param id identificador da entidade
     * @return entidade encontrada ou null
     */
    T buscar(String id);

    /**
     * @param item entidade com dados atualizados
     * @return true se atualizado com sucesso
     */
    boolean atualizar(T item);

    /**
     * @return lista de entidades
     */
    List<T> listarTodos();
}
