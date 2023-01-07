package carsharing;

import java.util.List;

// Defines methods for interacting with a database of customers of a car-sharing service.
//Returns a list of all the customers in the database.
//Creates a new customer with the given name and adds it to the database
//Marks the car with the given identifier as being available for rent and unlinks it from thecustomer with the given identifier.
public interface CustomerDao {
    List<Customer> getAllCustomers();

    void createCustomer(String name);

    int myRentedCarId(int customerId);

    void rentCar(int carId, int customerId);

    void returnCar(int carId, int customerId);

    Customer getCustomer(int customerId);

    List<Integer> getAllRentedCars();
}
