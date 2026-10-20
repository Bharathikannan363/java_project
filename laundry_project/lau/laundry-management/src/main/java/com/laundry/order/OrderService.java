package com.laundry.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.laundry.catalog.LaundryService;
import com.laundry.catalog.LaundryServiceService;
import com.laundry.common.BadRequestException;
import com.laundry.common.ResourceNotFoundException;
import com.laundry.customer.Customer;
import com.laundry.customer.CustomerService;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final LaundryServiceService laundryServiceService;

    public OrderService(OrderRepository orderRepository,
                        CustomerService customerService,
                        LaundryServiceService laundryServiceService) {
        this.orderRepository = orderRepository;
        this.customerService = customerService;
        this.laundryServiceService = laundryServiceService;
    }

    @Transactional
    public OrderResponse create(OrderRequest request) {
        // Uses the other two modules (throws 404 if id does not exist)
        Customer customer = customerService.findById(request.getCustomerId());
        LaundryService service = laundryServiceService.findById(request.getServiceId());

        LaundryOrder order = new LaundryOrder();
        order.setCustomer(customer);
        order.setService(service);
        order.setQuantity(request.getQuantity());
        // Business logic: total = price x quantity
        order.setTotalAmount(service.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
        order.setStatus(OrderStatus.RECEIVED);
        order.setOrderDate(LocalDateTime.now());

        return OrderResponse.from(orderRepository.save(order));
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> findAll(Long customerId, OrderStatus status) {
        List<LaundryOrder> orders;
        if (customerId != null) {
            orders = orderRepository.findByCustomerId(customerId);
        } else if (status != null) {
            orders = orderRepository.findByStatus(status);
        } else {
            orders = orderRepository.findAll();
        }
        return orders.stream().map(OrderResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public OrderResponse findById(Long id) {
        return OrderResponse.from(getOrder(id));
    }

    @Transactional
    public OrderResponse updateStatus(Long id, OrderStatus newStatus) {
        LaundryOrder order = getOrder(id);
        OrderStatus allowed = order.getStatus().next();
        if (allowed == null) {
            throw new BadRequestException("Order is already " + order.getStatus() + " and cannot change");
        }
        if (newStatus != allowed) {
            throw new BadRequestException("Invalid status change: " + order.getStatus()
                    + " -> " + newStatus + ". Next allowed status is " + allowed);
        }
        order.setStatus(newStatus);
        return OrderResponse.from(orderRepository.save(order));
    }

    @Transactional
    public void delete(Long id) {
        LaundryOrder order = getOrder(id);
        if (order.getStatus() != OrderStatus.RECEIVED) {
            throw new BadRequestException("Only orders in RECEIVED status can be cancelled");
        }
        orderRepository.delete(order);
    }

    private LaundryOrder getOrder(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + id));
    }
}
