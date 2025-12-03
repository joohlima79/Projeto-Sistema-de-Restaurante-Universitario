package sistema.servicos;

import sistema.interfaces.Gerenciavel;
import sistema.modelos.pessoa.usuario.Usuario;
import java.util.*;

/**
 * @author Joanderson Borges de Lima
 * @version 1.0
 */
public class UsuarioServicos implements Gerenciavel<Usuario>{

    private Map<String, Usuario> usuarios;

    public UsuarioServicos() {
        this.usuarios = new HashMap<>();
    }

    @Override
    public boolean adicionar(Usuario usuario) {
        if (usuario == null || usuario.getLogin() == null) {
            return false;
        }

        if (usuarios.containsKey(usuario.getLogin())) {
            System.out.println("Login já existe!");
            return false;
        }

        usuarios.put(usuario.getLogin(), usuario);
        System.out.println("✓ Usuário cadastrado: " + usuario.getNome());
        return true;
    }

    @Override
    public boolean remover(String login) {
        if (usuarios.remove(login) != null) {
            System.out.println("✓ Usuário removido!");
            return true;
        }
        return false;
    }

    @Override
    public Usuario buscar(String login) {
        return usuarios.get(login);
    }

    @Override
    public boolean atualizar(Usuario usuario) {
        if (usuario == null || !usuarios.containsKey(usuario.getLogin())) {
            return false;
        }

        usuarios.put(usuario.getLogin(), usuario);
        return true;
    }

    @Override
    public List<Usuario> listarTodos() {
        return new ArrayList<>(usuarios.values());
    }

    /**
     * @param login login
     * @param senha senha
     * @return usuário autenticado ou null
     */
    public Usuario autenticar(String login, String senha) {
        Usuario usuario = buscar(login);
        if (usuario != null && usuario.autenticar(login, senha)) {
            return usuario;
        }
        return null;
    }
}