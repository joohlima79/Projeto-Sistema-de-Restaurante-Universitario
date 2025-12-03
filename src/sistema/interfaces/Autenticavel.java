package sistema.interfaces;

/**
 * @author Joanderson Borges de lima
 * @version 1.0
 */
public interface Autenticavel {

    /**
     * @param login login do usuário
     * @param senha senha do usuário
     * @return true se autenticado com sucesso
     */
    boolean autenticar(String login, String senha);

    /**
     * @param senhaAntiga senha atual
     * @param novaSenha nova senha
     * @return true se alterado com sucesso
     */
    boolean alterarSenha(String senhaAntiga, String novaSenha);
}