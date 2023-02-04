package com.portofolio.demo.domain.order;

public enum OrderStatus {
    DRAFT(1),
    PROCESSING(2),
    DONE(3);

    private final int position;

    public int getPosition() {
        return position;
    }

    OrderStatus(int position) {
        this.position = position;
    }
}
