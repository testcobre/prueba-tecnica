
CREATE TABLE IF NOT EXISTS notification_event(
    event_id        VARCHAR(64)              NOT NULL PRIMARY KEY,
    event_type      VARCHAR(32)              NOT NULL,
    content         VARCHAR(512)             NOT NULL,
    delivery_status VARCHAR(32)              NOT NULL,
    delivery_date   TIMESTAMP WITH TIME ZONE NOT NULL
);


CREATE TABLE IF NOT EXISTS subscription(
    subscription_id UUID          NOT NULL PRIMARY KEY,
    url             VARCHAR(2048)              NOT NULL,
    content         VARCHAR(512)             NOT NULL,
    delivery_status VARCHAR(32)              NOT NULL,
    delivery_date   TIMESTAMP WITH TIME ZONE NOT NULL
);