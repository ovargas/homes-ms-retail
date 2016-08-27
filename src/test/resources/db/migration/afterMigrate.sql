DELETE FROM product;
DELETE FROM store;

-- Stores
INSERT INTO store (name) VALUES ('Store One');
INSERT INTO store (name) VALUES ('Store Two');
INSERT INTO store (name) VALUES ('Store To Delete');

-- Products
INSERT INTO product (store_id, name, description, sku, price) VALUES (1, 'Product One', 'Description of the product One', 'ABC11', 150.25);
INSERT INTO product (store_id, name, description, sku, price) VALUES (1, 'Product Two', 'Description of the product Two', 'ABC12', 175.99);
INSERT INTO product (store_id, name, description, sku, price) VALUES (1, 'Product Three', 'Description of the product Three', 'CFC12', 115.29);
INSERT INTO product (store_id, name, description, sku, price) VALUES (1, 'Product to be deleted', 'Description of the product to be deleted', 'ABC13', 30.55);
