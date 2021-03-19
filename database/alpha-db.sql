-- DROP SCHEMA alpha_schema;

CREATE SCHEMA alpha_schema AUTHORIZATION postgres;

-- DROP SEQUENCE alpha_schema.sequence_generator;

CREATE SEQUENCE alpha_schema.sequence_generator
	INCREMENT BY 50
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;-- alpha_schema.alpha_object definition

-- Drop table

-- DROP TABLE alpha_schema.alpha_object;

CREATE TABLE alpha_schema.alpha_object (
	id int8 NOT NULL,
	alpha_data varchar NULL,
	CONSTRAINT alpha_object_pk PRIMARY KEY (id)
);
