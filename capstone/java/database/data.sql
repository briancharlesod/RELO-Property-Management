BEGIN TRANSACTION;

INSERT INTO users (username,password_hash,role) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN');

INSERT INTO rental_property (rental_address,rental_amount,bathrooms,bedrooms,is_rented,type_of_residence) VALUES ('760-782 Shady Dr, Pittsburgh, PA 15228', 1050.00, 1.0, 2, false, 'house');
INSERT INTO rental_property (rental_address,rental_amount,bathrooms,bedrooms,is_rented,type_of_residence) VALUES ('4925 Friendship Ave Apt 6, Pittsburgh, PA 15224', 1400.00, 1.0, 2, false, 'apartment');
INSERT INTO rental_property (rental_address,rental_amount,bathrooms,bedrooms,is_rented,type_of_residence) VALUES ('4925 Friendship Ave Apt 3, Pittsburgh, PA 15224', 1400.50, 1.5, 2, false, 'apartment');
INSERT INTO rental_property (rental_address,rental_amount,bathrooms,bedrooms,is_rented,type_of_residence) VALUES ('5737 Hobart St, Pittsburgh, PA 15217', 1648.00, 1.0, 2, false, 'house');




COMMIT TRANSACTION;
