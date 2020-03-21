package exemplo05mysql;

import exemplo05mysql.db.ConnectionFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Um pequeno exemplo de como fazer uma fábrica de conexões para MySQL.
 * <p>
 * O arquivo resources/lab01-dml-ddl.sql contém as instruções DML e DDL para criação
 * do banco de dados necessário para esse exemplo. Crie um esquema em uma instalação
 * MySQL / MariaDB e importe o conteúdo desse arquivo.
 * <p>
 * Faça os ajustes de configuração de conexão no arquivo db/ConnectionFactory.java
 */
public class ExemploMySQL {

    /**
     * Listando todos as linhas e colunas da tabela Departamento
     */
    public String listarDadosDeTodosDepartamentos() {
        StringBuilder sb = new StringBuilder();

        String query = "SELECT * FROM Departamento";

        try (PreparedStatement stmt = ConnectionFactory.getDBConnection().prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {

                sb.append("------------------------------------------------------\n");
                sb.append(String.format("|%-5s|%-35s|%10s|\n", "ID", "Nome", "Orçamento"));
                sb.append("------------------------------------------------------\n");

                do {
                    int idDepto = rs.getInt("idDepartamento");
                    String dNome = rs.getString("dNome");
                    double orcamento = rs.getDouble("Orcamento");

                    sb.append(String.format("|%-5d|%-35s|%10.2f|\n", idDepto, dNome, orcamento));
                } while (rs.next());
                sb.append("------------------------------------------------------\n");
            } else {
                sb.append("Não há registros no banco de dados\n");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}
