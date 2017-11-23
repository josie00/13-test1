cd /usr/local/mysql/bin
./mysql
create database obs;

INSERT INTO customer VALUES(1, 'Pittsburgh', '1993-05-01', 'abcd', 'josie@gmail.com', 'Josie', 'Liu', '123456', '123-3455-1234', '123-345-1234', 'PA', 'Forbs Ave', 'josie', '15213');

INSERT INTO account_type VALUES(1, 'Checking', 0.1);
INSERT INTO account_type VALUES(2, 'Saving', 0.2);


INSERT INTO account VALUES(1, 100, 'active', 1, 1);
INSERT INTO account VALUES(2, 1300, 'active', 2, 1);
INSERT INTO account VALUES(3, 0, 'inactive', 1, 1);
