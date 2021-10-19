/**
  1. A follow B, B follow A is possible.
  2. A follow cannot be duplicated
  3. When a user is deleted, the follow is removed
 */
CREATE TABLE user_follow
(
    id          SERIAL primary key,
    follower_id SERIAL
        CONSTRAINT fk_userId_follower REFERENCES users (id) ON DELETE CASCADE,
    followee_id SERIAL
        CONSTRAINT fk_userId_followee REFERENCES users (id) ON DELETE CASCADE,
    UNIQUE (follower_id, followee_id),
    created_at timestamptz,
    modified_at timestamptz
);

-- Phil follows everyone
INSERT INTO user_follow (followee_id, follower_id, created_at, modified_at) VALUES (2, 1, now(), now());
INSERT INTO user_follow (followee_id, follower_id, created_at, modified_at) VALUES (3, 1, now(), now());
INSERT INTO user_follow (followee_id, follower_id, created_at, modified_at) VALUES (4, 1, now(), now());
INSERT INTO user_follow (followee_id, follower_id, created_at, modified_at) VALUES (5, 1, now(), now());
INSERT INTO user_follow (followee_id, follower_id, created_at, modified_at) VALUES (6, 1, now(), now());
INSERT INTO user_follow (followee_id, follower_id, created_at, modified_at) VALUES (7, 1, now(), now());
INSERT INTO user_follow (followee_id, follower_id, created_at, modified_at) VALUES (8, 1, now(), now());
INSERT INTO user_follow (followee_id, follower_id, created_at, modified_at) VALUES (9, 1, now(), now());
INSERT INTO user_follow (followee_id, follower_id, created_at, modified_at) VALUES (10, 1, now(), now());
