CREATE TABLE wahiya.inventory (
 inventory_id uuid PRIMARY KEY,
 doctor_id uuid,
 drug_id uuid,
 units_available double,
 expiry_date date,
 last_updated date,
 unit_sell_price double,
 unit_buy_price double,
 unit_price_currency varchar(3)
);