-- DROP SCHEMA beta_schema;

CREATE SCHEMA beta_schema AUTHORIZATION postgres;

-- DROP SEQUENCE beta_schema.sequence_generator;

CREATE SEQUENCE beta_schema.sequence_generator
	INCREMENT BY 50
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;-- beta_schema.beta_object definition

-- Drop table

-- DROP TABLE beta_schema.beta_object;

CREATE TABLE beta_schema.beta_object (
	id int8 NOT NULL,
	beta_data varchar NULL,
	CONSTRAINT beta_object_pk PRIMARY KEY (id)
);
