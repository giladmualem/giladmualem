package fasade;

import dao.DAO.CompaniesDAO;
import dao.DAO.CouponsDAO;
import dao.DAO.CustomersDAO;
import dao.DBDAO.CompaniesDBDAO;
import dao.DBDAO.CouponsDBDAO;
import dao.DBDAO.CustomersDBDAO;

public abstract class ClientFacade {
    protected CompaniesDBDAO companiesDBDAO;
    protected CustomersDAO customersDAO;
    protected CouponsDAO couponsDAO;

    public ClientFacade(){
        this.companiesDBDAO = new CompaniesDBDAO();
        this.customersDAO = new CustomersDBDAO();
        this.couponsDAO = new CouponsDBDAO();
    }

    public abstract Boolean login(String email, String password);
}
