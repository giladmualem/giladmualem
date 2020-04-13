package fasade;

import dao.DBDAO.CompaniesDBDAO;
import dao.DBDAO.CouponsDBDAO;
import dao.DBDAO.CustomersDBDAO;
import dao.DBDAO.PurchaseDBDAO;

public abstract class ClientFacade {
    protected CompaniesDBDAO companiesDBDAO;
    protected CustomersDBDAO customersDBDAO;
    protected CouponsDBDAO couponsDBDAO;
    protected PurchaseDBDAO purchaseDBDAO;

    public ClientFacade(){
        this.companiesDBDAO = new CompaniesDBDAO();
        this.customersDBDAO = new CustomersDBDAO();
        this.couponsDBDAO = new CouponsDBDAO();
        this.purchaseDBDAO=new PurchaseDBDAO();
    }


    public abstract Boolean login(String email, String password);


}
