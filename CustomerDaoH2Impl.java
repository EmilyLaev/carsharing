package carsharing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class CustomerDaoH2Impl extends BaseDao implements CustomerDao {
    // SQL query for creating the CUSTOMER table
    private static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS CUSTOMER(" +
            "ID INT AUTO_INCREMENT PRIMARY KEY, " +
            "NAME VARCHAR(255) NOT NULL UNIQUE, " +
            "RENTED_CAR_ID INT DEFAULT NULL, " +
            "CONSTRAINT fk_car FOREIGN KEY (RENTED_CAR_ID)" +
            "REFERENCES CAR(ID))";
    // SQL queries for inserting a new customer into the CUSTOMER table
    private static final String CREATE_CUSTOMER = "INSERT INTO CUSTOMER (NAME) VALUES(?)";
    private static final String GET_ALL_CUSTOMERS = "SELECT * FROM CUSTOMER";
    private static final String GET_RENTED_CAR = "SELECT * FROM CUSTOMER WHERE ID = ?";
    private static final String GET_ALL_RENTED_CARS = "SELECT * FROM CUSTOMER WHERE RENTED_CAR_ID > 0";
    private static final String RENT_CAR = "UPDATE CUSTOMER SET RENTED_CAR_ID = ? WHERE ID = ?";
    private static final String RETURN_CAR = "UPDATE CUSTOMER SET RENTED_CAR_ID = NULL WHERE ID = ?";
    private static final String GET_CUSTOMER = "SELECT * FROM CUSTOMER WHERE ID = ?";

    //Constructor for CustomerDaoH2Impl
    //@param connection a JDBC Connection object
    public CustomerDaoH2Impl(Connection connection) {
        super(connection);
    }

    //Overrides the getCreateTableSQL method in BaseDao
    //returns the SQL query for creating the CUSTOMER table
    @Override
    protected String getCreateTableSQL() {
        return CREATE_TABLE_SQL;
    }

    //Retrieves all rows from the CUSTOMER table and returns customer
    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new LinkedList<>();
        try (PreparedStatement stmt = connection.prepareStatement(GET_ALL_CUSTOMERS)) {
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                customers.add(new Customer(resultSet.getInt("ID"),
                        resultSet.getString("NAME"),
                        resultSet.getInt("RENTED_CAR_ID")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    //Overrider the getAllRentedCars() and returns cars
    @Override
    public List<Integer> getAllRentedCars() {
        List<Integer> cars = new LinkedList<>();
        List<Customer> customers = new LinkedList<>();
        try (PreparedStatement stmt = connection.prepareStatement(GET_ALL_RENTED_CARS)) {
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                customers.add(new Customer(resultSet.getInt("ID"),
                        resultSet.getString("NAME"),
                        resultSet.getInt("RENTED_CAR_ID")));
            }
            customers.stream().map(c -> c.getCarId()).forEach(cars::add);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    //Overrides createCustomer() updates customer statement
    @Override
    public void createCustomer(String customer) {
        try (PreparedStatement stmt = connection.prepareStatement(CREATE_CUSTOMER)) {
            stmt.setString(1, customer);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Overrides rentCar() to update car statements
    @Override
    public void rentCar(int carId, int customerId) {
        try (PreparedStatement stmt = connection.prepareStatement(RENT_CAR)) {
            stmt.setString(1, String.valueOf(carId));
            stmt.setString(2, String.valueOf(customerId));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //A method to update a returned car
    public void returnCar(int carId, int customerId) {
        try (PreparedStatement stmt = connection.prepareStatement(RETURN_CAR)) {
            stmt.setString(1, String.valueOf(customerId));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Overrides myRentedCarId() and returns car id
    @Override
    public int myRentedCarId(int customerId) {
        try (PreparedStatement stmt = connection.prepareStatement(GET_RENTED_CAR)) {
            stmt.setString(1, String.valueOf(customerId));
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("RENTED_CAR_ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    //Method to retrieve customer id
    public Customer getCustomer(int customerId) {
        try (PreparedStatement stmt = connection.prepareStatement(GET_CUSTOMER)) {
            stmt.setString(1, String.valueOf(customerId));
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                Customer customer = new Customer(resultSet.getInt("ID"),
                        resultSet.getString("NAME"),
                        resultSet.getInt("RENTED_CAR_ID"));
                return customer;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
