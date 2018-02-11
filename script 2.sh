cd /usr/local/mysql/bin
./mysql
use cfs;


INSERT INTO customer VALUES(1, '1234 Forbs Ave', 'Apt 3', '53.00', 'Pittsburgh', 'Josie', 'Liu', '123456', 'PA', '20.00', 'josie','15217');
INSERT INTO customer VALUES(2, '1234 Forbs Ave', 'Apt 4', '200.00', 'Pittsburgh', 'Ziyao', 'Wu', '123456', 'PA', '200.00', 'ziyao','15217');
INSERT INTO employee VALUES(1, 'Josie', 'Liu', '123456', 'josie');
INSERT INTO fund VALUES(1, 9, 'fund1', 'f1');
INSERT INTO fund VALUES(2, 60, 'fund2', 'f2');
INSERT INTO fund_price_history VALUES(3, 9, '2014-03-02', 1);
INSERT INTO position VALUES(1,100,100,1,1);

INSERT INTO transaction VALUES(1, null, 0, 0,'Pending', 100, 'Request Check', 1, 1, 1);
INSERT INTO transaction VALUES(2, null, 0, 0,'Pending', 200, 'Deposit Check', 1, 1, 1);
