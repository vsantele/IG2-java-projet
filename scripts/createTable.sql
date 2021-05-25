DROP TABLE IF EXISTS booking;
DROP TABLE IF EXISTS date;
DROP TABLE IF EXISTS charity;
DROP TABLE IF EXISTS session;
DROP TABLE IF EXISTS activity;

CREATE TABLE activity
(
    activity_code VARCHAR(8),
    title VARCHAR(64) NOT NULL,
    CONSTRAINT activity_pk PRIMARY KEY (activity_code)
);

CREATE TABLE session
(
    session_id INTEGER AUTO_INCREMENT,
    num_day TINYINT NOT NULL,
    start_hour TIME NOT NULL,
    end_hour TIME NOT NULL,
    is_weekly BOOLEAN NOT NULL,
    nb_max INTEGER,
    activity_code VARCHAR(8) NOT NULL,
    CONSTRAINT session_pk PRIMARY KEY (session_id),
    CONSTRAINT session_activity_fk FOREIGN KEY (activity_code) REFERENCES activity(activity_code),
    CONSTRAINT session_num_day_ck CHECK (num_day BETWEEN 1 AND 7),
    CONSTRAINT session_hour_ck CHECK (start_hour < end_hour)
);

CREATE TABLE charity
(
    charity_code VARCHAR(8),
    name VARCHAR(64) NOT NULL,
    contact VARCHAR(64) NOT NULL,
    address VARCHAR(64),
    city VARCHAR(64),
    country VARCHAR(64),
    zip_code VARCHAR(10),
    CONSTRAINT charity_pk PRIMARY KEY (charity_code)
);

CREATE TABLE booking
(
    booking_id INTEGER AUTO_INCREMENT,
    lastname VARCHAR(30) NOT NULL,
    firstname VARCHAR(30) NOT NULL,
    amount DOUBLE NOT NULL,
    is_paid BOOLEAN NOT NULL,
    phone VARCHAR(20) NOT NULL,
    birthdate DATE,
    email VARCHAR(64),
    date DATE NOT NULL,
    charity_code VARCHAR(8) NOT NULL,
    session_id INTEGER NOT NULL,
    CONSTRAINT booking_pk PRIMARY KEY (booking_id),
    CONSTRAINT booking_charity_fk FOREIGN KEY (charity_code) REFERENCES charity(charity_code),
    CONSTRAINT booking_session_fk FOREIGN KEY (session_id) REFERENCES session(session_id)
);

CREATE TABLE date
(
    date_id INTEGER AUTO_INCREMENT,
    type VARCHAR(10) NOT NULL,
    date DATE NOT NULL,
    session_id INTEGER NOT NULL,
    CONSTRAINT date_pk PRIMARY KEY (date_id),
    CONSTRAINT date_session_fk FOREIGN KEY (session_id) REFERENCES session(session_id),
    CONSTRAINT date_type_ck CHECK (type = 'canceled' OR type = 'custom')
);