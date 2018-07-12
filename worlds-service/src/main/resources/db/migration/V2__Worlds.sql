CREATE TABLE worlds(
    world_id UUID NOT NULL PRIMARY KEY,
    version UUID NOT NULL,
    created TIMESTAMP WITH TIME ZONE NOT NULL,
    updated TIMESTAMP WITH TIME ZONE NOT NULL,
    name VARCHAR(100) NOT NULL,
    display_name VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,
    owner UUID NOT NULL,

    CONSTRAINT fk_world_owner FOREIGN KEY (owner) REFERENCES users (user_id) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT uk_world_owner_name UNIQUE (owner, name)
);
