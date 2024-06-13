INSERT INTO Portfolio (user_id) VALUES (1);
INSERT INTO Portfolio (user_id) VALUES (2);

INSERT INTO Share (symbol, name, rate, remaining_count) VALUES ('TSL', 'Tesla', 5, 20);
INSERT INTO Share (symbol, name, rate, remaining_count) VALUES ('MST', 'Microsoft', 5, 20);
INSERT INTO Share (symbol, name, rate, remaining_count) VALUES ('APL', 'Apple', 2, 50);
INSERT INTO Share (symbol, name, rate, remaining_count) VALUES ('OAI', 'Open AI', 4, 25);

INSERT INTO Share_Price (share_id, price, created_date) VALUES (1, 20, '2020-08-07');
INSERT INTO Share_Price (share_id, price, created_date) VALUES (2, 20, '2020-08-07');
INSERT INTO Share_Price (share_id, price, created_date) VALUES (3, 30, '2020-08-07');
INSERT INTO Share_Price (share_id, price, created_date) VALUES (4, 40, '2020-08-07');

INSERT INTO Portfolio_Share (share_id, portfolio_id, quantity) VALUES ( 1, 1, 15);
INSERT INTO Portfolio_Share (share_id, portfolio_id, quantity) VALUES (2, 2, 10);

INSERT INTO Trade (portfolio_id, share_id, created_date, trade_type, amount, quantity) VALUES (1, 1, '2020-08-07', 'BUY',  75, 15);
INSERT INTO Trade (portfolio_id, share_id, created_date, trade_type, amount, quantity) VALUES (1, 1, '2020-08-08', 'BUY',  75, 15);
INSERT INTO Trade (portfolio_id, share_id, created_date, trade_type, amount, quantity) VALUES (1, 1, '2020-08-09', 'SELL',  150, 15);


INSERT INTO Trade (portfolio_id, share_id, created_date, trade_type, amount, quantity) VALUES (2, 2, '2020-08-07', 'BUY',  200, 20);
INSERT INTO Trade (portfolio_id, share_id, created_date, trade_type, amount, quantity) VALUES (2, 2, '2020-08-08', 'SELL',  200, 10);


