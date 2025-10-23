DROP SCHEMA IF EXISTS feed_app CASCADE;
CREATE SCHEMA feed_app;
SET search_path TO feed_app;

CREATE TABLE feed_app.users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL
);

CREATE TABLE feed_app.polls (
    id BIGSERIAL PRIMARY KEY,
    question VARCHAR(255) NOT NULL,
    published_at TIMESTAMP WITH TIME ZONE NOT NULL,
    valid_until TIMESTAMP WITH TIME ZONE NOT NULL,
    created_by_id BIGINT NOT NULL REFERENCES feed_app.users(id)
);
