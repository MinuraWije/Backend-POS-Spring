package org.example.backendpossystemspring.service;

import org.example.backendpossystemspring.dto.CustomerStatus;
import org.example.backendpossystemspring.dto.impl.Customer;

import java.util.List;

public interface CustomerService {
    void saveCustomer(Customer customerDTO);
    List<Customer> getAllCustomers();
    CustomerStatus getCustomer(String customerDTO);
    void deleteCustomer(String customerId);
    void updateCustomer(String userID,Customer customerDTO);
    List<String> findSuggestions(String trim);

}
