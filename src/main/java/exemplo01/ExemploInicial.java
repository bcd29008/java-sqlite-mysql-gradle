package exemplo01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;


/**
 * Exemplo didático de como trabalhar com SQLite e Java.
 *
 * Essa classe apresenta um exemplo simples de como fazer uma inserção de registro em uma tabela
 * e como obter todas as linhas de uma tabela com o banco de dados SQLite.
 * <p>
 * Essa classe não segue as melhores práticas para trabalhar com banco de dados relacionais.
 *
 * A organização apresentada nesse projeto não deve ser usado em ambientes de produção.
 *
 */
public class ExemploInicial {
    /**
     * Localização onde ficará o banco de dados SQLite
     */
    private final static String dbPath = "src/main/resources/lab01.db";


    /**
     * Inserindo um registro no banco
     *
     * @throws Exception toda e qualquer exceção gerada ao lidar com o banco de dados
     */
    public static void inserirAluno() throws Exception{
        /**
         * Configuração do driver JDBC para o SQLite. O jar desse driver foi
         * obtido por meio do gradle (veja arquivo build.gradle)
         */
        Class.forName("org.sqlite.JDBC");

        Connection conexao = DriverManager.getConnection("jdbc:sqlite:" + dbPath);

        Statement stmt = conexao.createStatement();

        String nome = "Aluna Teste2";
        double peso = 90;
        int altura = 176;
        String email = "aluna@teste2.com.br";


        String sql = "INSERT INTO Pessoa (nome, peso, altura, email) VALUES "
                + "("
                + "'" + nome + "',"
                + peso + ","
                + altura + ","
                + "'" + email + "'"
                + ")";

        stmt.executeUpdate(sql);
        stmt.close();
        conexao.close();
    }

    /**
     * Listando todas as colunas e linhas da tabela Pessoa
     * @throws Exception
     */
    public static void listarRegistros() throws Exception{

        /**
         * Configuração do driver JDBC para o SQLite. O jar desse driver foi
         * obtido por meio do gradle (veja arquivo build.gradle)
         */
        Class.forName("org.sqlite.JDBC");
        Connection conexao = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
        Statement stmt = conexao.createStatement();


        stmt = conexao.createStatement();

        String sql = "SELECT * FROM Pessoa";

        ResultSet rs = stmt.executeQuery(sql);


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
        stmt.close();
        conexao.close();
    }

    /**
     * Buscando por uma pessoa específica. O usuário fornece o endereço de
     * email.
     *
     * O código abaixo sofre com problemas de SQL Injection
     *
     *  https://xkcd.com/327/
     *
     *  Se o usuário entrar com a String: naosei' OR '1' = '1
     *  ele conseguirá retornar todas as linhas
     *
     * @throws Exception
     */
    public static void buscandoPorPessoaEspecifica() throws Exception{
        /**
         * Configuração do driver JDBC para o SQLite. O jar desse driver foi
         * obtido por meio do gradle (veja arquivo build.gradle)
         */
        Class.forName("org.sqlite.JDBC");
        Connection conexao = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
        Statement stmt = conexao.createStatement();
        Scanner teclado = new Scanner(System.in);

        System.out.print("Entre com o email: ");
        String emailPessoa = teclado.nextLine();

        stmt = conexao.createStatement();

        String sql = "SELECT * FROM Pessoa WHERE Email = '" + emailPessoa + "'";

        System.out.println("Consulta SQL: " + sql + "\n");

        ResultSet rs = stmt.executeQuery(sql);


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
        stmt.close();
        conexao.close();
    }

    public static void main(String[] args) throws Exception {
//        inserirAluno();
        listarRegistros();
//        buscandoPorPessoaEspecifica();
    }


}
