CREATE TABLE employee
(
  id integer NOT NULL,
  first_name character varying(20) DEFAULT NULL::character varying,
  last_name character varying(20) DEFAULT NULL::character varying,
  salary integer,
  CONSTRAINT employee_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);

DROP SEQUENCE employee_seq;

CREATE SEQUENCE employee_seq
INCREMENT 1
MINVALUE 1
MAXVALUE 9223372036854775807
START 4
CACHE 1;