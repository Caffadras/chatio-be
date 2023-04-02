CREATE TABLE message
(
    message_id bigserial PRIMARY KEY,
    contents varchar(255),
    timestamp TIMESTAMP,
    sender_id int,

    CONSTRAINT fk_userprofile_message FOREIGN KEY (sender_id) REFERENCES user_profile(id)
);