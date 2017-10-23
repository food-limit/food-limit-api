
CREATE TABLE Home (
idhome varchar(50),
homename varchar(100),
CONSTRAINT pk_home_id PRIMARY KEY(idhome)
);


CREATE TABLE Person (
personname varchar(50),
password varchar(8),
mail varchar(255),
phonenumber varchar(10),
adresse varchar(255),
idhome varchar(50),
isadmin smallint,
CONSTRAINT pk_person_mail PRIMARY KEY(mail),
CONSTRAINT fk_person_home FOREIGN KEY(idhome) REFERENCES home(idhome)
);


CREATE TABLE home_person(
idhome varchar(50),
mail varchar(255),
CONSTRAINT pk_home_person PRIMARY KEY(mail,idhome),
CONSTRAINT fk_home_person_id FOREIGN KEY(idhome) REFERENCES home(idhome),
CONSTRAINT fk_home_person_mail FOREIGN KEY(mail) REFERENCES person(mail)
);


CREATE TABLE product(
barcode varchar(13),
productname varchar(50),
dlc date,
qte int,
image varchar(255),
idhome varchar(50),
CONSTRAINT pk_product_barcode PRIMARY KEY(barcode),
CONSTRAINT fk_product_home FOREIGN KEY(idhome) REFERENCES home(idhome)
);






