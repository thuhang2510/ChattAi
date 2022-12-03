create table tbl_user(
	id INT IDENTITY(1,1) PRIMARY KEY,
    firstname VARCHAR(55) NULL,
    lastname VARCHAR(55) NULL,
	password VARCHAR(55) NULL,
    gender VARCHAR(10) NULL,
	email VARCHAR(55) NULL UNIQUE,
);

alter table tbl_user add reset_password_token varchar(55)