create table wahiya.taken_medicine (
    id uuid primary key,
    dosage_id uuid,
    taken_status varchar(255),
    scheduled_taken_date timestamp,
    taken_status_date timestamp
);
