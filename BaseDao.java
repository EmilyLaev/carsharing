package carsharing;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/* Base class for Data Access Objects (DAOs) that connect to a database.
 * Subclasses should implement the getCreateTableSQL() method to provide a 
 * valid SQL CREATE TABLE statement.
 */
public abstract class BaseDao {

    protected Connection connection;

    //calls the createTable() method to create a table in the database
    public BaseDao(Connection connection) {
        this.connection = connection;
        createTable();
    }

    //Creates a table in the database using a Statement object and the getCreateTableSQL() method.
    //If an exception is thrown, it is caught and the stack trace is printed.
    private void createTable() {
        try (Statement statement = connection.createStatement()) {

            statement.executeUpdate(getCreateTableSQL());
            //statement.executeUpdate(dropTableSQL());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected abstract String getCreateTableSQL();

}
