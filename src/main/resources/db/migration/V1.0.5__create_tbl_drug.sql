CREATE TABLE wahiya.drug (
 drug_id uuid PRIMARY KEY,
 name varchar(100),
 description varchar(250),
 expiry_date date,
 available_units double,
 unit varchar(20), -- tablet, capsule
 unit_price double,
 image_link varchar(200),
 is_available boolean default true
);