package exemplo04;

import exemplo04.entities.Pessoa;
import exemplo04.entities.PessoaDAO;

import java.util.Iterator;
import java.util.List;

/**
 * Essa classe apresenta um exemplo de como usar o padrão de projeto Direct Access Object (DAO)
 *
 */
public class UsandoDAO {


    public static void inserirPessoa(){

        Pessoa p = new Pessoa("Juca Teste", 60, 160, "juca@teste.com");

        PessoaDAO.adiciona(p);

    }


    public static void listarPessoas(){
        List<Pessoa> pessoas = PessoaDAO.listarTodas();

        System.out.println("---------------------------------------------------------------------------------");
        System.out.println(String.format("|%-5s|%-25s|%-10s|%-10s|%-25s|", "ID", "Nome", "Peso", "Altura", "Email"));
        System.out.println("---------------------------------------------------------------------------------");

        // Varrendo lista de pessoas
        pessoas.forEach(pessoa -> {
            System.out.println(pessoa);
        });

        System.out.println("---------------------------------------------------------------------------------");


    }


    public static void main(String[] args) {

        //inserirPessoa();

        listarPessoas();


    }
}
