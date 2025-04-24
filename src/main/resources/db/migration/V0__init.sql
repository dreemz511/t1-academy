CREATE SEQUENCE IF NOT EXISTS task_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE task
(
    id          BIGINT NOT NULL,
    title       VARCHAR(255),
    description VARCHAR(255),
    user_id     BIGINT,
    CONSTRAINT pk_task PRIMARY KEY (id)
);