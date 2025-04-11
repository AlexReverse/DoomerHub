create schema if not exists doomerhub;

create table doomerhub.t_post(
    id serial primary key,
    title varchar(50) not null check (length(trim(title)) >= 3),
    description varchar(5000)
);

CREATE TABLE doomerhub.favourite_post (
	id serial4 NOT NULL,
	post_id serial4 NOT NULL,
	user_name varchar NOT NULL,
	CONSTRAINT favourite_post_pk PRIMARY KEY (id)
);

CREATE TABLE doomerhub.favourite_post (
	id serial4 NOT NULL,
	post_id serial4 NOT NULL,
	user_name varchar NOT NULL,
	CONSTRAINT favourite_post_pk PRIMARY KEY (id)
);