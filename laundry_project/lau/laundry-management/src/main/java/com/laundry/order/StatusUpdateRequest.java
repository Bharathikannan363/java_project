package com.laundry.order;

import jakarta.validation.constraints.NotNull;

/** JSON body for PUT /api/orders/{id}/status */
public class StatusUpdateRequest {

    @NotNull(message = "status is required")
    private OrderStatus status;

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
}
