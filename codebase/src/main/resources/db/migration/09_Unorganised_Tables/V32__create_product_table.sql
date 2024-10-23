DROP TABLE IF EXISTS product;
CREATE TABLE product(
    productId INT PRIMARY KEY AUTO_INCREMENT,
    itemName varchar(255) not null,
    description VARCHAR(255) not null,
    quantity INT not null default  0,
    quantityInCarts INT not null default 0,
    price FLOAT not null,
    specialPrice FLOAT
);