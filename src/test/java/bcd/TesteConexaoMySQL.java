package bcd;

import exemplo05mysql.db.ConnectionFactory;
import java.sql.Connection;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;


/**
 * Classe para executar teste de unidade sobre a conexão com MySQL
 */
public class TesteConexaoMySQL {

    @Test
    public void testarConexao(){
        Connection conexao = ConnectionFactory.getDBConnection();

        assertNotNull(conexao, "Não foi possível conectar no servidor MySQL");
    }
}
