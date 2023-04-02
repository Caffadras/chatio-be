CREATE TABLE user_profile
(
    id          bigserial PRIMARY KEY,
    username    varchar(255),
    first_name  varchar(255),
    last_name   varchar(255),
    credentials_id int NOT NULL,

    CONSTRAINT fk_secuser_userprofile FOREIGN KEY (credentials_id) REFERENCES user_credentials(id)

);