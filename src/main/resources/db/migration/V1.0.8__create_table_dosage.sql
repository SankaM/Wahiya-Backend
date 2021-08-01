create table wahiya.dosage (
    id uuid primary key,
    prescription_id uuid,
    drug_id uuid,
    treatment_days integer,
    times_per_day integer,
    dosage_rule varchar(255),
    dosage_count double,
    drug_cost double
);
