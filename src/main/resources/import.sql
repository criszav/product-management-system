INSERT INTO t_users (firstname, lastname, username, email, password, role, created_at) VALUES ('Cristian', 'Zavala', 'czavala', 'czavala@gmail.com', '$2a$10$heu6fzRlZWTZaeSnAnp0s.qEjxUQbc7QBD2QfaFfinOSshr7sy89a', 'ADMIN', CURRENT_TIMESTAMP)
INSERT INTO t_users (firstname, lastname, username, email, password, role, created_at) VALUES ('Matias', 'Prado', 'mprado', 'mprado@gmail.com', '$2a$10$zvADbH.MODdAFkFPD22f4exf9BLzJx8fL1dYkSFbxGR8mu.D0rcii', 'ASSISTANT_ADMIN', CURRENT_TIMESTAMP)
INSERT INTO t_users (firstname, lastname, username, email, password, role, created_at) VALUES ('Cristiano', 'Ronaldo', 'cronaldo', 'cronaldo@gmail.com', '$2a$10$MBmXqZnPHaxQVFXho7I8EO7lyCkotDokYI3qpc95WSDIpnQI55XCC', 'CUSTOMER', CURRENT_TIMESTAMP)

INSERT INTO t_categories (name, status, created_at) VALUES ('Deporte', 'ENABLED', CURRENT_TIMESTAMP)
INSERT INTO t_categories (name, status, created_at) VALUES ('Electronica', 'ENABLED', CURRENT_TIMESTAMP)
INSERT INTO t_categories (name, status, created_at) VALUES ('Calzado', 'ENABLED', CURRENT_TIMESTAMP)

INSERT INTO t_products (name, price, image_url, status, category_id, created_at) VALUES ('Bote Kayak', 400, 'https://unsplash.com/es/fotos/fotografia-de-enfoque-selectivo-de-kayak-amarillo-6z5O9gIzn7Y', 'ENABLED', 1, CURRENT_TIMESTAMP)
INSERT INTO t_products (name, price, image_url, status, category_id, created_at) VALUES ('Balon de Futbol', 85, 'https://unsplash.com/es/fotos/tres-balones-de-futbol-blancos-y-negros-en-el-campo-JO19K0HDDXI', 'ENABLED', 1, CURRENT_TIMESTAMP)
INSERT INTO t_products (name, price, image_url, status, category_id, created_at) VALUES ('Celular Xiaomi Redmi', 220, 'https://unsplash.com/es/fotos/un-telefono-celular-negro-agE9Lf26h68', 'ENABLED', 2, CURRENT_TIMESTAMP)
INSERT INTO t_products (name, price, image_url, status, category_id, created_at) VALUES ('Tablet Apple', 350, 'https://unsplash.com/es/fotos/tableta-negra-sobre-textil-gris-doTjbfxrmRw', 'ENABLED', 2, CURRENT_TIMESTAMP)
INSERT INTO t_products (name, price, image_url, status, category_id, created_at) VALUES ('Zapatilla Adidas', 340, 'https://unsplash.com/es/fotos/par-de-zapatillas-adidas-ultra-boost-negras-mgweTPIa2Pc', 'ENABLED', 3, CURRENT_TIMESTAMP)
INSERT INTO t_products (name, price, image_url, status, category_id, created_at) VALUES ('Zapatos de Nieve', 420, 'https://unsplash.com/es/fotos/zapatos-de-senderismo-negros-y-marrones-en-suelo-cubierto-de-nieve-M8DqTxUg6GE', 'ENABLED', 3, CURRENT_TIMESTAMP)