package carsharing;

import java.util.List;

//Interface for interacting with a database that stores information about cars in a car-sharing service.
//Returns a list of Car objects that belong to a specific company, identified by the companyId argument.
public interface CarDao {
    List<Car> getCars(int companyId);

    void createCar(String name, int companyId);

    Car getCar(int carId);
}
