CREATE TABLE USER (
  user_id bigint(20) NOT NULL AUTO_INCREMENT,
  active int(11) NOT NULL,
  email varchar(255) NOT NULL,
  last_name varchar(255) NOT NULL,
  name varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  username varchar(255) NOT NULL,
  PRIMARY KEY (user_id),
  UNIQUE KEY (email),
  UNIQUE KEY (username)
);

CREATE TABLE ROLE (
  role_id bigint(20) NOT NULL AUTO_INCREMENT,
  role varchar(255) NOT NULL,
  PRIMARY KEY (role_id),
  UNIQUE KEY (role)
);

CREATE TABLE USER_ROLE (
  user_id bigint(20) NOT NULL,
  role_id bigint(20) NOT NULL,
  CONSTRAINT FOREIGN KEY (user_id) REFERENCES user (user_id),
  CONSTRAINT FOREIGN KEY (role_id) REFERENCES role (role_id)
);

CREATE TABLE PRODUCT (
  product_id bigint(20) NOT NULL AUTO_INCREMENT,
  description varchar(255) NOT NULL,
  name varchar(255) NOT NULL,
  price decimal(19,2) NOT NULL,
  quantity int(11) NOT NULL,
  PRIMARY KEY (product_id),
  UNIQUE KEY (name)
);

CREATE TABLE USER_PRODUCTS (
  invoice_id bigint(20) NOT NULL AUTO_INCREMENT,
  price decimal(19,2) NOT NULL,
  product_name varchar(255) NOT NULL,
  quantity int(11) NOT NULL,
  user_id varchar(255) NOT NULL,
  PRIMARY KEY (invoice_id),
  UNIQUE KEY (product_name)
) ;

CREATE TABLE product_imgs (
  img_id bigint(20) NOT NULL AUTO_INCREMENT,
  content_type varchar(255) NOT NULL,
  img_data longblob NOT NULL,
  img_title varchar(255) NOT NULL,
  product_id bigint(20) NOT NULL,
  PRIMARY KEY (img_id)
);
