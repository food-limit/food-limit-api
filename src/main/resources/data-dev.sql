INSERT INTO PING(TITLE) VALUES ('PONG EN DEV !');

INSERT INTO USER_PROFILE (USERNAME, EMAIL, NAME, PASSWORD) VALUES ('admin', 'admin@foodlimit.fr', 'Admin Admin', '$2a$12$I6nKjXN24kFy6Q1sB5EHUeK/wRe7u7LnPesWUHOPKvGah4eGgygvy');
INSERT INTO USER_PROFILE (USERNAME, EMAIL, NAME, PASSWORD) VALUES ('laurent', 'laurent@foodlimit.fr', 'Laurent Thiebault', '$2a$12$I6nKjXN24kFy6Q1sB5EHUeK/wRe7u7LnPesWUHOPKvGah4eGgygvy');
INSERT INTO USER_PROFILE (USERNAME, EMAIL, NAME, PASSWORD) VALUES ('ludo', 'ludo@foodlimit.fr', 'Ludovic Landschoot', '$2a$12$I6nKjXN24kFy6Q1sB5EHUeK/wRe7u7LnPesWUHOPKvGah4eGgygvy');

INSERT INTO PLACE(NAME,USER_ID) VALUES ('maison', 'admin');
INSERT INTO PLACE(NAME,USER_ID) VALUES ('maman', 'admin');
INSERT INTO PLACE(NAME,USER_ID) VALUES ('mamie', 'laurent');
INSERT INTO PLACE(NAME,USER_ID) VALUES ('tonton', 'ludo');
