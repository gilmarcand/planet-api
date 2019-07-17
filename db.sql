--schema
GRANT ALL ON SCHEMA public TO PUBLIC;

GRANT ALL ON SCHEMA public TO api_user;

-- sequence
CREATE SEQUENCE public.hibernate_sequence
    INCREMENT 1
    START 4
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.hibernate_sequence
    OWNER TO api_user;

--table
CREATE TABLE public.planet
(
    id bigint NOT NULL,
    climate character varying(255) COLLATE pg_catalog."default" NOT NULL,
    films bigint,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    terrain character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT planet_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.planet
    OWNER to api_user;
