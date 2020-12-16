package bcd;

import exemplo05mysql.db.ConnectionFactory;
import org.junit.Test;

import java.sql.Connection;
import static org.junit.Assert.*;


/**
 * Classe para executar teste de unidade sobre a conexão com MySQL
 */
public class TesteConexaoMySQL {

    @Test
    public void testarConexao(){
        Connection conexao = ConnectionFactory.getDBConnection();

        assertNotNull("Não foi possível conectar no servidor MySQL",conexao);
    }


}
