package com.laundry.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/** Flat JSON returned to the client (avoids exposing full entity graphs). */
public class OrderResponse {

    private Long id;
    private Long customerId;
    private String customerName;
    private Long serviceId;
    private String serviceName;
    private BigDecimal unitPrice;
    private Integer quantity;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private LocalDateTime orderDate;

    public static OrderResponse from(LaundryOrder o) {
        OrderResponse r = new OrderResponse();
        r.id = o.getId();
        r.customerId = o.getCustomer().getId();
        r.customerName = o.getCustomer().getName();
        r.serviceId = o.getService().getId();
        r.serviceName = o.getService().getServiceName();
        r.unitPrice = o.getService().getPrice();
        r.quantity = o.getQuantity();
        r.totalAmount = o.getTotalAmount();
        r.status = o.getStatus();
        r.orderDate = o.getOrderDate();
        return r;
    }

    public Long getId() { return id; }
    public Long getCustomerId() { return customerId; }
    public String getCustomerName() { return customerName; }
    public Long getServiceId() { return serviceId; }
    public String getServiceName() { return serviceName; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public Integer getQuantity() { return quantity; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public OrderStatus getStatus() { return status; }
    public LocalDateTime getOrderDate() { return orderDate; }
}
