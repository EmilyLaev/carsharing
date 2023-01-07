package carsharing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

//Concrete implementation of the CompanyDao interface that uses a database connection
//to execute SQL queries and perform CRUD operations on a COMPANY table.
public class CompanyDaoH2Impl extends BaseDao implements CompanyDao {
    // SQL statement for creating a COMPANY table in the database.
    private static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS COMPANY " +
            "(ID INT AUTO_INCREMENT PRIMARY KEY, " +
            " NAME VARCHAR(255) NOT NULL UNIQUE)";
    private static final String GET_ALL_COMPANIES = "SELECT * FROM COMPANY";
    private static final String CREATE_COMPANY = "INSERT INTO COMPANY (NAME) VALUES(?)";
    private static final String GET_COMPANY = "SELECT * FROM COMPANY WHERE ID = ?";

    //Constructor that takes a Connection object and passes it to the superclass's constructor.
    public CompanyDaoH2Impl(Connection connection) {
        super(connection);
    }

    //Overrides the getCreateTableSQL() method from the superclass and returns the CREATE_TABLE_SQL constant.
    @Override
    protected String getCreateTableSQL() {
        return CREATE_TABLE_SQL;
    }

    @Override
    public List<Company> getAllCompanies() {
        List<Company> companies = new LinkedList<>();
        try (PreparedStatement stmt = connection.prepareStatement(GET_ALL_COMPANIES)) {
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                companies.add(new Company(resultSet.getInt("ID"), resultSet.getString("NAME")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return companies;
    }

    //Overrides the getCompany() method and returns the company object
    @Override
    public Company getCompany(int id) {
        try (PreparedStatement stmt = connection.prepareStatement(GET_COMPANY)) {
            stmt.setString(1, String.valueOf(id));
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                Company company = new Company(resultSet.getInt("ID"), resultSet.getString("NAME"));
                return company;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Overrides the createCompany to create a different company with only a name
    @Override
    public void createCompany(String company) {

        if (getAllCompanies().size() == 0) {
            try (PreparedStatement statement = connection.prepareStatement("ALTER TABLE COMPANY ALTER " +
                    "COLUMN ID RESTART WITH ?")) {
                statement.setString(1, "1");
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try (PreparedStatement stmt = connection.prepareStatement(CREATE_COMPANY)) {
            stmt.setString(1, company);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
