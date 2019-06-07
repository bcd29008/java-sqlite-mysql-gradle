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


    /**
     * Método principal
     *
     * @param args
     */
    public static void main(String[] args) {

        UsandoPreparedStmt exer03 = new UsandoPreparedStmt();

//        exer03.listarDadosDeUmaPessoa(1);
//        exer03.atualizaEmail(1, "novo@email.com");

        exer03.listarPessoas();

        exer03.listarPessoas();
    }

    public void listarPessoas() {
        String query = "SELECT * FROM Pessoa";

        try (Connection conexao = ConnectionFactory.getDBConnection();
             PreparedStatement stmt = conexao.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();

            System.out.println("---------------------------------------------------------------------------------");
            System.out.println(String.format("|%-5s|%-25s|%-10s|%-10s|%-25s|", "ID", "Nome", "Peso", "Altura", "Email"));
            System.out.println("---------------------------------------------------------------------------------");
            while (rs.next()) {
                System.out.println(String.format("|%-5d|%-25s|%-10.2f|%-10d|%-25s|",
                        rs.getInt("idPessoa"),
                        rs.getString("Nome"),
                        rs.getDouble("peso"),
                        rs.getInt("altura"),
                        rs.getString("email")));
            }
            System.out.println("---------------------------------------------------------------------------------");
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void listarDadosDeUmaPessoa(int idPessoa) {
        String query = "SELECT * FROM Pessoa WHERE idPessoa = ?";

        try (Connection conexao = ConnectionFactory.getDBConnection();
             PreparedStatement stmt = conexao.prepareStatement(query)) {


            stmt.setInt(1, idPessoa);
            ResultSet rs = stmt.executeQuery();

            System.out.println("---------------------------------------------------------------------------------");
            System.out.println(String.format("|%-5s|%-25s|%-10s|%-10s|%-25s|", "ID", "Nome", "Peso", "Altura", "Email"));
            System.out.println("---------------------------------------------------------------------------------");
            while (rs.next()) {
                System.out.println(String.format("|%-5d|%-25s|%-10.2f|%-10d|%-25s|",
                        rs.getInt("idPessoa"),
                        rs.getString("Nome"),
                        rs.getDouble("peso"),
                        rs.getInt("altura"),
                        rs.getString("email")));
            }
            System.out.println("---------------------------------------------------------------------------------");
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizaEmail(int idPessoa, String email) {
        String query = "UPDATE Pessoa SET Email = ?  WHERE idPessoa = ?";

        try (Connection conexao = ConnectionFactory.getDBConnection();
             PreparedStatement stmt = conexao.prepareStatement(query)) {


            stmt.setString(1, email);
            stmt.setInt(2, idPessoa);

            int totalLinhasModificadas = stmt.executeUpdate();
            System.out.println("Total de registros modificados: " + totalLinhasModificadas);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
