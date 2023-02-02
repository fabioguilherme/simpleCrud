create table stock (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        item_id BIGINT NOT NULL,
        quantity int not null,
        creation_date TIMESTAMP,
        FOREIGN KEY (item_id) REFERENCES item(id)
);