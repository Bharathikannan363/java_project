package com.laundry.order;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/** JSON body for POST /api/orders */
public class OrderRequest {

    @NotNull(message = "customerId is required")
    private Long customerId;

    @NotNull(message = "serviceId is required")
    private Long serviceId;

    @NotNull(message = "quantity is required")
    @Min(value = 1, message = "quantity must be at least 1")
    private Integer quantity;

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public Long getServiceId() { return serviceId; }
    public void setServiceId(Long serviceId) { this.serviceId = serviceId; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}
