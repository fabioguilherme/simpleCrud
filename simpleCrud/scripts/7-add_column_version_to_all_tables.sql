ALTER TABLE item ADD version NUMERIC(19) DEFAULT 0 NOT NULL;
ALTER TABLE notification ADD version NUMERIC(19) DEFAULT 0 NOT NULL;
ALTER TABLE simple_crud_order ADD version NUMERIC(19) DEFAULT 0 NOT NULL;
ALTER TABLE simple_crud_user ADD version NUMERIC(19) DEFAULT 0 NOT NULL;
ALTER TABLE stock ADD version NUMERIC(19) DEFAULT 0 NOT NULL;