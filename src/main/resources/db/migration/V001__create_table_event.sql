CREATE TABLE event(
    id BIGSERIAL NOT NULL,
    type VARCHAR(30) NOT NULL,
    title VARCHAR(100) NOT NULL,
    date_event DATE,
    description VARCHAR(200) NOT NULL,
    
    PRIMARY KEY(id)
);