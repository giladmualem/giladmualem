package dao.DAO;

import entites.Companies;
import java.util.List;

public interface CompaniesDAO {

    Boolean isCompanyExists(String email, String password);

    void addCompany(Companies company);

    void updateCompany(Companies company);

    void deleteCompany(Long companyId);

    List<Companies> getAllCompanies();

    Companies getOneCompany(Long id);

}
