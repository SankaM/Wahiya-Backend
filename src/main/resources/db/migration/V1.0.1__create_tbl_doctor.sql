CREATE TABLE wahiya.doctor (
 doctor_id uuid primary key,
 name varchar(50),
 user_name varchar(20),
 password varchar(64),
 email varchar(40),
 location varchar(40),
 address_1 varchar(150),
 address_2 varchar(150),
 address_3 varchar(150),
 zip_code varchar(20),
 mobile_number varchar(20),
 is_active boolean default true,
 profile varchar(300),
 tags varchar(200),
 image_link varchar(200)
);