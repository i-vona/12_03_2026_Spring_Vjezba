INSERT INTO Category(name, description)
VALUES('CPU', 'New and used');

INSERT INTO Category(name, description)
VALUES('GPU', 'New and used');

INSERT INTO Category(name, description)
VALUES('MBO', 'New and used');

INSERT INTO Category(name, description)
VALUES('RAM', 'New and used');

INSERT INTO Category(name, description)
VALUES('Storage', 'New and used');

INSERT INTO Category(name, description)
VALUES('Other', 'New and used');

INSERT INTO Hardware(name, description, price, category_id)
VALUES('Intel Core i9-14900K', 'High-end desktop CPU', 45000, 1);

INSERT INTO Hardware(name, description, price, category_id)
VALUES('AMD Ryzen 9 7950X', 'High-end desktop CPU', 42000, 1);

INSERT INTO Hardware(name, description, price, category_id)
VALUES('NVIDIA GeForce RTX 4080', 'High-end gaming GPU', 80000, 2);

INSERT INTO Hardware(name, description, price, category_id)
VALUES('AMD Radeon RX 7900 XTX', 'High-end gaming GPU', 70000, 2);


INSERT INTO authority(authority_name) VALUES('ROLE_ADMIN');
INSERT INTO authority(authority_name) VALUES('ROLE_USER');

INSERT INTO users(username, password) VALUES('admin', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG');
INSERT INTO users(username, password) VALUES('user', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG');

INSERT INTO users_authority(user_id, authority_id) VALUES(1, 1);
INSERT INTO users_authority(user_id, authority_id) VALUES(2, 2);