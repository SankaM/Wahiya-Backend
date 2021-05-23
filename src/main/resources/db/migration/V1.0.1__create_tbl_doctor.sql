CREATE TABLE wahiya.doctor (
 id uuid primary key,
 doctor_id varchar(20),
 name varchar(50),
 user_name varchar(20),
 password varchar(64),
 email varchar(40),
 location varchar(40),
 address varchar(150),
 postal_code varchar(20),
 mobile varchar(20),
 is_active boolean default true,
 profile varchar(300),
 tags varchar(200),
 image_url varchar(200)
);