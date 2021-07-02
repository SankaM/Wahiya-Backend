create table wahiya.doctor (
    id uuid primary key,
    name varchar(255),
    gender varchar(255),
    speciality varchar(255),
    profile varchar(500),
    general_work_hour varchar(255),
    mobile_phone varchar(255) unique,
    email varchar(255) unique,
    address_1 varchar(255),
    address_2 varchar(255),
    address_3 varchar(255),
    zip_code varchar(255),
    image_url varchar(255),
    user_name varchar(255) unique,
    password varchar(255),
    is_active boolean default true,
    doctor_cost double
);
