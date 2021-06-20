CREATE TABLE wahiya.dosage (
 id uuid PRIMARY KEY,
 prescription_id uuid,
 drug_id uuid,
 note varchar(300),
 unit_per_dose double,
 dose_count int,
 days int,
 before_after_meal varchar(1)
);