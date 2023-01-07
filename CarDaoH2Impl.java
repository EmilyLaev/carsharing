package carsharing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

//SQL statement for creating a CAR table in the database.
public class CarDaoH2Impl extends BaseDao implements CarDao {
    private static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS CAR" +
            "(ID INT AUTO_INCREMENT PRIMARY KEY, " +
            "NAME VARCHAR(255) NOT NULL UNIQUE, " +
            "COMPANY_ID INT NOT NULL, " +
            "CONSTRAINT fk_company FOREIGN KEY (COMPANY_ID)" +
            "REFERENCES COMPANY(ID))";
    
    //SQL query for selecting all cars that belong to a specific company.
    //SQL statement and queries for inserting car information.
    private static final String GET_CARS = "SELECT * FROM CAR WHERE COMPANY_ID = ?";
    private static final String CREATE_CAR = "INSERT INTO CAR (NAME, COMPANY_ID) VALUES(?, ?)";
    private static final String GET_CAR = "SELECT * FROM CAR WHERE ID = ?";
    private static final String GET_CAR_BY_NAME = "SELECT * FROM CAR WHERE NAME = ?";

    public CarDaoH2Impl(Connection connection) {
        super(connection);
    }

    //Overrides the getCreateTableSQL() method from the superclass and returns the CREATE_TABLE_SQL constant.
    @Override
    protected String getCreateTableSQL() {
        return CREATE_TABLE_SQL;
    }

    // Returns a list of Car objects that belong to a specific company, identified by the companyId argument.
    //Uses a PreparedStatement and a ResultSet to execute a SELECT query on the database.
    @Override
    public List<Car> getCars(int companyId) {
        List<Car> companies = new LinkedList<>();
        try (PreparedStatement stmt = connection.prepareStatement(GET_CARS)) {
            stmt.setString(1, String.valueOf(companyId));
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                companies.add(new Car(resultSet.getInt("ID"),
                        resultSet.getString("NAME"),
                        resultSet.getInt("COMPANY_ID")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return companies;
    }

    //Override to create car object with arguments car and id number
    @Override
    public void createCar(String car, int companyId) {

        try (PreparedStatement stmt = connection.prepareStatement(CREATE_CAR)) {
            stmt.setString(1, car);
            stmt.setString(2, String.valueOf(companyId));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Override to get car using the carId number
    @Override
    public Car getCar(int carId) {
        try (PreparedStatement stmt = connection.prepareStatement(GET_CAR)) {
            stmt.setString(1, String.valueOf(carId));
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                Car car = new Car(resultSet.getInt("ID"),
                        resultSet.getString("NAME"),
                        resultSet.getInt("COMPANY_ID"));
                return car;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
