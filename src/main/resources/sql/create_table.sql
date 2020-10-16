USE shopdb;

CREATE TABLE mst_user (
 id int(11) PRIMARY KEY AUTO_INCREMENT,
 user_name VARCHAR(32) NOT NULL UNIQUE,
 password VARCHAR(16) NOT NULL,
 family_name VARCHAR(255) NOT NULL,
 first_name VARCHAR(255) NOT NULL,
 family_name_kana VARCHAR(255) NOT NULL,
 first_name_kana VARCHAR(255) NOT NULL,
 gender TINYINT DEFAULT 0,
 created_at TIMESTAMP NOT NULL default now(),
 updated_at TIMESTAMP NOT NULL default now()
);

CREATE TABLE mst_category (
 id int(11) PRIMARY KEY AUTO_INCREMENT,
 category_name VARCHAR(255) NOT NULL,
 category_description VARCHAR(255),
 created_at TIMESTAMP NOT NULL default now(),
 updated_at TIMESTAMP NOT NULL default now()
);

CREATE TABLE mst_product (
 id int(11) PRIMARY KEY AUTO_INCREMENT,
 product_name VARCHAR(255) NOT NULL UNIQUE,
 product_name_kana VARCHAR(255) NOT NULL UNIQUE,
 product_description VARCHAR(255),
 category_id int(11) NOT NULL,
 price int(11) NOT NULL,
 image_full_path VARCHAR(255) NOT NULL,
 release_date VARCHAR(10),
 release_company VARCHAR(255),
 created_at TIMESTAMP NOT NULL default now(),
 updated_at TIMESTAMP NOT NULL default now(),
 FOREIGN KEY(category_id) REFERENCES mst_category(id)
);

CREATE TABLE mst_destination (
 id int(11) PRIMARY KEY AUTO_INCREMENT,
 user_id int(11) NOT NULL,
 family_name VARCHAR(255) NOT NULL,
 first_name VARCHAR(255) NOT NULL,
 tel_number VARCHAR(13),
 address VARCHAR(255) NOT NULL,
 status TINYINT  NOT NULL DEFAULT 1,
 created_at TIMESTAMP NOT NULL default now(),
 updated_at TIMESTAMP NOT NULL default now(),
 FOREIGN KEY(user_id) REFERENCES mst_user(id)
);

CREATE TABLE tbl_cart (
 id int(11) PRIMARY KEY AUTO_INCREMENT,
 user_id int(11) NOT NULL,
 product_id int(11) NOT NULL,
 product_count int(11) NOT NULL,
 created_at TIMESTAMP NOT NULL default now(),
 updated_at TIMESTAMP NOT NULL default now(),
 FOREIGN KEY(product_id) REFERENCES mst_product(id)
);

CREATE TABLE tbl_purchase_history (
 id int(11) PRIMARY KEY AUTO_INCREMENT,
 user_id int(11) NOT NULL,
 product_id int(11) NOT NULL,
 product_count int(11) NOT NULL,
 price int(11) NOT NULL,
 destination_id int(11) NOT NULL,
 status TINYINT  NOT NULL DEFAULT 1,
 purchased_at TIMESTAMP NOT NULL default now(),
 created_at TIMESTAMP NOT NULL default now(),
 updated_at TIMESTAMP NOT NULL default now(),
 FOREIGN KEY(user_id) REFERENCES mst_user(id),
 FOREIGN KEY(product_id) REFERENCES mst_product(id),
 FOREIGN KEY(destination_id) REFERENCES mst_destination(id)
);
