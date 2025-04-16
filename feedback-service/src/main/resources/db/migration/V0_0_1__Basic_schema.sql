create schema if not exists doomerhub;

CREATE TABLE doomerhub.author_post (
	id serial8 NOT NULL,
	title varchar(50) NOT NULL,
	description varchar(5000) NOT NULL,
	user_id varchar NOT NULL,
	post_date timestamp(6) NOT NULL,
	CONSTRAINT author_post_pk PRIMARY KEY (id)
);

CREATE TABLE doomerhub.favourite_post (
	id serial8 NOT NULL,
	post_id serial8 NOT NULL,
	user_name varchar NOT NULL,
	favourite_post_date timestamp(6) NOT NULL,
	CONSTRAINT favourite_post_pk PRIMARY KEY (id)
);

CREATE TABLE doomerhub.post_review (
	id serial8 NOT NULL,
	post_id serial8 NOT NULL,
	review varchar(100) NOT NULL,
	user_name varchar() NOT NULL,
	post_review_date timestamp(6) NOT NULL,
	CONSTRAINT post_review_pk PRIMARY KEY (id)
);