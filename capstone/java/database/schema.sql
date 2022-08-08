BEGIN TRANSACTION;

DROP TABLE IF EXISTS users;

CREATE TABLE users (
	user_id SERIAL,
	username varchar(50) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

CREATE TABLE rental_property (
	rental_id SERIAL,
	rental_address varchar(200) NOT NULL,
	rental_amount DECIMAL (4,2) NOT NULL,
	bathrooms DECIMAL (1,1) NOT NULL,
	bedrooms varchar (20) NOT NULL,
	is_rented BOOLEAN NOT NULL,
	type_of_residence varchar (50) NOT NULL,
	CONSTRAINT PK_rental_property PRIMARY KEY (rental_id),
);

CREATE TABLE user_rental (
	user_id int NOT NULL,
	rental_id int NOT NULL,
	CONSTRAINT FK_user_rental_user FOREIGN KEY (user_id) REFERENCES users(user_id),
	CONSTRAINT FK_user_rental_user FOREIGN KEY (rental_id) REFERENCES rental_property(rental_id),
);

CREATE TABLE maintenance (
	maintenance_id SERIAL,
	maintenance_request varchar (200),
	completed BOOLEAN,
	completion_date,
	rental_id int NOT NULL,
	CONSTRAINT PK_maintenance PRIMARY KEY (maintenance_id)
	CONSTRAINT FK_maintenance FOREIGN KEY (rental_id) REFERENCES rental_property(rental_id),
);

CREATE TABLE user_maintenance (
	user_id int NOT NULL,
	maintenance_id int NOT NULL,
	CONSTRAINT FK_user_maintenance FOREIGN KEY (user_id) REFERENCES users(user_id),
	CONSTRAINT FK_user_maintenance FOREIGN KEY (maintenance_id) REFERENCES maintenance(maintenance_id),
);

)

COMMIT TRANSACTION;
