package exemplo04;

import exemplo04.entities.Pessoa;
import exemplo04.entities.PessoaDAO;

import java.sql.SQLException;
import java.util.List;

/**
 * Essa classe apresenta um exemplo de como usar o padrão de projeto Data Access Object (DAO)
 */
public class UsandoDAO {
    private final String DIVISOR = "---------------------------------------------------------------------------------\n";


    public boolean cadastrarPessoa(Pessoa p) throws SQLException {
        return PessoaDAO.adiciona(p);
    }

    public String listarPessoas() throws SQLException {
        List<Pessoa> pessoas = PessoaDAO.listarTodas();

        StringBuilder sb = new StringBuilder();

        sb.append(DIVISOR);
        sb.append(String.format("|%-5s|%-25s|%-10s|%-10s|%-25s|\n", "ID", "Nome", "Peso", "Altura", "Email"));
        sb.append(DIVISOR);

        // Varrendo lista de pessoas e concatenando no StringBuilder usando lambda
        // https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html
        pessoas.forEach(pessoa -> sb.append(pessoa + "\n"));

        sb.append(DIVISOR);

        return sb.toString();
    }
}
