CREATE TABLE wahiya.drug (
 drug_id uuid PRIMARY KEY,
 name varchar(100),
 description varchar(250),
 other_names varchar(100),
 prescribe_for varchar(300),
 unit_type varchar(20), -- tablet, capsule
 image_url varchar(200)
);