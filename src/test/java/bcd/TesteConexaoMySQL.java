package bcd;

import exemplo05mysql.db.ConnectionFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;


/**
 * Classe para executar teste de unidade sobre a conexão com MySQL
 */
public class TesteConexaoMySQL {

    @Test
    public void testarConexao() throws IOException, SQLException{
        Connection conexao = ConnectionFactory.getDBConnection();

        assertNotNull(conexao, "Não foi possível conectar no servidor MySQL");
    }
}
