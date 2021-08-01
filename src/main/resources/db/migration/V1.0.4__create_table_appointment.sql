create table wahiya.appointment (
    id uuid primary key,
    work_hour_id uuid,
    patient_id uuid,
    appointment_date timestamp,
    status varchar(255)
);
