package com.laundry.order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<LaundryOrder, Long> {

    List<LaundryOrder> findByCustomerId(Long customerId);

    List<LaundryOrder> findByStatus(OrderStatus status);
}
