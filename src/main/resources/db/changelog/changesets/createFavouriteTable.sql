CREATE TABLE user_article_favourite(
    id SERIAL PRIMARY KEY,
    user_id SERIAL CONSTRAINT user_article_favourite_user_id_fkey REFERENCES users(id) ON DELETE CASCADE,
    article_id SERIAL CONSTRAINT user_article_favourite_article_id_fkey REFERENCES article(id) ON DELETE CASCADE,
    created_at timestamptz,
    modified_at timestamptz
)