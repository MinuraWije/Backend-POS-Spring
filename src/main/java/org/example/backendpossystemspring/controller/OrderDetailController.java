package org.example.backendpossystemspring.controller;

import org.example.backendpossystemspring.dto.impl.CartItemDto;
import org.example.backendpossystemspring.dto.impl.Item;
import org.example.backendpossystemspring.dto.impl.Order;
import org.example.backendpossystemspring.dto.impl.OrderRequest;
import org.example.backendpossystemspring.service.CustomerService;
import org.example.backendpossystemspring.service.ItemService;
import org.example.backendpossystemspring.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v3/orderdetail")
public class OrderDetailController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private CustomerService customerService;
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<OrderRequest> getOrders() {
        List<Order> orders = orderService.getOrders();
        System.out.println(orders.toString());
        List<OrderRequest> dtos = new ArrayList<>();

        orders.forEach(order -> {
            OrderRequest dto = new OrderRequest();
            dto.setOrderId(order.getOrderId());
            dto.setCustomerId(order.getCustomer().getCustomerId());
            dto.setCustomerName(order.getCustomer().getCustomerName());
            dto.setDate(order.getDate());
            dto.setTotal(order.getTotal());
            dto.setDiscount(order.getDiscount());
            dto.setSubTotal(order.getSubtotal());


            List<CartItemDto> cartItems = new ArrayList<>();
            order.getItems().forEach(orderDetail -> {
                CartItemDto cartItem = new CartItemDto();
                cartItem.setItemCode(orderDetail.getItemId());
                cartItem.setQty(orderDetail.getQty());
                Item item = (Item) itemService.getItemById(orderDetail.getItemId());
                if (item != null) {
                    cartItem.setItemName(item.getItemName());
                    cartItem.setUnitPrice(item.getItemPrice());
                    cartItem.setTotalPrice(cartItem.getUnitPrice() * cartItem.getQty());
                }
                cartItems.add(cartItem);
            });

            dto.setCartItems(cartItems);
            dtos.add(dto);
        });

        return dtos;
    }
}
