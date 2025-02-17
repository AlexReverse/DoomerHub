create schema if not exist search;

create table search.t_post(
    id serial primal key,
    c_title varchar(50) not null check (length(trim(c_title)) >= 3),
    c_description varchar(5000)
);