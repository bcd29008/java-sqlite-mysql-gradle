package exemplo01;

import java.sql.*;

import org.sqlite.SQLiteConfig;

/**
 * Exemplo didático de como trabalhar com SQLite e Java.
 * <p>
 * Essa classe apresenta um exemplo simples de como fazer interação com o banco
 * de dados SQLite.
 * <p>
 * Essa classe não segue as melhores práticas para trabalhar com banco de dados
 * relacionais.
 * <p>
 * A organização apresentada nessa classe não deve ser usada em ambientes de
 * produção.
 */
public class ExemploMuitoSimples {
    /**
     * Localização onde ficará o banco de dados SQLite. O arquivo encontra-se dentro
     * do diretório src/main/resources
     * 
     * Outra opção seria ter um banco somente na memória com a String:
     * "jdbc:sqlite:"
     */
    private String DB_URI = "jdbc:sqlite::resource:lab01.sqlite";

    /**
     * Para enviar configurações na conexão com o SQLite
     */
    private SQLiteConfig sqLiteConfig;

    private final String DIVISOR = "---------------------------------------------------------------------------------\n";

    public ExemploMuitoSimples(String dB_URI) {
        this();
        DB_URI = dB_URI;
    }

    public ExemploMuitoSimples() {
        this.sqLiteConfig = new SQLiteConfig();
        // Para ativar a restrição de chave estrangeira (e.g. PRAGMA foreign_keys = ON;)
        // https://www.sqlite.org/foreignkeys.html
        sqLiteConfig.enforceForeignKeys(true);
    }

    /**
     * Inserindo um registro no banco
     * 
     * @throws SQLException
     */
    public int cadastrarPessoa(String nome, double peso, int altura, String email) throws SQLException {
        int resultado = -1;

        // Try-with-resources irá fechar automaticamente a Conexão, o Statement e o
        // ResultSet
        try (Connection conexao = DriverManager.getConnection(DB_URI, this.sqLiteConfig.toProperties());
                Statement stmt = conexao.createStatement()) {

            // Concatenando entrada do usuário com a instrução SQL INSERT
            // Essa é uma péssima prática, opte por PreparedStatement apresentado no exemplo
            // 03
            String sql = "INSERT INTO Pessoa (nome, peso, altura, email) VALUES ("
                    + "'" + nome + "'," // String deve ficar dentro de apóstrofos. Ex: 'joao'
                    + peso + "," // double não precisa de apóstrofos
                    + altura + "," // int não precisa de apóstrofos
                    + "'" + email + "'"
                    + ")";

            // Executando instrução para atualizar a tabela no banco de dados
            resultado = stmt.executeUpdate(sql);

        } catch (SQLException e) {
            throw new SQLException("Erro ao cadastrar pessoa", e);
        }

        // retorna o número de linhas que foram alteradas no banco. No caso, deve-se ser
        // igual 1
        return resultado;
    }

    public int alterarDadosPessoa(int idPessoa, String nome, double peso, int altura, String email)
            throws SQLException {

        int resultado = -1;

        // Try-with-resources irá fechar automaticamente a Conexão, o Statement e o
        // ResultSet
        try (Connection conexao = DriverManager.getConnection(DB_URI, this.sqLiteConfig.toProperties());
                Statement stmt = conexao.createStatement()) {

            // Concatenando entrada do usuário com a instrução SQL UPDATE
            // Essa é uma péssima prática, opte por PreparedStatement apresentado no exemplo
            // 03
            String sql = "UPDATE Pessoa SET nome = '" + nome + "', peso=" + peso + ", altura=" + altura
                    + ", email = '" + email + "' WHERE idPessoa=" + idPessoa;

            // Executando instrução para atualizar a tabela no banco de dados
            resultado = stmt.executeUpdate(sql);

        } catch (SQLException e) {
            throw new SQLException("Erro ao alterar dados de pessoas", e);
        }

        // retorna o número de linhas que foram alteradas no banco. No caso, deve-se ser
        // igual 1
        return resultado;
    }

    public int excluirPessoa(int idPessoa) throws SQLException {
        int resultado = -1;

        // Try-with-resources irá fechar automaticamente a Conexão, o Statement e o
        // ResultSet
        try (Connection conexao = DriverManager.getConnection(DB_URI, this.sqLiteConfig.toProperties());
                Statement stmt = conexao.createStatement()) {

            // Concatenando entrada do usuário com a instrução SQL DELETE
            // Essa é uma péssima prática, opte por PreparedStatement apresentado no exemplo
            // 03
            String sql = "DELETE FROM Pessoa WHERE idPessoa = " + idPessoa;

            // Executando instrução para atualizar a tabela no banco de dados
            resultado = stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new SQLException("Erro ao excluir pessoas", e);
        }
        return resultado;
    }

    /**
     * Listando todas as colunas e linhas da tabela Pessoa
     * 
     * @throws SQLException
     */
    public String listarRegistros() throws SQLException {
        // Criando objeto para guardar o resultado
        StringBuilder sb = new StringBuilder();

        // Criando instrução SQL
        String sql = "SELECT * FROM Pessoa";

        // Try-with-resources irá fechar automaticamente a Conexão, o Statement e o
        // ResultSet
        try (Connection conexao = DriverManager.getConnection(DB_URI, this.sqLiteConfig.toProperties());
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
                    sb.append(String.format("|%-5d|%-25s|%-10.2f|%-10d|%-25s|\n", // formatando String para melhorar
                                                                                  // apresentação
                            rs.getInt("idPessoa"), // é necessário saber o nome da coluna e o domínio dos dados
                            rs.getString("Nome"), // é necessário saber o nome da coluna e o domínio dos dados
                            rs.getDouble("peso"), // é necessário saber o nome da coluna e o domínio dos dados
                            rs.getInt("altura"), // é necessário saber o nome da coluna e o domínio dos dados
                            rs.getString("email")));
                } while (rs.next());
                sb.append(DIVISOR);
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao listar todas pessoas", e);
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
     * @throws SQLException
     *
     */
    public String listarDadosPessoa(String emailPessoa) throws SQLException {

        // Criando objeto para guardar o resultado
        StringBuilder sb = new StringBuilder();

        // Montando uma consulta SQL concatenando com o parâmetro recebido
        // Essa é uma péssima prática, o ideal é fazer uso de PreparedStatement
        String sql = "SELECT * FROM Pessoa WHERE Email = '" + emailPessoa + "'";

        // Try-with-resources irá fechar automaticamente a Conexão, o Statement e o
        // ResultSet
        // Documentação oficial:
        // https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
        try (Connection conexao = DriverManager.getConnection(DB_URI, this.sqLiteConfig.toProperties());
                Statement stmt = conexao.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

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
            throw new SQLException("Erro ao listar dados de pessoas", e);
        }
        return sb.toString();
    }

    /**
     * Irá apagar a tabela Pessoa e criar novamente com um único registro
     * 
     * @throws Exception
     */
    public boolean criaBancoDeDados() throws Exception {
        try (Connection conexao = DriverManager.getConnection(DB_URI, this.sqLiteConfig.toProperties());
                Statement statement = conexao.createStatement();) {

            statement.executeUpdate("drop table if exists Pessoa");

            statement.executeUpdate("create table Pessoa ( idPessoa INTEGER not null\n" +
                    " primary key AUTOINCREMENT,\n" +
                    " Nome     TEXT    not null,\n" +
                    " Peso     REAL,\n" +
                    " Altura   INTEGER,\n" +
                    " Email    Text)");

            statement.executeUpdate("INSERT INTO Pessoa (Nome, Peso, Altura, Email) " +
                    "VALUES ('Aluno Teste', 85.2, 180, 'aluno@teste.com.br')");
        } catch (Exception e) {
            throw new Exception("Erro ao criar tabelas", e);
        }
        return true;
    }
}