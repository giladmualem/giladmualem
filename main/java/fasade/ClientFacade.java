package fasade;

import dao.CompaniesDAO;
import dao.CouponsDAO;
import dao.CustomersDAO;

public abstract class ClientFacade {
    protected CompaniesDAO companiesDAO;
    protected CustomersDAO customersDAO;
    protected CouponsDAO couponsDAO;
    protected Boolean isLogin=false;

    public abstract Boolean login(String email, String password);
}
