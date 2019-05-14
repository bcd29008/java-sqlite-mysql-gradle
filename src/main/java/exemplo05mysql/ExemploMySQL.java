package exemplo05mysql;

import exemplo05mysql.db.ConnectionFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Um pequeno exemplo de como fazer uma fábrica de conexões (singleton) para MySQL.
 * <p>
 * O arquivo resources/lab02-dml-ddl.sql contém as instruções DML e DDL para criação
 * do banco de dados necessário para esse exemplo. Crie um esquema em uma instalação
 * MySQL / MariaDB e importe o conteúdo desse arquivo.
 * <p>
 * Faça os ajustes de configuração de conexão no arquivo db/ConnectionFactory.java
 */
public class ExemploMySQL {


    public static void main(String[] args) {

        ExemploMySQL exemploMySQL = new ExemploMySQL();

        exemploMySQL.listarDadosDeTodosDepartamentos();

    }

    /**
     * Listando todos as linhas e colunas da tabela Departamento
     */
    public void listarDadosDeTodosDepartamentos() {
        String query = "SELECT * FROM Departamento";


        try (PreparedStatement stmt = ConnectionFactory.getDBConnection().prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("------------------------------------------------------");
            System.out.println(String.format("|%-5s|%-35s|%10s|", "ID", "Nome", "Orçamento"));
            System.out.println("------------------------------------------------------");

            while (rs.next()) {
                int idDepto = rs.getInt("idDepartamento");
                String dNome = rs.getString("dNome");
                double orcamento = rs.getDouble("Orcamento");

                System.out.println(String.format("|%-5d|%-35s|%10.2f|", idDepto, dNome, orcamento));
            }
            System.out.println("------------------------------------------------------");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
