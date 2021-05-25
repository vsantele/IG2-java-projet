-- Charity
INSERT INTO charity(charity_code, name, contact,address, city, country, zip_code) VALUES ('AVMP', 'A Vos Marques Prêts', 'contact@avosmarquesprets.be', 'Rue de la Barrière Leclercq', 'Mouscron', 'Belgique', '7700');
INSERT INTO charity(charity_code, name, contact,address, city, country, zip_code) VALUES ('CHAR1', 'My Charity 1', 'contact@charity1.be', 'Rue du bananier 14', 'Liege', 'Belgique', '4000');
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
INSERT INTO date(type, date, session_id) VALUES ('custom', '2021-05-29', 5);
INSERT INTO date(type, date, session_id) VALUES ('custom', '2021-06-12', 5);
INSERT INTO date(type, date, session_id) VALUES ('canceled', '2021-05-06', 2);
INSERT INTO date(type, date, session_id) VALUES ('canceled', '2021-06-06', 2);

-- Booking
INSERT INTO booking(lastname, firstname, amount, is_paid, phone, birthdate, email, date, charity_code, session_id) VALUES ('Santelé', 'Victor', 10.0, true, '+32 491/08.62.53', '2000-03-16', 'victor.santele@gmail.com', '2021-05-08', 'AVMP', 5);
INSERT INTO booking(lastname, firstname, amount, is_paid, phone, birthdate, email, date, charity_code, session_id) VALUES ('Depauw', 'Loïc', 42.0, false, '+32 471/22.05.79', '2000-10-27', 'lolodpw@gmail.com', '2021-05-15', 'AVMP', 5);
INSERT INTO booking(lastname, firstname, amount, is_paid, phone, birthdate, email, date, charity_code, session_id) VALUES ('Depauw', 'Loïc', 10, true, '+32 471/22.05.79', '2000-10-27', 'lolodpw@gmail.com', '2021-05-10', 'CHAR1', 3);
INSERT INTO booking(lastname, firstname, amount, is_paid, phone, birthdate, email, date, charity_code, session_id) VALUES ('Benoit', 'Aristide', 20.83, false, '0461/26.59.10', '1974-03-01', 'Aristide_Benoit@yahoo.fr', '2021-06-17', 'AVMP', 2);
INSERT INTO booking(lastname, firstname, amount, is_paid, phone, birthdate, email, date, charity_code, session_id) VALUES ('Julien', 'Anselme', 21.19, true, '0401/15.80.06', '1989-06-08', 'Anselme_Julien@gmail.com', '2021-06-17', 'YATB', 2);
INSERT INTO booking(lastname, firstname, amount, is_paid, phone, birthdate, email, date, charity_code, session_id) VALUES ('Leclerc', 'Aymard', 9.14, false, '0416/73.78.98', '1971-04-29', 'Aymard83@hotmail.fr', '2021-06-17', 'YATB', 2);
INSERT INTO booking(lastname, firstname, amount, is_paid, phone, birthdate, email, date, charity_code, session_id) VALUES ('Schneider', 'Emmanuel', 23.77, true, '0425/55.17.45', '2001-01-23', 'Emmanuel.Schneider4@gmail.com', '2021-05-31', 'CHAR1', 3);
INSERT INTO booking(lastname, firstname, amount, is_paid, phone, birthdate, email, date, charity_code, session_id) VALUES ('Adam', 'Anceline', 18.11, true, '0409/26.82.78', '1974-05-13', 'Anceline_Adam90@gmail.com', '2021-05-31', 'YATB', 3);
INSERT INTO booking(lastname, firstname, amount, is_paid, phone, birthdate, email, date, charity_code, session_id) VALUES ('Vincent', 'Tiphaine', 31.97, false, '0418/85.17.57', '2001-09-24','Tiphaine65@hotmail.fr', '2021-06-17', 'YATB', 2);
INSERT INTO booking(lastname, firstname, amount, is_paid, phone, birthdate, email, date, charity_code, session_id) VALUES ('Blanchard', 'Baptiste', 3.22, true, '0482/95.11.47', '1992-8-24','Baptiste33@hotmail.fr', '2021-05-31', 'YATB', 3);
INSERT INTO booking(lastname, firstname, amount, is_paid, phone, birthdate, email, date, charity_code, session_id) VALUES ('Martin', 'Rodolphe', 3.06, true, '0484/83.52.01', '1971-01-27', 'Rodolphe.Martin86@hotmail.fr', '2021-06-05', 'CHAR1', 5);
INSERT INTO booking(lastname, firstname, amount, is_paid, phone, birthdate, email, date, charity_code, session_id) VALUES ('Faure', 'Thérèse', 28.61, false, '0458/55.40.68', '1976-10-09', 'Thrse_Faure35@yahoo.fr', '2021-06-05', 'CHAR1', 5);
INSERT INTO booking(lastname, firstname, amount, is_paid, phone, birthdate, email, date, charity_code, session_id) VALUES ('Francois', 'Toussaint', 38.04, false, '0451/55.67.39', '2003-07-26', 'Toussaint_Francois9@hotmail.fr',' 2021-05-24', 'AVMP', 1);
INSERT INTO booking(lastname, firstname, amount, is_paid, phone, birthdate, email, date, charity_code, session_id) VALUES ('Meyer', 'Astride', 25.96, true, '0419/94.96.50', '2000-06-18', 'Astride_Meyer25@gmail.com', '2021-06-14', 'CHAR1', 3);
INSERT INTO booking(lastname, firstname, amount, is_paid, phone, birthdate, email, date, charity_code, session_id) VALUES ('Nguyen', 'Muriel', 23.94, true, '0436/17.47.83', '2007-01-07', 'Muriel_Nguyen@gmail.com', '2021-06-07', 'AVMP', 3);
INSERT INTO booking(lastname, firstname, amount, is_paid, phone, birthdate, email, date, charity_code, session_id) VALUES ('Boyer', 'Henri', 45.03, true, '0451/44.23.64', '1973-01-17', 'Henri10@yahoo.fr', '2021-05-29', 'AVMP', 5);
INSERT INTO booking(lastname, firstname, amount, is_paid, phone, birthdate, date, charity_code, session_id) VALUES ('Lucas', 'Pierre', 34.28, true, '0485/91.80.49', '1981-11-25', '2021-06-10', 'CHAR1', 2);
INSERT INTO booking(lastname, firstname, amount, is_paid, phone, birthdate, date, charity_code, session_id) VALUES ('Roy', 'Guy', 12.28, true, '0442/24.83.76', '1973-06-9', '2021-05-24', 'YATB', 3);
INSERT INTO booking(lastname, firstname, amount, is_paid, phone, email, date, charity_code, session_id) VALUES ('Marchand', 'Manassé', 39.98, true, '0460/84.78.45', 'Manass_Marchand87@yahoo.fr', '2021-06-02', 'CHAR1', 4);
INSERT INTO booking(lastname, firstname, amount, is_paid, phone, email, date, charity_code, session_id) VALUES ('Faure', 'Théophile', 27.38, false, '0478/78.13.84', 'Thophile.Faure27@gmail.com', '2021-06-12', 'YATB', 5);
INSERT INTO booking(lastname, firstname, amount, is_paid, phone, date, charity_code, session_id) VALUES ('Perez', 'Arnaud', 30.25, false, '0481/00.99.91', '2021-06-14', 'CHAR1', 3);
INSERT INTO booking(lastname, firstname, amount, is_paid, phone, date, charity_code, session_id) VALUES ('Meyer', 'Agnane', 19.43, true, '0495/58.57.67', '2021-05-27', 'AVMP', 2);
