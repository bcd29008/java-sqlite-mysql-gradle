package exemplo04;

import exemplo04.entities.Pessoa;
import exemplo04.entities.PessoaDAO;

import java.util.List;

/**
 * Essa classe apresenta um exemplo de como usar o padrão de projeto Data Access Object (DAO)
 */
public class UsandoDAO {

    public boolean cadastrarPessoa(Pessoa p) {
        return PessoaDAO.adiciona(p);
    }

    public String listarPessoas() {
        List<Pessoa> pessoas = PessoaDAO.listarTodas();

        StringBuilder sb = new StringBuilder();

        sb.append("---------------------------------------------------------------------------------\n");
        sb.append(String.format("|%-5s|%-25s|%-10s|%-10s|%-25s|\n", "ID", "Nome", "Peso", "Altura", "Email"));
        sb.append("---------------------------------------------------------------------------------\n");

        // Varrendo lista de pessoas e concatenando no StringBuilder usando lambda
        // https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html
        pessoas.forEach(pessoa -> sb.append(pessoa + "\n"));

        sb.append("---------------------------------------------------------------------------------\n");

        return sb.toString();
    }
}
