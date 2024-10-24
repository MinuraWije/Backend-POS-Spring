package org.example.backendpossystemspring.controller;

import org.example.backendpossystemspring.dto.impl.Order;
import org.example.backendpossystemspring.dto.impl.OrderRequest;
import org.example.backendpossystemspring.exception.DataPersistException;
import org.example.backendpossystemspring.exception.OrderNotFoundException;
import org.example.backendpossystemspring.service.CustomerService;
import org.example.backendpossystemspring.service.ItemService;
import org.example.backendpossystemspring.service.OrderService;
import org.example.backendpossystemspring.util.AppUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v3/order")
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private OrderService orderService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private CustomerService customerService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveOrder(@RequestBody OrderRequest order) {
        System.out.println(order.toString());
        orderService.saveOrder(order);
        try {
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (DataPersistException e){
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value="/orderId",produces = MediaType.APPLICATION_JSON_VALUE)
    public String getOrderId(){
        return AppUtil.generateOrderId();
    }


    @GetMapping(value = "$/{orderId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> getOrderById(@PathVariable("orderId") String orderId){
        try {
            orderService.getOrder(orderId);
            return new ResponseEntity<>(orderService.getOrder(orderId), HttpStatus.OK);
        }catch(OrderNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping(value = "$/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("orderId") String orderId){
        try{
            orderService.deleteOrder(orderId);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(OrderNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,value = "$/{orderId}")
    public ResponseEntity<Void> updateOrder(@RequestBody Order order, @PathVariable("orderId") String orderId){
        try {
            orderService.updateOrder(order,orderId);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(OrderNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/itemsuggest")
    public ResponseEntity<List<String>> suggestItems(@RequestParam("query") String query) {
        try {
            List<String> suggestions = itemService.findSuggestions(query.toLowerCase().trim());
            return ResponseEntity.ok(suggestions);
        } catch (Exception e) {
            logger.error("Error fetching suggestions for query: {}", query, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @GetMapping("/customersuggest")
    public ResponseEntity<List<String>> suggestCustomer(@RequestParam("query") String query) {
        try {
            List<String> suggestions = customerService.findSuggestions(query.toLowerCase().trim());
            return ResponseEntity.ok(suggestions);
        } catch (Exception e) {
            logger.error("Error fetching suggestions for query: {}", query, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
