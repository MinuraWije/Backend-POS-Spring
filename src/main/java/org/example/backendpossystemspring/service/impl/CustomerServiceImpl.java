package org.example.backendpossystemspring.service.impl;

import jakarta.transaction.Transactional;
import org.example.backendpossystemspring.customStatusCodes.SelectedCustomerItemOrderErrorStatus;
import org.example.backendpossystemspring.dao.CustomerDao;
import org.example.backendpossystemspring.dto.CustomerStatus;
import org.example.backendpossystemspring.dto.impl.Customer;
import org.example.backendpossystemspring.entity.impl.CustomerEntity;
import org.example.backendpossystemspring.exception.CustomerNotFoundException;
import org.example.backendpossystemspring.exception.DataPersistException;
import org.example.backendpossystemspring.service.CustomerService;
import org.example.backendpossystemspring.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private Mapping mapping;
    @Override
    public void saveCustomer(Customer customerDTO) {
        /*UserEntity savedUser = userDao.save(mapping.toUserEntity(userDTO));
        mapping.touserDTO(savedUser);*/
        CustomerEntity savedCustomer = customerDao.save(mapping.toCustomerEntity(customerDTO));
        if(savedCustomer == null){
            throw new DataPersistException("User not saved.");
        }
    }

    @Override
    public List<Customer> getAllCustomers() {
        List<CustomerEntity> allCustomers = customerDao.findAll();
        return mapping.asCustomerDTOList(allCustomers);
    }

    @Override
    public CustomerStatus getCustomer(String customerId) {
        if(customerDao.existsById(customerId)){
            CustomerEntity selectedCustomer = customerDao.getReferenceById(customerId);
            return mapping.toCustomerDTO(selectedCustomer);
        }else{
            return new SelectedCustomerItemOrderErrorStatus(2, "Customer with id "+ customerId + "not found.");
        }
    }

    @Override
    public void deleteCustomer(String customerId) {
        Optional<CustomerEntity> existedUser = customerDao.findById(customerId);
        if(!existedUser.isPresent()){
            throw new CustomerNotFoundException("User with id "+customerId+" not found");
        }else {
            customerDao.deleteById(customerId);
        }
    }

    @Override
    public void updateCustomer(String customerID, Customer customerDTO) {
        Optional<CustomerEntity> tmpUser = customerDao.findById(customerID);
        if(tmpUser.isPresent()){
            tmpUser.get().setCustomerName(customerDTO.getCustomerName());
            tmpUser.get().setCustomerEmail(customerDTO.getCustomerEmail());
            tmpUser.get().setCustomerAddress(customerDTO.getCustomerAddress());
            tmpUser.get().setCustomerPhone(customerDTO.getCustomerPhone());
        }
    }

    @Override
    public List<String> findSuggestions(String query) {
        List<CustomerEntity> list = customerDao.findAll();

        return list.stream()
                .filter(customerEntity -> customerEntity.getCustomerName().toLowerCase().contains(query.toLowerCase()))
                .map(customerEntity -> String.format(" Name: %s - Address: %s - Phone: %s", customerEntity.getCustomerName(), customerEntity.getCustomerAddress(), customerEntity.getCustomerPhone()))
                .collect(Collectors.toList());
    }
}
