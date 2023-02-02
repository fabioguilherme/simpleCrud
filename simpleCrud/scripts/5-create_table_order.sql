create table simple_crud_order (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        item_id BIGINT NOT NULL,
        user_id BIGINT NOT NULL,
        quantity int not null,
        creation_date TIMESTAMP,
        order_status VARCHAR(30) NOT NULL,
        FOREIGN KEY (item_id) REFERENCES item(id),
        FOREIGN KEY (user_id) REFERENCES simple_crud_user(id)
);