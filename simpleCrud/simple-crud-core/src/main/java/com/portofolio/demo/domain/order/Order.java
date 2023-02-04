package com.portofolio.demo.domain.order;

import com.portofolio.demo.domain.item.Item;
import com.portofolio.demo.domain.user.User;
import com.portofolio.demo.shared.errors.BusinessException;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "simple_crud_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "quantity")
    private int quantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus status;

    protected Order() {
    }

    public Order(Builder builder) {
        this.item = builder.item;
        this.user = builder.user;
        this.creationDate = LocalDateTime.now();
        this.quantity = builder.quantity;
        this.status = OrderStatus.DRAFT;

        validate();
    }

    public Long getId() {
        return id;
    }

    public Item getItem() {
        return item;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public OrderStatus getStatus() {
        return status;
    }

    private void validate() {

        if (this.item == null) {
            throw new BusinessException("Item can not be null");
        }

        if (this.user == null) {
            throw new BusinessException("User can not be null");
        }

        if (this.quantity == 0) {
            throw new BusinessException("Quantity can not be zero");
        }

        if (this.quantity < 0) {
            throw new BusinessException("Quantity can not be bellow zero");
        }
    }

    protected void changeStatus(OrderStatus newStatus) {

        if (newStatus == null) {
            throw new BusinessException("Status can not be null");
        }

        this.status = newStatus;
    }

    public static final class Builder {
        private Item item;
        private User user;
        private int quantity;


        private Builder() {
        }

        public static Builder with() {
            return new Builder();
        }

        public Builder item(Item item) {
            this.item = item;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
