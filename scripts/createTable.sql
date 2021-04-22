
DROP TABLE IF EXISTS activity;
CREATE TABLE activity
(
    activity_code VARCHAR(8),
    title VARCHAR(20) NOT NULL,
    CONSTRAINT activity_pk PRIMARY KEY (activity_code)
);

DROP TABLE IF EXISTS session;
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
    CONSTRAINT session_activity_fk FOREIGN KEY (activity_code) REFERENCES activity(activity_code)
);

DROP TABLE IF EXISTS charity;
CREATE TABLE charity
(
    charity_code VARCHAR(8),
    name VARCHAR(20) NOT NULL,
    contact VARCHAR(20) NOT NULL,
    address VARCHAR(20),
    city VARCHAR(20),
    country VARCHAR(20),
    zip_code VARCHAR(10),
   CONSTRAINT charity_pk PRIMARY KEY (charity_code)
);

DROP TABLE IF EXISTS booking;
CREATE TABLE booking
(
    booking_id INTEGER AUTO_INCREMENT,
    last_name VARCHAR(30) NOT NULL,
    first_name VARCHAR(30) NOT NULL,
    amount DOUBLE NOT NULL,
    is_paid BOOLEAN NOT NULL,
    phone VARCHAR(20) NOT NULL,
    birth_date DATE,
    email VARCHAR(20),
    date DATE NOT NULL,
    charity_code VARCHAR(8) NOT NULL,
    session_id INTEGER NOT NULL,
    CONSTRAINT booking_pk PRIMARY KEY (booking_id),
    CONSTRAINT booking_charity_fk FOREIGN KEY (charity_code) REFERENCES charity(charity_code),
    CONSTRAINT booking_session_fk FOREIGN KEY (session_id) REFERENCES session(session_id)
);

DROP TABLE IF EXISTS date;
CREATE TABLE date
(
    date_id INTEGER AUTO_INCREMENT,
    type VARCHAR(10) NOT NULL,
    date DATE NOT NULL,
    session_id INTEGER NOT NULL,
    CONSTRAINT date_pk PRIMARY KEY (date_id),
    CONSTRAINT date_session_fk FOREIGN KEY (session_id) REFERENCES session(session_id)
);