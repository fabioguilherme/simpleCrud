create table notification (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        item_id BIGINT NOT NULL,
        type VARCHAR(50) not null,
        creation_date TIMESTAMP,
        message BLOB NOT NULL,
        FOREIGN KEY (user_id) REFERENCES simple_crud_user(id)
);