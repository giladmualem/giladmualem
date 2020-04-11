package fasade;

import org.junit.Test;

import static org.junit.Assert.*;

public class CustomerFacadeTest {

    @Test
    public void login() {
        CustomerFacade customerFacade=new CustomerFacade();
        System.out.println(customerFacade.login("danalevi@gmail.com","1234"));
    }
}