create table wahiya.drug (
    id uuid primary key,
    name varchar(255),
    description varchar(255),
    type varchar(255),
    measurement double,
    measurement_unit varchar(255),
    image_url varchar(255)
);
