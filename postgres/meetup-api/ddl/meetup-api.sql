CREATE DATABASE "meetup";
\c "meetup"

SET client_encoding = 'UTF8';

CREATE SCHEMA "app";

CREATE TABLE IF NOT EXISTS app.event
(
    id uuid NOT NULL,
    description character varying(255),
    dt_updated timestamp without time zone,
    location character varying(255),
    organizer character varying(255),
    scheduled_time timestamp without time zone,
    subject character varying(255),
    CONSTRAINT event_pkey PRIMARY KEY (id)
);