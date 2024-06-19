INSERT INTO t_users (firstname, lastname, username, email, password, role, created_at) VALUES ('Cristian', 'Zavala', 'czavala', 'czavala@gmail.com', '$2a$10$heu6fzRlZWTZaeSnAnp0s.qEjxUQbc7QBD2QfaFfinOSshr7sy89a', 'ADMIN', CURRENT_TIMESTAMP)
INSERT INTO t_users (firstname, lastname, username, email, password, role, created_at) VALUES ('Matias', 'Prado', 'mprado', 'mprado@gmail.com', '$2a$10$zvADbH.MODdAFkFPD22f4exf9BLzJx8fL1dYkSFbxGR8mu.D0rcii', 'ASSISTANT_ADMIN', CURRENT_TIMESTAMP)
INSERT INTO t_users (firstname, lastname, username, email, password, role, created_at) VALUES ('Cristiano', 'Ronaldo', 'cronaldo', 'cronaldo@gmail.com', '$2a$10$MBmXqZnPHaxQVFXho7I8EO7lyCkotDokYI3qpc95WSDIpnQI55XCC', 'CUSTOMER', CURRENT_TIMESTAMP)

INSERT INTO t_categories (name, status, created_at) VALUES ('Deporte', 'ENABLED', CURRENT_TIMESTAMP)
INSERT INTO t_categories (name, status, created_at) VALUES ('Electronica', 'ENABLED', CURRENT_TIMESTAMP)
INSERT INTO t_categories (name, status, created_at) VALUES ('Calzado', 'ENABLED', CURRENT_TIMESTAMP)

INSERT INTO t_products (name, price, status, category_id, created_at) VALUES ('Bote Kayak', 400, 'ENABLED', 1, CURRENT_TIMESTAMP)
INSERT INTO t_products (name, price, status, category_id, created_at) VALUES ('Balon de Futbol', 85, 'ENABLED', 1, CURRENT_TIMESTAMP)
INSERT INTO t_products (name, price, status, category_id, created_at) VALUES ('Celular Xiaom Redmi', 220, 'ENABLED', 2, CURRENT_TIMESTAMP)
INSERT INTO t_products (name, price, status, category_id, created_at) VALUES ('Table Apple', 350, 'ENABLED', 2, CURRENT_TIMESTAMP)
INSERT INTO t_products (name, price, status, category_id, created_at) VALUES ('Zapatilla Adidas', 340, 'ENABLED', 3, CURRENT_TIMESTAMP)
INSERT INTO t_products (name, price, status, category_id, created_at) VALUES ('Zapatos de Nieve', 420, 'ENABLED', 3, CURRENT_TIMESTAMP)