package exemplo01;

import java.sql.*;


/**
 * Exemplo didático de como trabalhar com SQLite e Java.
 * <p>
 * Essa classe apresenta um exemplo simples de como fazer interação com o banco de dados SQLite.
 * <p>
 * Essa classe não segue as melhores práticas para trabalhar com banco de dados relacionais.
 * <p>
 * A organização apresentada nessa classe não deve ser usada em ambientes de produção.
 */
public class ExemploMuitoSimples {
    /**
     * Localização onde ficará o banco de dados SQLite
     */
    private final String DB_URI = "jdbc:sqlite:src/main/resources/lab01.sqlite";

    private final String DIVISOR = "---------------------------------------------------------------------------------\n";

    public String getDB_URI(){
        return this.DB_URI;
    }

    /**
     * Inserindo um registro no banco
     */
    public int cadastrarPessoa(String nome, double peso, int altura, String email) {
        int resultado = -1;

        // Try-with-resources irá fechar automaticamente a Conexão, o Statement e o ResultSet
        // Documentação oficial: https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
        try (Connection conexao = DriverManager.getConnection(DB_URI);
             Statement stmt = conexao.createStatement()) {

            // Concatenando entrada do usuário com a instrução SQL INSERT
            // Essa é uma péssima prática, opte por PreparedStatement apresentado no exemplo 03
            String sql = "INSERT INTO Pessoa (nome, peso, altura, email) VALUES ("
                    + "'" + nome + "'," // String deve ficar dentro de apóstrofos. Ex: 'joao'
                    + peso + "," // double não precisa de apóstrofos
                    + altura + "," // int não precisa de apóstrofos
                    + "'" + email + "'"
                    + ")";

            // Executando instrução para atualizar a tabela no banco de dados
            resultado = stmt.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // retorna o número de linhas que foram alteradas no banco. No caso, deve-se ser igual 1
        return resultado;
    }

    public int alterarDadosPessoa(int idPessoa, String nome, double peso, int altura, String email) {

        int resultado = -1;

        // Try-with-resources irá fechar automaticamente a Conexão, o Statement e o ResultSet
        // Documentação oficial: https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
        try (Connection conexao = DriverManager.getConnection(DB_URI);
             Statement stmt = conexao.createStatement()) {
            // Concatenando entrada do usuário com a instrução SQL UPDATE
            // Essa é uma péssima prática, opte por PreparedStatement apresentado no exemplo 03
            String sql = "UPDATE Pessoa SET nome = '" + nome + "', peso=" + peso + ", altura=" + altura
                    + ", email = '" + email + "' WHERE idPessoa=" + idPessoa;

            // Executando instrução para atualizar a tabela no banco de dados
            resultado = stmt.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // retorna o número de linhas que foram alteradas no banco. No caso, deve-se ser igual 1
        return resultado;
    }

    public int excluirPessoa(int idPessoa) {
        int resultado = -1;

        // Try-with-resources irá fechar automaticamente a Conexão, o Statement e o ResultSet
        // Documentação oficial: https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
        try (Connection conexao = DriverManager.getConnection(DB_URI);
             Statement stmt = conexao.createStatement()) {

            // Concatenando entrada do usuário com a instrução SQL DELETE
            // Essa é uma péssima prática, opte por PreparedStatement apresentado no exemplo 03
            String sql = "DELETE FROM Pessoa WHERE idPessoa = " + idPessoa;

            // Executando instrução para atualizar a tabela no banco de dados
            resultado = stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    /**
     * Listando todas as colunas e linhas da tabela Pessoa
     */
    public String listarRegistros() {
        // Criando objeto para guardar o resultado
        StringBuilder sb = new StringBuilder();

        // Criando instrução SQL
        String sql = "SELECT * FROM Pessoa";

        // Try-with-resources irá fechar automaticamente a Conexão, o Statement e o ResultSet
        // Documentação oficial: https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
        try (Connection conexao = DriverManager.getConnection(DB_URI);
             Statement stmt = conexao.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

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

    /**
     * Buscando por uma pessoa específica. O usuário fornece o endereço de
     * email.
     * <p>
     * O código abaixo sofre com problemas de SQL Injection
     * <p>
     * https://xkcd.com/327/
     * <p>
     * Se o usuário entrar com a String: naosei' OR '1' = '1
     * ele conseguirá retornar todas as linhas
     *
     */
    public String listarDadosPessoa(String emailPessoa){

        // Criando objeto para guardar o resultado
        StringBuilder sb = new StringBuilder();

        // Montando uma consulta SQL concatenando com o parâmetro recebido
        // Essa é uma péssima prática, o ideal é fazer uso de PreparedStatement
        String sql = "SELECT * FROM Pessoa WHERE Email = '" + emailPessoa + "'";

        // Try-with-resources irá fechar automaticamente a Conexão, o Statement e o ResultSet
        // Documentação oficial: https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
        try (Connection conexao = DriverManager.getConnection(DB_URI);
             Statement stmt = conexao.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {


            if (rs.next() == false) {
                sb.append("\nNenhuma pessoa cadastrada possui o email informado\n");
            } else {
                do {
                    sb.append(String.format("d|s|10.2f|d|s",
                            rs.getInt("idPessoa"),
                            rs.getString("Nome"),
                            rs.getDouble("peso"),
                            rs.getInt("altura"),
                            rs.getString("email")));
                } while (rs.next());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}