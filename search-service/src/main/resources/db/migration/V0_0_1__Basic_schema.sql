create schema if not exists doomerhub;

create table doomerhub.t_post(
    id serial primary key,
    c_title varchar(50) not null check (length(trim(c_title)) >= 3),
    c_description varchar(5000)
);