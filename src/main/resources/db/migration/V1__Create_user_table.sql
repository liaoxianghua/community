create table USER
(
	ID INTEGER  auto_increment,
	ACCOUNT_ID VARCHAR(100),
	NAME VARCHAR(100),
	TOKEN CHAR(36),
	GMT_CREATE BIGINT,
	GMT_MODIFIED BIGINT,
	primary key (ID)
);