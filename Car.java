package carsharing;

//Java bean that represents a car in the car-sharing service
//Unique ID of the car, Name of the car and ID of the company that owns the car.
public class Car {
    private int id;
    private String name;
    private int companyId;


    public Car(int id, String name, int companyId) {
        this.id = id;
        this.name = name;
        this.companyId = companyId;

    }

    //The getters and setters for Id, Name and company id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.getId() + ". " + this.getName();
    }
}
