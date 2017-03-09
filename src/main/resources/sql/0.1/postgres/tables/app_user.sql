CREATE TABLE app_user
(
    id VARCHAR(255) NOT NULL,
    username VARCHAR(255) PRIMARY KEY NOT NULL,
    password VARCHAR(255) NOT NULL,
    authorities VARCHAR(255),
    enabled BOOLEAN NOT NULL,
    account_non_expired BOOLEAN NOT NULL,
    credentials_non_expired BOOLEAN NOT NULL,
    account_non_locked BOOLEAN NOT NULL,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    email VARCHAR(255),
    registration_time BIGINT NOT NULL,
    receive_email_updates BOOLEAN NOT NULL
);
CREATE UNIQUE INDEX app_user_id_uindex ON app_user (id);
CREATE UNIQUE INDEX app_user_username_uindex ON app_user (username);
