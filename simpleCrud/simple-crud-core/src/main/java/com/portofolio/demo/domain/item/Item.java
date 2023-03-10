package com.portofolio.demo.domain.item;

import com.portofolio.demo.shared.errors.BusinessException;
import jakarta.persistence.*;

@Entity
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "version")
    @Version
    private Long version;

    protected Item() {
    }

    private Item(String name) {
        this.name = name;
        validate();
    }

    private void validate() {
        if (this.name == null || this.name.isEmpty()) {
            throw new BusinessException("Field name can not be null", null);
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getVersion() {
        return version;
    }

    protected static Item withName(String name) {
        return new Item(name);
    }
}
