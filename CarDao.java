package carsharing;

import java.util.List;

public interface CarDao {
    List<Car> getCars(int companyId);

    void createCar(String name, int companyId);

    Car getCar(int carId);
}
