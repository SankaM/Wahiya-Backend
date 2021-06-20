CREATE TABLE wahiya.prescription (
 id uuid PRIMARY KEY,
 doctor_id uuid,
 patient_id uuid,
 note varchar(300),
 prescribed_date date,
 attachment_url varchar(200)
);