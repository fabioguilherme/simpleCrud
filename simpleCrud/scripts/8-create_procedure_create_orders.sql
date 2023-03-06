CREATE PROCEDURE copyOrderForItemAndUser(times INT, userId BIGINT, itemId BIGINT, quantity INT)
BEGIN
    DECLARE i int unsigned default 0;
    WHILE i < times do
      INSERT INTO simple_crud.simple_crud_order (item_id,user_id,quantity,creation_date,order_status) VALUES (itemId, userId, quantity, CURRENT_TIMESTAMP, 'DRAFT');
     set i = i + 1;
    END WHILE;
END;