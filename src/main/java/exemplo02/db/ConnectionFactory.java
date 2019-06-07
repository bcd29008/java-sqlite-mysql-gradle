package exemplo02.db;

import org.sqlite.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe responsável por criar conexões com o banco
 */
public abstract class ConnectionFactory {
    private static final String dbPath = "src/main/resources/lab01.db";
    private static Connection cnx;

    /**
     * Faz a conexão  e retorna o objeto Connection
     *
     * @return conexão com um banco SQLite
     */
    public static synchronized Connection getDBConnection() {
        try {
            DriverManager.registerDriver(new JDBC());
            cnx = DriverManager.getConnection("jdbc:sqlite:" + dbPath);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cnx;
    }
}
