package exemplo04.entities;

import exemplo02.db.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Data Access Object (DAO)
 *
 * Padrão de projetos de forma que se cria um objeto
 * responsável por acessar os dados em um banco. Evita ter um monte de código
 * SQL espalhado por diversas classes no projeto
 *
 *
 * @author Emerson Ribeiro de Mello
 */
public class PessoaDAO {


    public static void adiciona(Pessoa p) {

        // Try-with-resources irá fechar automaticamente a conexão
        try (Connection conexao = ConnectionFactory.getConnection()) {

            String sql = "INSERT INTO Pessoa (nome, peso, altura, email) VALUES (?,?,?,?)";
            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {

                stmt.setString(1, p.getNome());
                stmt.setDouble(2, p.getPeso());
                stmt.setInt(3, p.getAltura());
                stmt.setString(4, p.getEmail());

                stmt.execute();
            }

        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }

    public static List<Pessoa> listarTodas() {
        List<Pessoa> pessoas = new ArrayList<>();

        try (Connection conexao = ConnectionFactory.getConnection()) {
            String sql = "SELECT * from Pessoa";
            try (PreparedStatement stmt = conexao.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Pessoa c = new Pessoa(
                            rs.getString("nome"),
                            rs.getDouble("peso"),
                            rs.getInt("altura"),
                            rs.getString("email"));
                    pessoas.add(c);
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }

        return pessoas;
    }
}
