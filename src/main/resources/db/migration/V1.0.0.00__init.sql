CREATE TABLE store (
    store_id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'The id of the store',
    name VARCHAR(50) NOT NULL COMMENT 'The name of the store'
);

CREATE TABLE product (
    product_id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'The id of the product',
    store_id BIGINT NOT NULL,
    name VARCHAR(50) NOT NULL COMMENT 'The name of the product',
    description TEXT NOT NULL COMMENT 'Description of the product',
    sku VARCHAR(10) NOT NULL COMMENT 'Product SKU code',
    price DECIMAL(20, 2) NOT NULL COMMENT 'Price of the product',
    FOREIGN KEY (store_id) REFERENCES store (store_id)
);

CREATE TABLE stock (
    --stock_id BIGINT AUTO_INCREMENT COMMENT  'Unique Id for the stock',
    product_id BIGINT NOT NULL COMMENT 'The id of the product',
    stock_count BIGINT NOT NULL COMMENT 'Total count in stock',
    FOREIGN KEY (product_id) REFERENCES product (product_id),
    PRIMARY KEY (product_id)
);
