package exemplo02;

import exemplo02.db.ConnectionFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Nessa classe é demonstrado como usar padrões de projeto para acessar banco de dados
 */
public class PadroesDeProjeto {

    private final String DIVISOR = "---------------------------------------------------------------------------------\n";

    public String listarPessoas() {

        // Criando objeto para guardar o resultado
        StringBuilder sb = new StringBuilder();
        String sql = "SELECT * FROM Pessoa";

        // Try-with-resources irá fechar automaticamente a Conexão, o Statement e o ResultSet
        // Documentação oficial: https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
        try (Connection conexao = ConnectionFactory.getDBConnection();
             Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (!rs.next()) {
                sb.append("\nNenhuma pessoa cadastrada no banco\n");
            } else {
                // Formantado a saída para melhorar a apresentação para o usuário
                sb.append(DIVISOR);
                sb.append(String.format("|%-5s|%-25s|%-10s|%-10s|%-25s|\n", "ID", "Nome", "Peso", "Altura", "Email"));
                sb.append(DIVISOR);

                // Percorrendo todas as linhas resultantes da consulta SQL
                do {
                    sb.append(String.format("|%-5d|%-25s|%-10.2f|%-10d|%-25s|\n",//formatando String para melhorar apresentação
                            rs.getInt("idPessoa"),// é necessário saber o nome da coluna e o domínio dos dados
                            rs.getString("Nome"),// é necessário saber o nome da coluna e o domínio dos dados
                            rs.getDouble("peso"),// é necessário saber o nome da coluna e o domínio dos dados
                            rs.getInt("altura"),// é necessário saber o nome da coluna e o domínio dos dados
                            rs.getString("email")));
                } while (rs.next());
                sb.append(DIVISOR);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
