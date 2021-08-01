create table wahiya.inventory (
    id uuid primary key,
    doctor_id uuid,
    drug_id uuid,
    available_units double,
    unit_sell_price double,
    unit_price_currency varchar(255),
    is_available boolean default true,
    last_updated timestamp
);
