CREATE SCHEMA NETTY_EXAMPLE_DB AUTHORIZATION DBA;

SET SCHEMA NETTY_EXAMPLE_DB;

DROP TABLE USERS IF EXISTS;

CREATE TABLE USERS(
	USERNO INT GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1) NOT NULL,
	USERID VARCHAR(45) NOT NULL,
	USERNAME VARCHAR(45) NOTN NULL,
	PASSWORD VARCHAR(45) NOT NULL,
	PRIMARY KEY(USERID)
)

