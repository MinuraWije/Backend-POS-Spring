package org.example.backendpossystemspring.dao;

import org.example.backendpossystemspring.entity.impl.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDao extends JpaRepository<OrderEntity,String> {
}
