CREATE TABLE Home (
home_id varchar(50),
home_name varchar(100),
CONSTRAINT pk_home_id PRIMARY KEY(home_id)
);


CREATE TABLE Person (
person_name varchar(50),
person_password varchar(50),
person_mail varchar(255),
person_phone_number varchar(10),
person_address varchar(255),
person_home_id varchar(50),
person_is_admin smallint,
CONSTRAINT pk_person_mail PRIMARY KEY(person_mail),
CONSTRAINT fk_person_home FOREIGN KEY(person_home_id) REFERENCES home(home_id)
);


CREATE TABLE Home_Person(
home_person_home_id varchar(50),
home_person_mail varchar(255),
CONSTRAINT pk_home_person PRIMARY KEY(home_person_mail, home_person_home_id),
CONSTRAINT fk_home_person_id FOREIGN KEY(home_person_home_id) REFERENCES home(home_id),
CONSTRAINT fk_home_person_mail FOREIGN KEY(home_person_mail) REFERENCES person(person_mail)
);


CREATE TABLE Product(
product_barcode varchar(13),
product_name varchar(50),
product_dlc date,
product_qte int,
product_image varchar(255),
product_home_id varchar(50),
CONSTRAINT pk_product_barcode PRIMARY KEY(product_barcode),
CONSTRAINT fk_product_home FOREIGN KEY(product_home_id) REFERENCES home(home_id)
);
