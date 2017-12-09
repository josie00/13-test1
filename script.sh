cd /usr/local/mysql/bin
./mysql
use obs;


INSERT INTO customer VALUES(1, 'Pittsburgh', '1993-05-01', 'abcd', 'josie@gmail.com', 'Josie', 'Liu', '123456', '123-3455-1234', '123-345-1234', 'PA', 'Forbs Ave', 'josie', '15213');
INSERT INTO customer VALUES(3, 'Pittsburgh', '1993-05-01', 'abcd', 'yuhan@gmail.com', 'Irene', 'Fu', '123456', '123-3455-1234', '123-345-1234', 'PA', 'Irene', 'irene', '15213');

INSERT INTO account_type VALUES(1, 'Checking', 0.1);
INSERT INTO account_type VALUES(2, 'Saving', 0.2);


INSERT INTO account VALUES(1, '123443211123', 800,'2017-01-02','234213453', 'active','323453012', 1, 1);
INSERT INTO account VALUES(2, '584022343768',1300, '2016-04-23', '012902033','active', '234513450', 2, 1);
INSERT INTO account VALUES(3, '324532542148',0, '2015-05-12','034532345','inactive', '053213453', 1, 1);
INSERT INTO account VALUES(4, '674022343799',100, '2016-04-23', '012902033','active', '234513450', 2, 3);

INSERT INTO transaction_type VALUES(1, 0, 'Withdraw');
INSERT INTO transaction_type VALUES(2, 0, 'Deposit');
INSERT INTO transaction_type VALUES(3, 0, 'Transfer');
INSERT INTO transaction_type VALUES(4, 0, 'Bill Pay');
INSERT INTO transaction_type VALUES(5, 0, 'Loan Pay');
INSERT INTO transaction_type VALUES(6, 0, 'Other');

INSERT INTO transaction VALUES(1, 500, 'VENMO DES:PAYMENT ID:XXXXX1456 INDN:JOSIE LIU CO ID:XXXXX81992 WEB', 'Processing', '2017-08-23', 800, 1, NULL, 2);
INSERT INTO transaction VALUES(2, -300, 'BARCLAYCARD US DES:CREDITCARD ID:XXXXX3972 INDN:JOSIE LIU CO ID:XXXXX07970 WEB', 'Clear', '2017-03-23', 1100 ,1, NULL, 3);
INSERT INTO transaction VALUES(3, 200.86, 'Online Banking transfer from SAV 3038 Confirmation# 2541880513', 'Clear', '2017-02-05', 899.14, 1, NULL, 3);


INSERT INTO loan_type VALUES(1, 48, 0.2, '48 Months Auto Loan Plan');
INSERT INTO loan_type VALUES(2, 360, 0.1, '360 Months Mortgage Plan');

INSERT INTO loan VALUES(1, 600, '832934520352','2017-12-25', '2016-05-21','active', 28800, 1, 1);
INSERT INTO loan VALUES(2, 1500, '342341456236','2017-12-21', '2013-07-02','active', 5400000, 1, 2);
INSERT INTO loan VALUES(3, 300, '123433524345','2017-12-25', '2016-05-21','inactive', 10000, 1, 1);


