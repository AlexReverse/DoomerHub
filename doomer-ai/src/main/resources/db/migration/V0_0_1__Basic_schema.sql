create schema if not exists aitranslation;

CREATE TABLE aitranslation.posts_translation (
	id bigserial NOT NULL,
	translated_description varchar(5000) NOT NULL,
	translated_date timestamp(6) NOT NULL,
	CONSTRAINT posts_translation_pk PRIMARY KEY (id)
);