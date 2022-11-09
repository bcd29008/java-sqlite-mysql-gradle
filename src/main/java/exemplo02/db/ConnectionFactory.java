package exemplo02.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.sqlite.SQLiteConfig;

/**
 * Classe responsável por criar conexões com o banco
 */
public abstract class ConnectionFactory {
    private static final String DB_URI = "jdbc:sqlite:src/main/resources/lab01.sqlite";
    private static Connection cnx;
    private static SQLiteConfig sqLiteConfig = new SQLiteConfig();

    /**
     * Faz a conexão  e retorna o objeto Connection
     *
     * @return conexão com um banco SQLite
     */
    public static synchronized Connection getDBConnection() {
        // Para ativar a restrição de chave estrangeira (e.g. PRAGMA foreign_keys = ON;)
        // https://www.sqlite.org/foreignkeys.html
        sqLiteConfig.enforceForeignKeys(true);
        try {
            cnx = DriverManager.getConnection(DB_URI, sqLiteConfig.toProperties());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cnx;
    }
}
