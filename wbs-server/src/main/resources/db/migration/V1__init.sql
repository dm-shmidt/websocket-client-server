create table if not exists cpu_usage (
    id serial not null primary key,
    date_time timestamp,
    value numeric
)
