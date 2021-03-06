CREATE TABLE ROLES
(
    ID   SERIAL PRIMARY KEY,
    NAME VARCHAR NOT NULL
);

CREATE TABLE USERS
(
    LOGIN    VARCHAR PRIMARY KEY,
    PASSWORD VARCHAR NOT NULL,
    ROLE     INTEGER
        CONSTRAINT FK_USER_LOGIN_TO_ROLE_ID_RELATION
            REFERENCES ROLES
);

CREATE TABLE USER_ROLE
(
    USER_LOGIN VARCHAR,
    ROLE_ID    INTEGER,
    PRIMARY KEY (USER_LOGIN, ROLE_ID),
    FOREIGN KEY (USER_LOGIN) REFERENCES USERS (LOGIN),
    FOREIGN KEY (ROLE_ID) REFERENCES ROLES (ID)
);

INSERT INTO ROLES (NAME)
VALUES ('ADMIN'),
       ('OPERATOR'),
       ('ANALIST');

INSERT INTO USERS (LOGIN, PASSWORD, ROLE)
VALUES ('Vasya228', '228', 1),
       ('Petya322', '322', 2);

INSERT INTO USER_ROLE(USER_LOGIN, ROLE_ID)
VALUES ('Vasya228', 1),
       ('Petya322', 2);