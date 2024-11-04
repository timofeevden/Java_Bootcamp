DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS chatrooms CASCADE;
DROP TABLE IF EXISTS messages CASCADE;

CREATE TABLE IF NOT EXISTS users (
    id       SERIAL PRIMARY KEY UNIQUE,
    login    VARCHAR NOT NULL UNIQUE,
    password VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS chatrooms (
    id       SERIAL PRIMARY KEY UNIQUE,
    name     VARCHAR NOT NULL,
    owner_id INTEGER NOT NULL,
    CONSTRAINT owner_id_fk FOREIGN KEY (owner_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS messages (
    id           SERIAL PRIMARY KEY UNIQUE,
    author_id    INTEGER,
    chatroom_id  INTEGER,
    text         TEXT,
    date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT author_id_fk FOREIGN KEY (author_id) REFERENCES users (id),
    CONSTRAINT chatroom_id_fk FOREIGN KEY (chatroom_id) REFERENCES chatrooms (id)
);
