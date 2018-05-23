CREATE TABLE openid_client(
    client_id UUID NOT NULL PRIMARY KEY,
    version UUID NOT NULL,
    created TIMESTAMP WITH TIME ZONE NOT NULL,
    updated TIMESTAMP WITH TIME ZONE NOT NULL,
    redirect_uris JSONB NOT NULL,
    response_types JSONB NOT NULL,
    grant_types JSONB NOT NULL,
    application_type VARCHAR(10) NOT NULL,
    contacts JSONB NOT NULL,
    client_name VARCHAR(200) NOT NULL,
    client_uri VARCHAR(260) NOT NULL,
    secret UUID NOT NULL,
    owner_id UUID NOT NULL,

    CONSTRAINT FK_owner_id FOREIGN KEY (owner_id) REFERENCES users (user_id) ON DELETE RESTRICT ON UPDATE CASCADE
);
