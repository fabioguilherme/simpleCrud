package com.portofolio.demo.aplication.item.model;

public class ItemDto {

    private final Long id;
    private final String name;
    private final String uri;

    private ItemDto(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.uri = builder.uri;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUri() {
        return uri;
    }

    public static final class Builder {
        private Long id;
        private String name;
        private String uri;

        private Builder() {
        }

        public static Builder with() {
            return new Builder();
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder uri(String uri) {
            this.uri = uri;
            return this;
        }

        public ItemDto build() {
            return new ItemDto(this);
        }
    }
}
