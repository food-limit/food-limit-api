CREATE TABLE Home (
id_home varchar(50),
home_name varchar(100),
CONSTRAINT pk_home_id PRIMARY KEY(id_home)
);


CREATE TABLE Person (
person_name varchar(50),
password varchar(50),
mail varchar(255),
phone_number varchar(10),
address varchar(255),
id_home varchar(50),
is_admin smallint,
CONSTRAINT pk_person_mail PRIMARY KEY(mail),
CONSTRAINT fk_person_home FOREIGN KEY(id_home) REFERENCES home(id_home)
);


CREATE TABLE Home_Person(
id_home varchar(50),
mail varchar(255),
CONSTRAINT pk_home_person PRIMARY KEY(mail,id_home),
CONSTRAINT fk_home_person_id FOREIGN KEY(id_home) REFERENCES home(id_home),
CONSTRAINT fk_home_person_mail FOREIGN KEY(mail) REFERENCES person(mail)
);


CREATE TABLE Product(
barcode varchar(13),
product_name varchar(50),
dlc date,
qte int,
image varchar(255),
id_home varchar(50),
CONSTRAINT pk_product_barcode PRIMARY KEY(barcode),
CONSTRAINT fk_product_home FOREIGN KEY(id_home) REFERENCES home(id_home)
);
