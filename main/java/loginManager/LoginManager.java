package loginManager;

import fasade.AdminFacade;
import fasade.ClientFacade;
import fasade.CompanyFacade;
import fasade.CustomerFacade;

public class LoginManager {
    //      singelton
    private static LoginManager instance = null;

    private LoginManager() {
    }

    public static LoginManager getInstance() {
        if (instance == null) {
            instance = new LoginManager();
        }
        return instance;
    }
    // this method return the right facade if it approve
    public ClientFacade login(ClientType type, String email, String password) {
        switch (type) {
            case COMPANY:
                CompanyFacade companyFacade = new CompanyFacade();
                if (companyFacade.login(email, password)) {
                    return companyFacade;
                }
                break;
            case CUSTOMER:
                CustomerFacade customerFacade = new CustomerFacade();
                if (customerFacade.login(email, password)) {
                    return customerFacade;
                }
                break;
            case ADMINISTRATOR:
                AdminFacade adminFacade = new AdminFacade();
                if (adminFacade.login(email, password)) {
                    return adminFacade;
                }
                break;
            default:
                return null;
        }
        return null;
    }
}
