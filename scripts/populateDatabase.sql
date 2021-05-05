-- Charity
INSERT INTO charity(charity_code, name, contact,address, city, country, zip_code) VALUES ('AVMP', 'A Vos Marques Prêts', 'contact@avosmarquesprets.be', 'Rue de la Barrière Leclercq', 'Mouscron', 'Belgique', '7700');
INSERT INTO charity(charity_code, name, contact,address, city, country, zip_code) VALUES ('CHAR1', 'My Charity 1', 'contact@charity1.be', 'Rue du banannier 14', 'Liege', 'Belgique', '4000');
INSERT INTO charity(charity_code, name, contact,address, city, country, zip_code) VALUES ('YATB', 'Your Are The Best', 'contact@youarethebest.be', '69 Bedford Rd', 'London', 'Royaume-Uni', 'SW4 7RH');

-- Activity
INSERT INTO activity VALUES ('IMN', 'Initiation Marche Nordique');
INSERT INTO activity VALUES ('POT', 'Poterie');
INSERT INTO activity VALUES ('CRCS', 'Comment Recycler Chez Soi?');

-- Session
INSERT INTO session(num_day, start_hour, end_hour, is_weekly, nb_max, activity_code) VALUES (1, '16:00:00', '17:00:00', true, 10, 'IMN' );
INSERT INTO session(num_day, start_hour, end_hour, is_weekly, nb_max, activity_code) VALUES (4, '14:30:00', '15:30:00', true, 10, 'IMN' );
INSERT INTO session(num_day, start_hour, end_hour, is_weekly, nb_max, activity_code) VALUES (1, '16:00:00', '17:00:00', true, 8, 'POT' );
INSERT INTO session(num_day, start_hour, end_hour, is_weekly, nb_max, activity_code) VALUES (3, '18:30:00', '20:00:00', true, 8, 'POT' );
INSERT INTO session(num_day, start_hour, end_hour, is_weekly, nb_max, activity_code) VALUES (6, '10:30:00', '12:00:00', false, 30, 'CRCS' );

-- Date
INSERT INTO date(type, date, session_id) VALUES ('custom', '2021-05-01', 5);
INSERT INTO date(type, date, session_id) VALUES ('custom', '2021-05-08', 5);
INSERT INTO date(type, date, session_id) VALUES ('custom', '2021-05-15', 5);
INSERT INTO date(type, date, session_id) VALUES ('canceled', '2021-05-06', 2);

-- Booking
INSERT INTO booking(lastname, firstname, amount, is_paid, phone, birthdate, email, date, charity_code, session_id) VALUES ('Santelé', 'Victor', 10.0, true, '+32 491/08.62.53', '2000-03-16', 'victor.santele@gmail.com','2021-05-08', 'AVMP', 5);
INSERT INTO booking(lastname, firstname, amount, is_paid, phone, birthdate, email, date, charity_code, session_id) VALUES ('Depauw', 'Loïc', 42.0, false, '+32 471 22 05 79', '2000-10-27', 'lolodpw@gmail.com','2021-05-15', 'AVMP', 5);
INSERT INTO booking(lastname, firstname, amount, is_paid, phone, birthdate, email, date, charity_code, session_id) VALUES ('Depauw', 'Loïc', 0.1, true, '+32 471 22 05 79', '2000-10-27', 'lolodpw@gmail.com','2021-05-10', 'CHAR1', 3);



