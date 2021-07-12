create table wahiya.prescription (
    id uuid primary key,
    doctor_id uuid,
    patient_id uuid,
    diagnosis_id uuid,
    illness_severity varchar(255),
    prescription_date timestamp,
    notes varchar(255),
    attachment_url varchar(255),
    doctor_cost double,
    drug_cost double,
    total_cost double
);
