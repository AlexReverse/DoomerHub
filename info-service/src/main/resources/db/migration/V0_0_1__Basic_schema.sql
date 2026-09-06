create schema if not exists doomerhub;

CREATE TABLE main_page (
	user_id uuid NOT NULL,
	nickname varchar(50) NOT NULL,
	name varchar(50) NOT NULL,
	sur_name varchar(50) NOT NULL,
	city varchar(50) NOT NULL,
	birth_day date NOT NULL,
	description varchar(100),
	registration_date timestamp(6) NOT NULL
);

CREATE TABLE education (
    id bigserial NOT NULL,
    user_id uuid NOT NULL,
    hei_name varchar(50) NOT NULL,
    education_start_date VARCHAR(4) NOT NULL,
    education_end_date VARCHAR(4) NOT NULL,
    specialization varchar(50) NOT NULL,
    form_education varchar(50) NOT NULL
)

CREATE TABLE work_experience (
    id bigserial NOT NULL,
    user_id uuid NOT NULL,
    company_name varchar(50) NOT NULL,
    work_start_date date NOT NULL,
    work_end_date date,
    company_position varchar(50) NOT NULL,
    responsibilities varchar(100)
)