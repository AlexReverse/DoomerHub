create schema if not exists user_management;

create table user_management.t_author_user
(
    id serial primary key,
    username varchar not null check( length(trim(c_username)) > 0) unique,
    password varchar,
    fullname varchar not null check( length(trim(c_username)) > 0) unique,
    email varchar not null check( length(trim(c_username)) > 0) unique,
    role varchar
);