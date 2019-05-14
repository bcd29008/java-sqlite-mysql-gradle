package exemplo05mysql.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Singleton responsável por criar conexões com o banco MySQL
 * <p>
 * O arquivo resources/lab02-dml-ddl.sql contém as instruções DML e DDL para criação
 * do banco de dados necessário para esse exemplo. Crie um esquema em uma instalação
 * MySQL / MariaDB e importe o conteúdo desse arquivo.
 * <p>
 * Faça os ajustes de configuração de conexão nas variáveis dentro do Construtor
 * dessa classe.
 * <p>
 * Verifique se sua instalação MySQL permite receber conexões por meio de sockets TCP
 */
public final class ConnectionFactory {

    private static Connection cnx;
    private static ConnectionFactory db;

    private ConnectionFactory() {
        Properties properties = new Properties();

        properties.setProperty("user", "nome-do-usuario");
        properties.setProperty("password", "senha-do-usuario");
        properties.setProperty("useSSL", "false");

        String host = "127.0.0.1";
        String port = "3306";
        String dbname = "lab02";

        String url = "jdbc:mysql://" + host + ":" + port + "/" + dbname;

        try {
            cnx = DriverManager.getConnection(url, properties);
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Faz a conexão em um banco de dados MySQL e retorna o objeto Connection
     *
     * @return conexão com um banco MySQL
     */
    public static synchronized Connection getDBConnection() {
        if (db == null) {
            db = new ConnectionFactory();
        }
        return cnx;
    }

}
