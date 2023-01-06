package carsharing;

import java.util.List;


//This is the interface for a data access object (DAO) so that we can use database operations on the Company
public interface CompanyDao {
    List<Company> getAllCompanies();

    void createCompany(String company);

    Company getCompany(int id);
}
