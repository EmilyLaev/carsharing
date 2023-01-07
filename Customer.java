package carsharing;

//Represents a customer of a car-sharing service.
public class Customer {
    private int id;
    private String name;
    private int carId;


    //Constructor that takes three arguments: the customer's identifier, name, and car identifier,
    //and initializes the id, name, and carId fields with these values.
    public Customer(int id, String name, int carId) {
        this.id = id;
        this.name = name;
        this.carId = carId;

    }

    //Constructor that takes two arguments: the customer's identifier and name, and initializes the
    //id and name fields with these values. It also sets the carId field to 0, which indicates that
    //the customer is not currently using any car.
    public Customer(int id, String name) {
        this.id = id;
        this.name = name;
        this.carId = 0;

    }

    //The getters and setters for the customer
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCarId() {
        return carId;
    }


    @Override
    public String toString() {
        return this.getId() + ". " + this.getName();
    }
}
