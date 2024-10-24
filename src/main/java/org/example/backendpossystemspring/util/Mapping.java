package org.example.backendpossystemspring.util;

import org.example.backendpossystemspring.dto.impl.Customer;
import org.example.backendpossystemspring.dto.impl.Item;
import org.example.backendpossystemspring.dto.impl.Order;
import org.example.backendpossystemspring.dto.impl.OrderDetail;
import org.example.backendpossystemspring.entity.impl.CustomerEntity;
import org.example.backendpossystemspring.entity.impl.ItemEntity;
import org.example.backendpossystemspring.entity.impl.OrderEntity;
import org.example.backendpossystemspring.entity.impl.OrderItemEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Mapping {
    @Autowired
    private ModelMapper modelMapper;

    //for customer mapping
    public CustomerEntity toCustomerEntity(Customer customerDTO){
        return modelMapper.map(customerDTO,CustomerEntity.class);
    }

    public Customer toCustomerDTO(CustomerEntity customerEntity){
        return modelMapper.map(customerEntity,Customer.class);
    }

    public List<Customer> asCustomerDTOList(List<CustomerEntity> customerEntities){
        return modelMapper.map(customerEntities, new TypeToken<List<Customer>>() {}.getType());
    }
    //for item mapping
    public Item toItemDTO(ItemEntity itemEntity){
        return modelMapper.map(itemEntity, Item.class);
    }
    public ItemEntity toItemEntity(Item itemDTO){
        return modelMapper.map(itemDTO, ItemEntity.class);
    }
    public List<Item> asItemDTOList(List<ItemEntity> itemEntityList){
        return modelMapper.map(itemEntityList, new TypeToken<List<Item>>() {}.getType());
    }
    public OrderEntity toOrderEntity(Order orderDTO) {
        return modelMapper.map(orderDTO, OrderEntity.class);
    }
    public Order toOrderDTO(OrderEntity orderEntity) {
        return modelMapper.map(orderEntity, Order.class);
    }
    public List<Order> toOrderList(List<OrderEntity> orderEntities) {
        List<Order> orderDTOs = new ArrayList<>();

        for (OrderEntity orderEntity : orderEntities) {
            Order orderDTO = new Order();
            orderDTO.setOrderId(orderEntity.getOrderId());
            orderDTO.setCustomerId(toCustomerDTO(orderEntity.getCustomer()));
            orderDTO.setDate(orderEntity.getOrderDate());
            orderDTO.setTotal(orderEntity.getTotal());
            orderDTO.setDiscount(orderEntity.getDiscount());
            orderDTO.setTotal(orderEntity.getSubTotal());

            List<OrderDetail> orderDetails = new ArrayList<>();

            for (OrderItemEntity orderItemEntity : orderEntity.getOrderItems()) {
                OrderDetail orderDetailDto = new OrderDetail();
                orderDetailDto.setId(orderItemEntity.getId());
                orderDetailDto.setOrderId(orderItemEntity.getOrder().getOrderId());
                orderDetailDto.setItemId(orderItemEntity.getItem().getItemCode());
                orderDetailDto.setQty(orderItemEntity.getQuantity());
                orderDetails.add(orderDetailDto);
            }

            orderDTO.setItems(orderDetails);

            orderDTOs.add(orderDTO);
        }

        return orderDTOs;
    }
}
