package dao;

import entites.Companies;
import exceptions.AlreadyExistException;
import exceptions.NotExistException;

import java.util.List;

public interface CompaniesDAO {

    boolean isCompanyExists(String email, String password);

    Companies addCompany(Companies companies) throws AlreadyExistException;

    void updateCompany(Companies company) throws NotExistException;

    void deleteCompany(long companyId) throws NotExistException;

    List<Companies> getAllCompanies();

    Companies getOneCompany(long id) throws NotExistException;

}
