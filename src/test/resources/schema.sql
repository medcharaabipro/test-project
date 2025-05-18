CREATE TABLE IF NOT EXISTS users (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       first_name VARCHAR(255),
                       last_name VARCHAR(255),
                       department_id BIGINT
);