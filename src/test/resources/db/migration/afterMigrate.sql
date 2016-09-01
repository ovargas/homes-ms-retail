DELETE FROM purchase_order;
DELETE FROM purchase_order_item;
DELETE FROM stock;
DELETE FROM product;
DELETE FROM store;

-- Stores
INSERT INTO store (name) VALUES ('Store One');
INSERT INTO store (name) VALUES ('Store Two');
INSERT INTO store (name) VALUES ('Store To Delete');

-- Products
INSERT INTO product (store_id, name, description, sku, price)
VALUES (1, 'Product One', 'Description of the product One', 'ABC11', 150.25);
INSERT INTO product (store_id, name, description, sku, price)
VALUES (1, 'Product Two', 'Description of the product Two', 'ABC12', 175.99);
INSERT INTO product (store_id, name, description, sku, price)
VALUES (1, 'Product Three', 'Description of the product Three', 'CFX12', 115.29);
INSERT INTO product (store_id, name, description, sku, price)
VALUES (1, 'Product to be deleted', 'Description of the product to be deleted', 'ABC13', 30.55);
INSERT INTO product (store_id, name, description, sku, price)
VALUES (1, 'Product Four', 'Description of the product Four', 'CFL12', 15.26);
INSERT INTO product (store_id, name, description, sku, price)
VALUES (1, 'Product Five', 'Description of the product Five', 'CFC19', 11.24);

-- Stock
INSERT INTO stock (product_id, stock_count) VALUES (2, 7);
INSERT INTO stock (product_id, stock_count) VALUES (3, 4);
INSERT INTO stock (product_id, stock_count) VALUES (5, 25);
INSERT INTO stock (product_id, stock_count) VALUES (6, 12);
