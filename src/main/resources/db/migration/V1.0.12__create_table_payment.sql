create table kahiya.payment (
    id int primary key auto_increment,
    appointment_id uuid,
    prescription_id uuid,
    amount double,
    paid boolean,
    generated_date timestamp,
    request_date timestamp,
    result_date timestamp,
    payment_provider varchar(255),
    payment_result_metadata varchar(1000)
);
