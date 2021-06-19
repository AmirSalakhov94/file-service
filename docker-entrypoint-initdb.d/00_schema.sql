CREATE TABLE file_meta
(
    id         UUID NOT NULL PRIMARY KEY,
    size       BIGINT,
    name       TEXT,
    extension  TEXT,
    path       TEXT,
    profile_id UUID NOT NULL
);