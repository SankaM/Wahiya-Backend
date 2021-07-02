create table wahiya.inventory_batch (
    id uuid primary key,
    inventory_id uuid,
    unit_buy_price double,
    unit_price_currency varchar(255),
    unit_count double,
    batch_date timestamp,
    expiry_date timestamp
);
