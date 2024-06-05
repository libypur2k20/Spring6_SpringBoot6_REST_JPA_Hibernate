CREATE DATABASE eazyschool;

USE eazyschool;

-- Create the contact_msg table
CREATE TABLE IF NOT EXISTS contact_msg (
    contact_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    mobile_num VARCHAR(10) NOT NULL,
    email VARCHAR(100) NOT NULL,
    subject VARCHAR(100) NOT NULL,
    message VARCHAR(500) NOT NULL,
    status VARCHAR(10) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(50) NOT NULL,
    updated_at TIMESTAMP DEFAULT NULL,
    updated_by VARCHAR(50) DEFAULT NULL
    );

CREATE TABLE IF NOT EXISTS holidays(
    holiday_day varchar(20) NOT NULL,
    holiday_reason varchar(100) NOT NULL,
    holiday_type varchar(20) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(50) NOT NULL,
    updated_at TIMESTAMP DEFAULT NULL,
    updated_by VARCHAR(50) DEFAULT NULL
);


CREATE TABLE IF NOT EXISTS roles (
    role_id int NOT NULL AUTO_INCREMENT,
    role_name varchar(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    created_by varchar(50) NOT NULL,
    updated_at TIMESTAMP DEFAULT NULL,
    updated_by varchar(50) DEFAULT NULL,
    PRIMARY KEY (role_id)
    );

CREATE TABLE IF NOT EXISTS address (
    address_id int NOT NULL AUTO_INCREMENT,
    address1 varchar(200) NOT NULL,
    address2 varchar(200) DEFAULT NULL,
    city varchar(50) NOT NULL,
    state varchar(50) NOT NULL,
    zip_code int NOT NULL,
    created_at TIMESTAMP NOT NULL,
    created_by varchar(50) NOT NULL,
    updated_at TIMESTAMP DEFAULT NULL,
    updated_by varchar(50) DEFAULT NULL,
    PRIMARY KEY (address_id)
    );


CREATE TABLE IF NOT EXISTS person (
    person_id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name varchar(100) NOT NULL,
    email varchar(50) NOT NULL,
    mobile_number varchar(20) NOT NULL,
    pwd varchar(200) NOT NULL,
    role_id int NOT NULL,
    address_id int NULL,
    created_at TIMESTAMP NOT NULL,
    created_by varchar(50) NOT NULL,
    updated_at TIMESTAMP DEFAULT NULL,
    updated_by varchar(50) DEFAULT NULL,
    FOREIGN KEY (role_id) REFERENCES roles(role_id),
    FOREIGN KEY (address_id) REFERENCES address(address_id)
);

CREATE TABLE IF NOT EXISTS class (
    class_id int NOT NULL AUTO_INCREMENT,
    name varchar(100) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    created_by varchar(50) NOT NULL,
    updated_at TIMESTAMP DEFAULT NULL,
    updated_by varchar(50) DEFAULT NULL,
    PRIMARY KEY (class_id)
    );

ALTER TABLE person
    ADD COLUMN class_id int NULL AFTER address_id,
    ADD CONSTRAINT FK_CLASS_CLASS_ID FOREIGN KEY (class_id) REFERENCES class(class_id);

CREATE TABLE IF NOT EXISTS courses (
    course_id int NOT NULL AUTO_INCREMENT,
    name varchar(100) NOT NULL,
    fees varchar(10) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    created_by varchar(50) NOT NULL,
    updated_at TIMESTAMP DEFAULT NULL,
    updated_by varchar(50) DEFAULT NULL,
    PRIMARY KEY (course_id)
    );

CREATE TABLE IF NOT EXISTS person_courses (
    person_id int NOT NULL,
    course_id int NOT NULL,
    FOREIGN KEY (person_id) REFERENCES person(person_id),
    FOREIGN KEY (course_id) REFERENCES courses(course_id),
    PRIMARY KEY (person_id,course_id)
    );