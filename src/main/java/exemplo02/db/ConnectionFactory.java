package exemplo02.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe responsável por criar conexões com o banco
 */
public abstract class ConnectionFactory {
    private static final String DB_URI = "jdbc:sqlite:src/main/resources/lab01.sqlite";
    private static Connection cnx;

    /**
     * Faz a conexão  e retorna o objeto Connection
     *
     * @return conexão com um banco SQLite
     */
    public static synchronized Connection getDBConnection() {
        try {
            cnx = DriverManager.getConnection(DB_URI);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cnx;
    }
}
