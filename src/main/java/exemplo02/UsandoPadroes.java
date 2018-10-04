package exemplo02;

import exemplo02.db.ConnectionFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Nessa classe é demonstrado como usar padrões de projeto para acessar banco de dados
 */
public class UsandoPadroes {

    private Connection conexao;

    public UsandoPadroes() {
        this.conexao = ConnectionFactory.getConnection();
    }

    public void closeConnection() {
        try {
            this.conexao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void createTable() {
        Statement stmt = null;
        try {
            stmt = conexao.createStatement();

            String sql = "CREATE TABLE IF NOT EXISTS Pessoa ("
                    + "idPessoa INTEGER PRIMARY KEY NOT NULL,"
                    + " Nome    TEXT NOT NULL, "
                    + " Peso    REAL,  "
                    + " Altura  INTEGER, "
                    + " Email   Text)";

            stmt.executeUpdate(sql);

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void inserirPessoa(String nome, double peso, int altura, String email) {
        Statement stmt = null;
        try {
            stmt = conexao.createStatement();

            String sql = "INSERT INTO Pessoa (nome, peso, altura, email) VALUES "
                    + "("
                    + "'" + nome + "',"
                    + peso + ","
                    + altura + ","
                    + "'" + email + "'"
                    + ")";

            stmt.executeUpdate(sql);

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void listarPessoas() {
        Statement stmt = null;
        try {
            stmt = conexao.createStatement();

            String sql = "SELECT * FROM Pessoa";

            ResultSet rs = stmt.executeQuery(sql);

            System.out.println(String.format("|%-5s|%-25s|%-10s|%-10s|%-25s|", "ID", "Nome", "Peso", "Altura", "Email"));
            System.out.println("-------------------------------------------------------------------------------------");
            while (rs.next()) {
                System.out.println(String.format("|%-5d|%-25s|%-10.2f|%-10d|%-25s|",
                        rs.getInt("idPessoa"),
                        rs.getString("Nome"),
                        rs.getDouble("peso"),
                        rs.getInt("altura"),
                        rs.getString("email")));
            }
            System.out.println("-------------------------------------------------------------------------------------");
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        UsandoPadroes exer02 = new UsandoPadroes();

        exer02.listarPessoas();
        exer02.closeConnection();

    }

}
