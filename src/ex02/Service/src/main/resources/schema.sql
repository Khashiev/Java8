DROP TABLE IF EXISTS userTable CASCADE;
CREATE TABLE userTable (
    id SERIAL PRIMARY KEY,
    email varchar(30),
    password varchar(30)
);