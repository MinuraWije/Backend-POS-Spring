package org.example.backendpossystemspring.controller;

import org.example.backendpossystemspring.customStatusCodes.SelectedCustomerItemOrderErrorStatus;
import org.example.backendpossystemspring.dto.CustomerStatus;
import org.example.backendpossystemspring.dto.impl.Customer;
import org.example.backendpossystemspring.exception.CustomerNotFoundException;
import org.example.backendpossystemspring.exception.DataPersistException;
import org.example.backendpossystemspring.service.CustomerService;
import org.example.backendpossystemspring.util.RegexProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/notes")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveCustomer(@RequestBody Customer customerDTO){
        try{
            customerService.saveCustomer(customerDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @GetMapping(value = "/{customerId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public CustomerStatus getSelectedCustomer(@PathVariable ("customerId") String customerId){
        if(!RegexProcess.customerIdMatcher(customerId)){
            return new SelectedCustomerItemOrderErrorStatus(1,"Customer id is not valid");
        }
        return customerService.getCustomer(customerId);
    }
    public List<Customer> getAllCustoemers(){
        return null;
    }
    @DeleteMapping(value = "{/customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable ("customerId") String customerId){
        try{
            if(!RegexProcess.customerIdMatcher(customerId)){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            customerService.deleteCustomer(customerId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch(CustomerNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @PutMapping(value = "{/customerId}")
    public ResponseEntity<Void> updateCustomer(@PathVariable ("customerId") String customerId,@RequestBody Customer updatedCustomerDTO){
        //validations
        try {
            if(!RegexProcess.customerIdMatcher(customerId) || updatedCustomerDTO == null){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            customerService.updateCustomer(customerId,updatedCustomerDTO);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
