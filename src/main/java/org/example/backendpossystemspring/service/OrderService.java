package org.example.backendpossystemspring.service;

import org.example.backendpossystemspring.dto.impl.Order;
import org.example.backendpossystemspring.dto.impl.OrderRequest;
import org.example.backendpossystemspring.dto.impl.Order;
import org.example.backendpossystemspring.dto.impl.OrderRequest;

import java.util.List;

public interface OrderService {
    void saveOrder(OrderRequest order);
    List<Order> getOrders();
    Order getOrder(String orderId);
    void deleteOrder(String orderId);
    void updateOrder(Order order,String orderId);
}
