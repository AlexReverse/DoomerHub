create schema if not exists aitranslation;

CREATE TABLE aitranslation.english_translation (
	id bigserial NOT NULL,
	post_id bigserial NOT NULL,
	translated_description varchar(5000) NOT NULL,
	translated_date timestamp(6) NOT NULL,
	CONSTRAINT english_translation_pk PRIMARY KEY (id),
	CONSTRAINT english_translation_unique UNIQUE (post_id)
);
