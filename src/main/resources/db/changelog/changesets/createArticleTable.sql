CREATE TABLE article
(
    id          SERIAL PRIMARY KEY,
    slug        varchar(512) UNIQUE,
    title       varchar(256),
    description varchar(512),
    body        text,
    created_at  timestamptz,
    modified_at timestamptz,
    author_id   SERIAL
        CONSTRAINT users_article_fkey REFERENCES users (id) ON DELETE CASCADE
);

INSERT INTO article (slug, title, description, body, created_at, modified_at, author_id)
VALUES ('hot-fashion-today', 'Hot fashion today!', 'The latest trend', 'lorem ipsum dolor met', now(), now(), 2);