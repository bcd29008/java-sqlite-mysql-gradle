package bcd;

import exemplo01.ExemploMuitoSimples;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Classe para executar testes de unidade no Exemplo Simples com SQLite
 */
@TestMethodOrder(MethodOrderer.MethodName.class)
public class TesteExemplo01SQLite {

    private ExemploMuitoSimples app;

    public TesteExemplo01SQLite() throws Exception {
        this.app = new ExemploMuitoSimples();
        // Irá apagar a tabela Pessoa e criar novamente com um único registro 
        this.app.criaBancoDeDados();
    }

    
    @Test
    public void testeAincluirRegistro() throws SQLException {
        int resultado = this.app.cadastrarPessoa("Juca", 71, 174, "juca@email.com");
        assertEquals(1, resultado);
    }

    @Test
    public void testeBlistarRegistros() throws SQLException {
        
        String registros = this.app.listarRegistros();
        assertFalse(registros.equals(""), "Banco sem registros iniciais");
        Logger.getLogger(TesteExemplo01SQLite.class.getName()).log(Level.INFO, "\n" + registros);
    }

    @Test
    /**
     * Esse teste irá alterar os dados da pessoa com id = 1
     */
    public void testeDalterarRegistro() throws Exception {
        int resultado = this.app.alterarDadosPessoa(1, "Novo nome", 82, 180, "aluno@teste.com.br");
        assertEquals(1, resultado);
        // retornando o banco para o estado inicial
        this.app.criaBancoDeDados();
    }

    @Test
    /**
     * Esse teste irá apagar a tabela Pessoa e iniciar a mesma somente com um registro.
     */
    public void testeEexcluirRegistro() throws Exception {
        // retornando o banco para o estado inicial
        this.app.criaBancoDeDados();

        assertEquals(1, this.app.excluirPessoa(1));
        
        // retornando o banco para o estado inicial
        this.app.criaBancoDeDados();
    }
}
