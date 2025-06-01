
CREATE TABLE IF NOT EXISTS notification_event(
    event_id        VARCHAR(64)              NOT NULL PRIMARY KEY,
    event_type      VARCHAR(32)              NOT NULL,
    content         VARCHAR(512)             NOT NULL,
    delivery_status VARCHAR(32)              NOT NULL,
    delivery_date   TIMESTAMP WITH TIME ZONE NOT NULL
);