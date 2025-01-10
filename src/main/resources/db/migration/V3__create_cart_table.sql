CREATE TABLE cart (
    id INT AUTO_INCREMENT PRIMARY KEY,
    profile_id bigint NOT NULL,
    created_date_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_date_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    KEY FK_ProfileCart (profile_id),
    CONSTRAINT FK_ProfileCart FOREIGN KEY (profile_id) REFERENCES profile (id)
);