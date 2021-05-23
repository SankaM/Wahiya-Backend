CREATE TABLE wahiya.patient (
 patient_id uuid,
 name varchar(50),
 age int,
 doctor_id varchar(30),
 email varchar(40),
 user_name varchar(40),
 birth_date date,
 health_profile varchar(250),
 mobile varchar(20),
 is_active boolean default true,
 image_url varchar(200)
);