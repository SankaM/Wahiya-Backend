CREATE TABLE wahiya.dose (
 dose_id uuid PRIMARY KEY,
 doctor_id uuid,
 patient_id uuid,
 drug_id uuid,
 note varchar(300),
 unit_per_dose double,
 dose_count int,
 days int,
 before_after_meal varchar(1),
 attachment_url varchar(200)
);