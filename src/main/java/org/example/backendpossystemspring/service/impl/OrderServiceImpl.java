package org.example.backendpossystemspring.service.impl;

import org.example.backendpossystemspring.controller.OrderController;
import org.example.backendpossystemspring.dao.CustomerDao;
import org.example.backendpossystemspring.dao.ItemDao;
import org.example.backendpossystemspring.dao.OrderDao;
import org.example.backendpossystemspring.dto.impl.CartItemDto;
import org.example.backendpossystemspring.dto.impl.Order;
import org.example.backendpossystemspring.dto.impl.OrderRequest;
import org.example.backendpossystemspring.entity.impl.CustomerEntity;
import org.example.backendpossystemspring.entity.impl.ItemEntity;
import org.example.backendpossystemspring.entity.impl.OrderEntity;
import org.example.backendpossystemspring.entity.impl.OrderItemEntity;
import org.example.backendpossystemspring.exception.CustomerNotFoundException;
import org.example.backendpossystemspring.exception.DataPersistException;
import org.example.backendpossystemspring.service.OrderService;
import org.example.backendpossystemspring.util.Mapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional

public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private Mapping mapping;
    @Autowired
    private ItemDao itemDao;

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private CustomerDao customerDao;

    @Override
    @Transactional(rollbackFor = {Exception.class, DataPersistException.class})
    public void saveOrder(OrderRequest order) throws DataPersistException {
        List<CartItemDto> cartItems = order.getCartItems();


        try {
            CustomerEntity customer = customerDao.findById(order.getCustomerId()).orElseThrow(()-> new CustomerNotFoundException("Customer not found"));

            OrderEntity newOrder = mapping.toOrderEntity(new Order(
                    order.getOrderId(),
                    mapping.toCustomerDTO(customer),
                    order.getDate(),
                    order.getTotal(),
                    order.getDiscount(),
                    order.getSubTotal(),
                    new ArrayList<>()
            ));

            for (CartItemDto cartItem : cartItems) {
                String itemCode = cartItem.getItemCode();

                // Retrieve the item entity and update the stock
                ItemEntity item = itemDao.findById(itemCode).orElseThrow(() -> new DataPersistException("Item not found: " + itemCode));

                // Decrease the item quantity based on the ordered quantity
                if (item.getItemQuantity() < cartItem.getQty()) {
                    throw new DataPersistException("Insufficient stock for item: " + itemCode);
                }
                item.setItemQuantity(item.getItemQuantity() - cartItem.getQty());
                itemDao.save(item);  // Save the updated item with decreased quantity

                // Create an OrderItemEntity for each item in the cart
                var orderItem = new OrderItemEntity();
                orderItem.setOrder(newOrder);
                orderItem.setItem(item);
                orderItem.setQuantity(cartItem.getQty());
                newOrder.getOrderItems().add(orderItem); // Add the OrderItemEntity to the order
            }

            // Save the order
            OrderEntity savedOrder = orderDao.save(newOrder);

            if (savedOrder == null) {
                throw new DataPersistException("Could not save order");
            }

        } catch (DataPersistException e) {
            throw e;  // Rethrow to trigger rollback
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataPersistException("An error occurred while saving the order");
        }
    }
    @Override
    public List<Order> getOrders() {
        List<OrderEntity> all = orderDao.findAll();
        return mapping.toOrderList(all);
    }

    @Override
    public Order getOrder(String orderId) {
        return null;
    }

    @Override
    public void deleteOrder(String orderId) {

    }

    @Override
    public void updateOrder(Order order, String orderId) {

    }
}
