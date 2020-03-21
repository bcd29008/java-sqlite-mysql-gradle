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


    /**
     * Inserindo um registro no banco
     *
     * @throws Exception toda e qualquer exceção gerada ao lidar com o banco de dados
     */
    public int cadastrarPessoa(String nome, double peso, int altura, String email) throws Exception {
        /**
         * Conexão com o banco de dados
         */
        Connection conexao = DriverManager.getConnection(DB_URI);
        Statement stmt = conexao.createStatement();

        // Concatenando entrada do usuário com a instrução SQL INSERT
        // Essa é uma péssima prática, opte por PreparedStatement apresentado no exemplo 03
        String sql = "INSERT INTO Pessoa (nome, peso, altura, email) VALUES ("
                + "'" + nome + "'," // String deve ficar dentro de apóstrofos. Ex: 'joao'
                + peso + "," // double não precisa de apóstrofos
                + altura + "," // int não precisa de apóstrofos
                + "'" + email + "'"
                + ")";

        // Executando instrução para atualizar a tabela no banco de dados
        int resultado = stmt.executeUpdate(sql);

        // Fechando sessão com o banco
        stmt.close();
        conexao.close();
        // retorna o número de linhas que foram alteradas no banco. No caso, deve-se ser igual 1
        return resultado;
    }

    public int alterarDadosPessoa(int idPessoa, String nome, double peso, int altura, String email) throws SQLException {
        /**
         * Conexão com o banco de dados
         */
        Connection conexao = DriverManager.getConnection(DB_URI);
        Statement stmt = conexao.createStatement();


        // Concatenando entrada do usuário com a instrução SQL UPDATE
        // Essa é uma péssima prática, opte por PreparedStatement apresentado no exemplo 03
        String sql = "UPDATE Pessoa SET nome = '" + nome + "', peso=" + peso + ", altura=" + altura + ", email = '"
                + email + "' WHERE idPessoa=" + idPessoa;

        // Executando instrução para atualizar a tabela no banco de dados
        int resultado = stmt.executeUpdate(sql);

        // Fechando sessão com o banco
        stmt.close();
        conexao.close();
        // retorna o número de linhas que foram alteradas no banco. No caso, deve-se ser igual 1
        return resultado;
    }

    public int excluirPessoa(int idPessoa) throws SQLException {
        /**
         * Conexão com o banco de dados
         */
        Connection conexao = DriverManager.getConnection(DB_URI);
        Statement stmt = conexao.createStatement();

        // Concatenando entrada do usuário com a instrução SQL DELETE
        // Essa é uma péssima prática, opte por PreparedStatement apresentado no exemplo 03
        String sql = "DELETE FROM Pessoa WHERE idPessoa = " + idPessoa;

        // Executando instrução para atualizar a tabela no banco de dados
        int resultado = stmt.executeUpdate(sql);

        return resultado;
    }

    /**
     * Listando todas as colunas e linhas da tabela Pessoa
     *
     * @throws Exception
     */
    public String listarRegistros() throws SQLException {
        /**
         * Conexão com o banco de dados
         */
        Connection conexao = DriverManager.getConnection(DB_URI);
        Statement stmt = conexao.createStatement();

        // Criando instrução SQL
        String sql = "SELECT * FROM Pessoa";

        // As linhas resultantes da instrução são colocadas no objeto da classe ResultSet
        ResultSet rs = stmt.executeQuery(sql);

        // Criando objeto para guardar o resultado
        StringBuilder sb = new StringBuilder();

        if (rs.next() == false) {
            sb.append("\nNenhuma pessoa cadastrada no banco\n");
        } else {
            // Formantado a saída para melhorar a apresentação para o usuário
            sb.append("---------------------------------------------------------------------------------\n");
            sb.append(String.format("|%-5s|%-25s|%-10s|%-10s|%-25s|\n", "ID", "Nome", "Peso", "Altura", "Email"));
            sb.append("---------------------------------------------------------------------------------\n");

            // Percorrendo todas as linhas resultantes da consulta SQL
            do {
                sb.append(String.format("|%-5d|%-25s|%-10.2f|%-10d|%-25s|\n",//formatando String para melhorar apresentação
                        rs.getInt("idPessoa"),// é necessário saber o nome da coluna e o domínio dos dados
                        rs.getString("Nome"),// é necessário saber o nome da coluna e o domínio dos dados
                        rs.getDouble("peso"),// é necessário saber o nome da coluna e o domínio dos dados
                        rs.getInt("altura"),// é necessário saber o nome da coluna e o domínio dos dados
                        rs.getString("email")));
            } while (rs.next());
            sb.append("---------------------------------------------------------------------------------");
        }
        // É necessário fechar a sessão com o banco de dados
        rs.close();
        stmt.close();
        conexao.close();

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
     * @throws Exception
     */
    public String listarDadosPessoa(String emailPessoa) throws SQLException {

        /**
         * Conexão com o banco de dados
         */
        Connection conexao = DriverManager.getConnection(DB_URI);
        Statement stmt = conexao.createStatement();
        ResultSet rs = null;

        // Criando objeto para guardar o resultado
        StringBuilder sb = new StringBuilder();

        try {

            stmt = conexao.createStatement();

            // Montando uma consulta SQL concatenando com o parâmetro recebido
            // Essa é uma péssima prática, o ideal é fazer uso de PreparedStatement
            String sql = "SELECT * FROM Pessoa WHERE Email = '" + emailPessoa + "'";

            rs = stmt.executeQuery(sql);

            if (rs.next() == false) {
                sb.append("\nNenhuma pessoa cadastrada possui o email informado\n");
            } else {
                sb.append("---------------------------------------------------------------------------------\n");
                sb.append(String.format("|%-5s|%-25s|%-10s|%-10s|%-25s|\n", "ID", "Nome", "Peso", "Altura", "Email"));
                sb.append("---------------------------------------------------------------------------------\n");
                do {
                    sb.append(String.format("|%-5d|%-25s|%-10.2f|%-10d|%-25s|\n",
                            rs.getInt("idPessoa"),
                            rs.getString("Nome"),
                            rs.getDouble("peso"),
                            rs.getInt("altura"),
                            rs.getString("email")));
                } while (rs.next());
                sb.append("---------------------------------------------------------------------------------\n");
            }
            // É necessário fechar a sessão com o banco de dados
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conexao != null) {
                conexao.close();
            }
        }
        return sb.toString();
    }



}
