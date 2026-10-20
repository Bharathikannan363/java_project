package com.laundry.order;

/**
 * Order life cycle: RECEIVED -> WASHING -> READY -> DELIVERED
 */
public enum OrderStatus {
    RECEIVED,
    WASHING,
    READY,
    DELIVERED;

    /** The only status an order may move to from this one (null if final). */
    public OrderStatus next() {
        int nextOrdinal = ordinal() + 1;
        return nextOrdinal < values().length ? values()[nextOrdinal] : null;
    }
}
