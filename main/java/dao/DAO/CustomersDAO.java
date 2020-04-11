package dao.DAO;

import entites.Customer;
import exceptions.AlreadyExistException;
import exceptions.NotExistException;

import java.util.List;

public interface CustomersDAO {

    Boolean isCustomerExists(String email, String password);

    void addCustomer(Customer customer);

    void updateCustomer(Customer customer);

    void deleteCustomer(Long customerID);

    List<Customer> allCustomer();

    Customer getOneCustomer(Long customerID);
}
