package carsharing;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Menu {

    public static final Scanner scanner = new Scanner(System.in);

    /**
     * Allows the user to view and create cars for a given company.
     * @param carDaoH2 object for performing operations on the CARS table in the database
     * @param companyDaoH2 object for performing operations on the COMPANIES table in the database
     * @param companyId ID of the company to manage cars for
     */
    public static void managementCar(CarDaoH2Impl carDaoH2, CompanyDaoH2Impl companyDaoH2, int companyId) {
        Scanner scanner = new Scanner(System.in);
        if (companyId > 0) {
            String companyName = companyDaoH2.getAllCompanies().get(companyId - 1).getName();
            System.out.println("'" + companyName + "' company");
        } else {
            return;
        }

        //An infinte for loop to print car list and for user to enter car name
        for (; ; ) {
            if (companyDaoH2.getAllCompanies().size() >= companyId) {
                MenuPrinter.companyMenu();
                switch (userChoice()) {
                    case 0:
                        return;
                    case 1:
                        if (carDaoH2.getCars(companyId).size() == 0) {
                            System.out.println("The car list is empty!\n");
                        } else {
                            System.out.println("Car list:");
                            int id = 1;
                            for (Car car : carDaoH2.getCars(companyId)) {
                                System.out.println(id + ". " + car.getName());
                                id++;
                            }
                            System.out.println();
                        }
                        break;
                    case 2:
                        System.out.println("Enter the car name:");
                        carDaoH2.createCar(scanner.nextLine(), companyId);
                        System.out.println("The car was added!\n");
                        break;
                }
            } else {
                return;
            }
        }
    }

    //This method allows a user to manage companies and cars in a database.
    // The method takes two implementation classes for interfaces, CompanyDao and CarDao,
    // as parameters in order to interact with the database.
    public static void managementCompany(CompanyDaoH2Impl companyDaoH2, CarDaoH2Impl carDaoH2) {
        Scanner scanner = new Scanner(System.in);
        for (; ; ) {
            MenuPrinter.managerMenu();
            switch (userChoice()) {
                case 0:
                    return;
                case 1:
                    if (companyDaoH2.getAllCompanies().size() == 0) {
                        System.out.println("The company list is empty!\n");
                    } else {
                        System.out.println("Choose the company:");
                        companyDaoH2.getAllCompanies()
                                .stream()
                                .forEach(System.out::println);
                        System.out.println("0. Back");
                        managementCar(carDaoH2, companyDaoH2, userChoice());
                    }
                    break;
                case 2:
                    System.out.println("Enter the company name:");
                    companyDaoH2.createCompany(scanner.nextLine());
                    System.out.println("The company was created!\n");
                    break;
            }
        }
    }

    //A method for user to enter ner customer name
    public static void managementCreateCustomer(CustomerDaoH2Impl customerDaoH2) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the customer name:");
        customerDaoH2.createCustomer(scanner.nextLine());
        System.out.println("The customer was added!\n");
    }

    // This method allows a user to manage customers in a database.
    // The method takes three implementation classes for interfaces, CustomerDao, CompanyDao, and CarDao,
    // as parameters in order to interact with the database.
    public static void managementCustomerMenu(CustomerDaoH2Impl customerDaoH2,
                                              CompanyDaoH2Impl companyDaoH2, CarDaoH2Impl carDaoH2) {
        if (customerDaoH2.getAllCustomers().size() == 0) {
            System.out.println("The customer list is empty!\n");
            return;
        } else {
            System.out.println("Choose a customer:");
            customerDaoH2.getAllCustomers()
                    .stream()
                    .forEach(System.out::println);
            System.out.println("0. Back\n");
            Customer customer = customerDaoH2.getCustomer(userChoice());
            managementCustomer(customerDaoH2, companyDaoH2, carDaoH2, customer);
        }
    }

    // This method allows a user to manage customers in a database.
    // The method takes four parameters,three implementation classes for interfaces, CustomerDao, CompanyDao, and CarDao,
    // and a Customer object.
    public static void managementCustomer(CustomerDaoH2Impl customerDaoH2,
                                          CompanyDaoH2Impl companyDaoH2, CarDaoH2Impl carDaoH2, Customer customer) {
        // Enter an infinite loop to continuously prompt the user for a choice
        for (; ; ) {
            MenuPrinter.customerMenu();
            switch (userChoice()) {
                case 0:
                    return;
                    
                // Check if there are any companies in the database
                // If there are no companies, print a message and go back to the beginning of the loop
                case 1:
                    if (companyDaoH2.getAllCompanies().size() == 0) {
                        System.out.println("The company list is empty!\n");
                    } else {
                        if (customer.getCarId() > 0) {
                            System.out.println("You've already rented a car!");
                            break;
                        } else {
                            System.out.println("Choose the company:");
                            companyDaoH2.getAllCompanies()
                                    .stream()
                                    .forEach(System.out::println);
                            System.out.println("0. Back");
                            int companyId = userChoice();
                            if (companyId == 0) {
                                System.out.println();
                                break;
                            }
                            Company company = companyDaoH2.getCompany(companyId);
                            managementRentCar(carDaoH2, customerDaoH2, company, customer);
                        }
                    }
                    break;
                
                // Get the latest version of the customer object from the database
                // Check if the customer has a car rented
                case 2:
                    customer = customerDaoH2.getCustomer(customer.getId());
                    if (customer.getCarId() == 0) {
                        System.out.println("You didn't rent a car!\n");
                    } else {
                        returnCar(customerDaoH2, customer);
                    }
                    break;
                //Get the latest version of the customer object from the database
                case 3:
                    customer = customerDaoH2.getCustomer(customer.getId());
                    if (customer.getCarId() == 0) {
                        System.out.println("You didn't rent a car!\n");
                    } else {
                        Car car = carDaoH2.getCar(customer.getCarId());
                        Company company = companyDaoH2.getCompany(car.getCompanyId());
                        System.out.println("Your rented car:");
                        System.out.println(car.getName());
                        System.out.println("Company:");
                        System.out.println(company.getName());
                        System.out.println();
                    }
            }
        }

    }

    //This method allows a user to rent a car from a specific company in a database.
    // The method takes four parameters: three implementation classes for interfaces, CarDao, CustomerDao,
    // and objects of type Company and Customer.
    // Filter the list of Car objects to exclude any cars that are already rented
    // Check if the filtered list of Car objects is empty
    // If the list is empty, print a message and return
    // If the list is not empty, print out a list of the available cars and allow the user to choose one
    private static void managementRentCar(CarDaoH2Impl carDaoH2,
                                          CustomerDaoH2Impl customerDaoH2,
                                          Company company, Customer customer) {
        List<Integer> rentedCarsId = customerDaoH2.getAllRentedCars();
        List<Car> carList = carDaoH2.getCars(company.getId())
                .stream()
                .filter((c) -> !rentedCarsId.contains(c.getId()))
                .collect(Collectors.toList());
        if (carList.size() == 0) {
            System.out.println("The car list is empty!\n");
            return;
        } else {

            System.out.println("Choose a car:");
            int id = 1;
            for (Car car : carList) {
                System.out.println(id + ". " + car.getName());
                id++;
            }
            System.out.println("0. Back");
            int carId = userChoice();
            if (carId == 0) {
                return;
            } else {

                if (carId > carList.size() + 1 || carId < 0) {
                    return;
                } else {
                    Car car = carList.get(carId - 1);
                    rentCar(customerDaoH2, car, customer);
                }

            }

        }
    }

    // This method allows a user to rent a car in a database.
    // The method takes three parameters: an implementation class for an interface, CustomerDao,
    // an object of type Car, and an object of type Customer.
    private static void rentCar(CustomerDaoH2Impl customerDaoH2, Car car, Customer customer) {
        customerDaoH2.rentCar(car.getId(), customer.getId());
        System.out.println("You rented '" + car.getName() + "'");
        System.out.println();
    }

    // This method allows a user to return a rented car in a database.
    // The method takes two parameters: an implementation class for an interface, CustomerDao, and an object of type Customer.
    private static void returnCar(CustomerDaoH2Impl customerDaoH2, Customer customer) {
        customerDaoH2.returnCar(customer.getCarId(), customer.getId());
        System.out.println("You've returned a rented car!\n");
    }

    // This method returns the next integer inputted by the user using a Scanner object.
    public static int userChoice() {
        return scanner.nextInt();
    }

}
