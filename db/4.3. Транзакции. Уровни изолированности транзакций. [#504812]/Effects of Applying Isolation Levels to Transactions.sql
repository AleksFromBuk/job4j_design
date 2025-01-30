CREATE TABLE accounts (
                          id SERIAL PRIMARY KEY,
                          login VARCHAR(255) NOT NULL,
                          balance BIGINT DEFAULT 0 NOT NULL,
                          created_at TIMESTAMP DEFAULT NOW()
);

INSERT INTO accounts (login, balance) VALUES
                                          ('petya', 1000),
                                          ('vasya', 2000),
                                          ('mark', 500);


-- Read uncommitted
