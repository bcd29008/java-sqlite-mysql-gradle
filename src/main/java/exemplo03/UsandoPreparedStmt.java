package exemplo03;

import exemplo02.db.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Nessa classe é demonstrado como usar a classe PreparedStatement, pois
 * essa evita os problemas com SQL Injection presentes no exemplo01
 */
public class UsandoPreparedStmt {
    private final String DIVISOR = "---------------------------------------------------------------------------------\n";


    public String listarPessoas() throws SQLException {
        StringBuilder sb = new StringBuilder();

        String query = "SELECT * FROM Pessoa";

        try (Connection conexao = ConnectionFactory.getDBConnection();
             PreparedStatement stmt = conexao.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

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
            throw new SQLException(e);
        }
        return sb.toString();
    }

    public String listarDadosDeUmaPessoa(int idPessoa) throws SQLException {
        StringBuilder sb = new StringBuilder();

        // Ao invés de concatenar o parâmetro idPessoa, coloca-se uma interrogação
        // Essa será preenchida logo abaixo
        String query = "SELECT * FROM Pessoa WHERE idPessoa = ?";

        try (Connection conexao = ConnectionFactory.getDBConnection();
             PreparedStatement stmt = conexao.prepareStatement(query)) {

            // Preenchendo o primeiro campo ? na String query criada acima
            stmt.setInt(1, idPessoa);

            ResultSet rs = stmt.executeQuery();

            if (rs.next() == false) {
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
            rs.close();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return sb.toString();
    }

    public int atualizaEmail(int idPessoa, String email) throws SQLException {
        int totalLinhasModificadas = 0;

        // Os campos com interrogação serão preenchidos abaixo
        String query = "UPDATE Pessoa SET Email = ?  WHERE idPessoa = ?";

        try (Connection conexao = ConnectionFactory.getDBConnection();
             PreparedStatement stmt = conexao.prepareStatement(query)) {

            stmt.setString(1, email);
            stmt.setInt(2, idPessoa);

            totalLinhasModificadas = stmt.executeUpdate();

        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return totalLinhasModificadas;
    }
}
