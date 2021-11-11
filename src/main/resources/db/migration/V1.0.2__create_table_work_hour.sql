create table wahiya.work_hour (
    id uuid primary key,
    doctor_id uuid,
    day_of_week varchar(255),
    start_time time,
    end_time time
);
