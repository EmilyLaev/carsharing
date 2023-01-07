package carsharing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Controller class for managing a database connection for a car-sharing service.
public class ControllerH2 {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:./src/carsharing/db/";

    private Connection connection;

    //Creates a connection to the database using the JDBC_DRIVER and DB_URL constants
    //and the specified database name.
    private static Connection createConnection(String dbName) throws SQLException, ClassNotFoundException {
        Class.forName(JDBC_DRIVER);
        return DriverManager.getConnection(DB_URL + dbName);
    }

    //Constructor that takes a database name and creates a connection to the database using the
    //createConnection() method. The connection's auto-commit mode is set to true.
    public ControllerH2(String dbName) {
        try {
            connection = createConnection(dbName);
            connection.setAutoCommit(true);
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }

    //Returns the connection to the database.
    public Connection getConnection() {
        return connection;
    }

    //Closes the connection to the database if it is not null
    public void closeConnection() {
        try {
            if (connection != null) connection.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
}
