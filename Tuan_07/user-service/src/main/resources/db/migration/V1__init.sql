CREATE TABLE users (
    id              VARCHAR(40)   PRIMARY KEY,
    username        VARCHAR(50)   NOT NULL UNIQUE,
    email           VARCHAR(120)  NOT NULL UNIQUE,
    password_hash   VARCHAR(120)  NOT NULL,
    full_name       VARCHAR(120),
    roles           VARCHAR(120)  NOT NULL DEFAULT 'USER',
    created_at      TIMESTAMPTZ   NOT NULL DEFAULT now(),
    updated_at      TIMESTAMPTZ   NOT NULL DEFAULT now()
);

CREATE INDEX idx_users_email    ON users (email);
CREATE INDEX idx_users_username ON users (username);
