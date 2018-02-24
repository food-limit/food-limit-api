INSERT INTO PING(TITLE) VALUES ('PONG EN DEV !');

INSERT INTO USER_PROFILE (USERNAME, EMAIL, NAME, PASSWORD) VALUES ('admin', 'admin@foodlimit.fr', 'Admin Admin', '$2a$12$I6nKjXN24kFy6Q1sB5EHUeK/wRe7u7LnPesWUHOPKvGah4eGgygvy');
INSERT INTO USER_PROFILE (USERNAME, EMAIL, NAME, PASSWORD) VALUES ('laurent', 'laurent@foodlimit.fr', 'Laurent Thiebault', '$2a$12$I6nKjXN24kFy6Q1sB5EHUeK/wRe7u7LnPesWUHOPKvGah4eGgygvy');
INSERT INTO USER_PROFILE (USERNAME, EMAIL, NAME, PASSWORD) VALUES ('ludo', 'ludo@foodlimit.fr', 'Ludovic Landschoot', '$2a$12$I6nKjXN24kFy6Q1sB5EHUeK/wRe7u7LnPesWUHOPKvGah4eGgygvy');

INSERT INTO PLACE(NAME,USER_ID) VALUES ('maison', 'admin');
INSERT INTO PLACE(NAME,USER_ID) VALUES ('maman', 'admin');
INSERT INTO PLACE(NAME,USER_ID) VALUES ('mamie', 'laurent');
INSERT INTO PLACE(NAME,USER_ID) VALUES ('tonton', 'ludo');

INSERT INTO FOOD(NAME, DLC, PICTURE, QUANTITY, PLACE_ID) VALUES ('haricots', '2018-12-31', 'https://www.w3schools.com/howto/img_fjords.jpg', 2, 1);
INSERT INTO FOOD(NAME, DLC, PICTURE, QUANTITY, PLACE_ID) VALUES ('olives', '2018-12-31', 'https://www.w3schools.com/howto/img_fjords.jpg', 3, 1);
INSERT INTO FOOD(NAME, DLC, PICTURE, QUANTITY, PLACE_ID) VALUES ('abricots', '2018-12-31', 'https://www.w3schools.com/howto/img_fjords.jpg', 10, 1);
INSERT INTO FOOD(NAME, DLC, PICTURE, QUANTITY, PLACE_ID) VALUES ('patates', '2018-12-31', 'https://www.w3schools.com/howto/img_fjords.jpg', 6, 2);
INSERT INTO FOOD(NAME, DLC, PICTURE, QUANTITY, PLACE_ID) VALUES ('kiwi', '2018-12-31', 'https://www.w3schools.com/howto/img_fjords.jpg', 1, 3);
INSERT INTO FOOD(NAME, DLC, PICTURE, QUANTITY, PLACE_ID) VALUES ('fraise', '2018-12-31', 'https://www.w3schools.com/howto/img_fjords.jpg', 1, 4);
