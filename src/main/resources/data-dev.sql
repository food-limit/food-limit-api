INSERT INTO PING(TITLE) VALUES ('PONG EN DEV !');

INSERT INTO USER_PROFILE (USERNAME, EMAIL, NAME, PASSWORD) VALUES ('admin', 'admin@foodlimit.fr', 'Admin Admin', '$2a$12$I6nKjXN24kFy6Q1sB5EHUeK/wRe7u7LnPesWUHOPKvGah4eGgygvy');
INSERT INTO USER_PROFILE (USERNAME, EMAIL, NAME, PASSWORD) VALUES ('laurent', 'laurent@foodlimit.fr', 'Laurent Thiebault', '$2a$12$I6nKjXN24kFy6Q1sB5EHUeK/wRe7u7LnPesWUHOPKvGah4eGgygvy');
INSERT INTO USER_PROFILE (USERNAME, EMAIL, NAME, PASSWORD) VALUES ('ludo', 'ludo@foodlimit.fr', 'Ludovic Landschoot', '$2a$12$I6nKjXN24kFy6Q1sB5EHUeK/wRe7u7LnPesWUHOPKvGah4eGgygvy');

INSERT INTO FOOD (NAME, QUANTITY, DLC, PICTURE, USER_ID) VALUES ('banane', 2, '2018-09-01', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTHLJ2e6ga1hGo93kiqbZa0J9NmAmn537io1WWUYvmaDymT1rHm', 'admin');
INSERT INTO FOOD (NAME, QUANTITY, DLC, PICTURE, USER_ID) VALUES ('concombre', 3, '2018-02-01', 'https://www.lesfruitsetlegumesfrais.com/_upload/cache/ressources/produits/concombre/concombre_346_346_filled.jpg', 'admin');
INSERT INTO FOOD (NAME, QUANTITY, DLC, PICTURE, USER_ID) VALUES ('tomate', 1, '2018-02-05', 'http://www.latomate.net/wp-content/uploads/2017/07/tomate.png', 'ludo');
