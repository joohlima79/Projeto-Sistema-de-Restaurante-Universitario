package sistema.servicos;

import sistema.modelos.pessoa.Cliente;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Joanderson Borges de Lima
 * @version 1.0
 */
public class FluxoClienteServicos {

    private Map<String, Cliente> clientes; // CPF -> Cliente
    private List<RegistroAcesso> historicoAcessos;

    public FluxoClienteServicos() {
        this.clientes = new HashMap<>();
        this.historicoAcessos = new ArrayList<>();
    }

    /**
     * @param cpf CPF do visitante
     * @param nome nome completo
     * @return cliente registrado
     */
    public Cliente registrarEntrada(String cpf, String nome) {
        Cliente cliente = clientes.get(cpf);

        if (cliente == null) {
            cliente = new Cliente(cpf, nome);
            clientes.put(cpf, cliente);
            System.out.println("Novo visitante registrado: " + nome);
        } else {
            cliente.registrarAcesso();
            System.out.println("Acesso registrado para: " + nome);
        }

        historicoAcessos.add(new RegistroAcesso(cpf, nome, LocalDateTime.now()));

        return cliente;
    }

    /**
     * @param cpf CPF do cliente
     * @return cliente encontrado ou null
     */
    public Cliente buscarCliente(String cpf) {
        return clientes.get(cpf);
    }

    /**
     * @return lista de clientes
     */
    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes.values());
    }

    /**
     * @return total de visitantes
     */
    public int getTotalVisitantes() {
        return clientes.size();
    }

    /**
     * @return total de acessos
     */
    public int getTotalAcessos() {
        return historicoAcessos.size();
    }

    /**
     * @return lista de visitantes de hoje
     */
    public List<Cliente> getVisitantesHoje() {
        LocalDate hoje = LocalDate.now();
        return historicoAcessos.stream()
                .filter(r -> r.dataHora.toLocalDate().equals(hoje))
                .map(r -> clientes.get(r.cpf))
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * @param limite quantidade de visitantes a retornar
     * @return lista dos mais frequentes
     */
    public List<Cliente> getVisitantesFrequentes(int limite) {
        return clientes.values().stream()
                .sorted((c1, c2) -> Integer.compare(c2.getNumeroAcessos(), c1.getNumeroAcessos()))
                .limit(limite)
                .collect(Collectors.toList());
    }

    /**
     * @param diasInativos dias sem acesso
     * @return quantidade removida
     */
    public int limparRegistrosAntigos(int diasInativos) {
        LocalDateTime limiteData = LocalDateTime.now().minusDays(diasInativos);
        int removidos = 0;

        Iterator<Map.Entry<String, Cliente>> iterator = clientes.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Cliente> entry = iterator.next();
            if (entry.getValue().getDataHoraAcesso().isBefore(limiteData)) {
                iterator.remove();
                removidos++;
            }
        }

        return removidos;
    }

    private static class RegistroAcesso {
        String cpf;
        String nome;
        LocalDateTime dataHora;

        RegistroAcesso(String cpf, String nome, LocalDateTime dataHora) {
            this.cpf = cpf;
            this.nome = nome;
            this.dataHora = dataHora;
        }
    }
}