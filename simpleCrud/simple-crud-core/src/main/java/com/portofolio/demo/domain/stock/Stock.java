package com.portofolio.demo.domain.stock;

import com.portofolio.demo.domain.item.Item;
import com.portofolio.demo.shared.errors.BusinessException;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "stock")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @OneToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private Item item;

    @Column(name = "version")
    @Version
    private Long version;

    protected Stock() {
    }

    private Stock(Builder builder) {

        this.creationDate = LocalDateTime.now();
        this.item = builder.item;
        this.quantity = builder.quantity;

        validate();
    }

    public Long getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Item getItem() {
        return item;
    }

    public Long getVersion() {
        return version;
    }

    private void validate() {

        if (this.item == null) {
            throw new BusinessException("Item can not be null", null);
        }

        if (this.quantity < 0) {
            throw new BusinessException("Quantity can not be bellow zero", null);
        }
    }

    protected void addStock(int quantity) {

        if (quantity < 0) {
            throw new BusinessException("Quantity can not be bellow zero", null);
        }

        this.quantity = this.quantity + quantity;
    }

    protected void subtractStock(int quantity) {

        if (quantity < 0) {
            throw new BusinessException("Quantity can not be bellow zero", null);
        }

        if (this.quantity - quantity < 0) {
            throw new BusinessException("Final quantity result can not be bellow zero", null);
        }


        this.quantity = this.quantity - quantity;
    }


    protected static final class Builder {
        private int quantity;
        private Item item;

        private Builder() {
        }

        public static Builder with() {
            return new Builder();
        }

        public Builder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder item(Item item) {
            this.item = item;
            return this;
        }

        public Stock build() {
            return new Stock(this);
        }
    }
}
