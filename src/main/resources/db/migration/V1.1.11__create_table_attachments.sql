create table wahiya.attachments (
    id uuid primary key,
    doctor_id uuid,
    patient_id uuid,
    attachment_name varchar(100),
    attachment_key varchar
);
