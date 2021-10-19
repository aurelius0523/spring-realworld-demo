CREATE table tag
(
    id          SERIAL PRIMARY KEY,
    name        varchar(30),
    created_at  timestamptz,
    modified_at timestamptz
);

CREATE TABLE article_tag
(
    id          SERIAL PRIMARY KEY,
    article_id  serial constraint article_id references article (id) on delete cascade,
    tag_id      serial constraint tag_id references tag (id) on delete cascade
);

INSERT INTO tag
VALUES (1, 'hot');
INSERT INTO tag
VALUES (2, 'dragon');

insert INTO article_tag
VALUES (1, 1, 1);
insert INTO article_tag
VALUES (2, 1, 2);
