package bcd;

import exemplo01.ExemploMuitoSimples;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Classe para executar testes de unidade no Exemplo Simples com SQLite
 */
public class TesteExemplo01 {

    private ExemploMuitoSimples app;

    public TesteExemplo01() {
        this.app = new ExemploMuitoSimples();
    }

    /**
     * Irá apagar a tabela Pessoa e criar novamente com um único registro
     */
    public void iniciaBancoDeDados() {
        try (Connection conexao = DriverManager.getConnection(this.app.getDB_URI());
             Statement statement = conexao.createStatement();) {

            statement.executeUpdate("drop table if exists Pessoa");

            statement.executeUpdate("create table Pessoa ( idPessoa INTEGER not null\n" +
                    " primary key,\n" +
                    " Nome     TEXT    not null,\n" +
                    " Peso     REAL,\n" +
                    " Altura   INTEGER,\n" +
                    " Email    Text)");

            statement.executeUpdate("INSERT INTO Pessoa (idPessoa, Nome, Peso, Altura, Email) " +
                    "VALUES (1, 'Aluno Teste', 85.2, 180, 'aluno@teste.com.br')");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void listarRegistros() {
        String registros = this.app.listarRegistros();
        assertFalse(registros.equals(""), "Banco sem registros iniciais");
        Logger.getLogger(TesteExemplo01.class.getName()).log(Level.INFO, "\n" + registros);
    }

    @Test
    public void incluirRegistro() {
        int resultado = this.app.cadastrarPessoa("Juca", 71, 174, "juca@email.com");
        assertEquals(1, resultado);
    }

    @Test
    /**
     * Esse teste irá apagar a tabela Pessoa e iniciar a mesma somente com um registro.
     */
    public void alterarRegistro() {
        int resultado = this.app.alterarDadosPessoa(1, "Novo nome", 82, 180, "aluno@teste.com.br");
        assertEquals(1, resultado);
        this.iniciaBancoDeDados();
    }

    @Test
    /**
     * Esse teste irá apagar a tabela Pessoa e iniciar a mesma somente com um registro.
     */
    public void excluirRegistro() {
        this.iniciaBancoDeDados();
        assertEquals(1, this.app.excluirPessoa(1));
        this.iniciaBancoDeDados();
    }
}
