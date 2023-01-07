package carsharing;

//creates  Java bean that represents a company in a car-sharing service
public class Company {
    private int id;
    private String name;

    //Constructor that takes the company's ID and name as arguments and setsthe respective fields
    public Company(int id, String name) {
        this.id = id;
        this.name = name;
    }

    //getters and setters for company information
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

    @Override
    public String toString() {
        return this.getId() + ". " + this.getName();
    }
}
