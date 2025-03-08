create schema if not exists doomerhub;

create table doomerhub.t_post(
    id serial primary key,
    title varchar(50) not null check (length(trim(title)) >= 3),
    description varchar(5000)
);