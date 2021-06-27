CREATE TABLE wahiya.patient (
 id uuid PRIMARY KEY,
 name varchar(50),
 age int,
 doctor_id uuid,
 email varchar(40) UNIQUE,
 user_name varchar(40) UNIQUE,
 birth_date date,
 health_profile varchar(250),
 mobile varchar(20) UNIQUE,
 is_active boolean default true,
 image_url varchar(200)
);