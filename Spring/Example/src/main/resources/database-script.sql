DROP TABLE IF EXISTS Category;

CREATE TABLE Category
(
    id int PRIMARY KEY IDENTITY,
    name        VARCHAR(50)    NOT NULL,
    description VARCHAR(255)
);

DROP TABLE IF EXISTS Hardware;

CREATE TABLE Hardware
(
    id int PRIMARY KEY IDENTITY,
    name        VARCHAR(50)    NOT NULL,
    description VARCHAR(255),
    price       DECIMAL(10, 2) NOT NULL,
    categoryId  INT,
    FOREIGN KEY (categoryId) REFERENCES Category(id)
);

INSERT INTO Category(name, description)
VALUES('CPU', 'New and used');

INSERT INTO Category(name, description)
VALUES('GPU', 'New and used');

INSERT INTO Hardware(name, description, price, categoryId)
VALUES('Intel Core i9-14900K', 'High-end desktop CPU', 45000, 1);

INSERT INTO Hardware(name, description, price, categoryId)
VALUES('AMD Ryzen 9 7950X', 'High-end desktop CPU', 42000, 1);

INSERT INTO Hardware(name, description, price, categoryId)
VALUES('NVIDIA GeForce RTX 4080', 'High-end gaming GPU', 80000, 2);

INSERT INTO Hardware(name, description, price, categoryId)
VALUES('AMD Radeon RX 7900 XTX', 'High-end gaming GPU', 70000, 2);

SELECT * FROM Hardware;

SELECT * FROM Category;