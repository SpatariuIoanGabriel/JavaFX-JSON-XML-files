package Repository;

import Domain.Identifiable;
import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class DatabaseRepository<T extends Identifiable<U>, U> extends MemoryRepository<T, U> {
    protected final String URL = "jdbc:sqlite:data/lab.db";

    protected String tableName;
    protected Connection conn = null;

    public DatabaseRepository(String tableName) {
        this.tableName = tableName;
    }

    public abstract void getData();

    public void openConnection() throws SQLException {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(URL);
        if (conn == null || conn.isClosed())
            conn = dataSource.getConnection();
    }

    public void closeConnection() throws SQLException {
        conn.close();
    }
}
