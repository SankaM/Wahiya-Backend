create table wahiya.patient (
    id uuid primary key,
    doctor_id uuid,
    first_name varchar(255),
    last_name varchar(255),
    birth_date date,
    gender varchar(255),
    mobile_phone varchar(255) unique,
    health_profile varchar(500),
    nic varchar(255),
    image_url varchar(255),
    user_name varchar(255) unique,
    password varchar(255),
    is_active boolean default true,
    email varchar(255) unique
);
