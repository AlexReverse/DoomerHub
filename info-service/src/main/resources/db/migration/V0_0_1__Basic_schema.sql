create schema if not exists infoservice;

CREATE TABLE doomerhub.author_post (
	user_id uuid NOT NULL,
	nickname varchar(50) NOT NULL,
	name varchar(50) NOT NULL,
	sur_name varchar(50) NOT NULL,
	city varchar(50) NOT NULL,
	age serial NOT NULL,
	description varchar(100),
	registration_date timestamp(6) NOT NULL
);