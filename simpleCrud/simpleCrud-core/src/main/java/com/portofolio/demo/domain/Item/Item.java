package com.portofolio.demo.domain.Item;

import com.portofolio.demo.errors.BusinessException;
import jakarta.persistence.*;

@Entity
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    protected Item() {
    }

    private Item(String name) {
        this.name = name;
        validate();
    }

    private void validate() {
        if (this.name == null || this.name.isEmpty()) {
            throw new BusinessException("Field name can not be null");
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static Item withName(String name) {
        return new Item(name);
    }
}
