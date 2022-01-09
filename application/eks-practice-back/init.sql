DROP TABLE IF EXISTS seller;
DROP TABLE IF EXISTS product_inventory;
DROP TABLE IF EXISTS product_variant;
DROP TABLE IF EXISTS product;
CREATE TABLE seller
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY ,
    identifier VARCHAR(150) NOT NULL,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(150) NOT NULL,
    birthday DATE NOT NULL,
    sex VARCHAR(20) NOT NULL,
    local VARCHAR(30) NOT NULL,
    role VARCHAR(50) NOT NULL,
    cell_phone_number VARCHAR(100) NOT NULL,
    mobile_carrier VARCHAR(50) NOT NULL,
    required_agreement BIT(1) NOT NULL DEFAULT 1,
    required_agree_at DATETIME NOT NULL DEFAULT NOW(),
    marketing_agreement BIT(1) NOT NULL DEFAULT 0,
    marketing_agree_at DATETIME NOT NULL DEFAULT NOW(),
    last_modified_at DATETIME NOT NULL DEFAULT NOW(),
    created_at DATETIME NOT NULL DEFAULT NOW()
) ENGINE = InnoDB
DEFAULT CHARSET = utf8mb4;
CREATE TABLE product
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    title           VARCHAR(255) NOT NULL,
    description     VARCHAR(255) NOT NULL,
    seo_title       VARCHAR(255) NOT NULL,
    seo_description VARCHAR(255) NOT NULL,
    media           JSON         DEFAULT NULL,
    option_names    JSON         DEFAULT NULL,
    status          VARCHAR(100) DEFAULT 'DRAFT'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE product_variant
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id    BIGINT         NOT NULL,
    sku           VARCHAR(255)   NOT NULL,
    price         DECIMAL(19, 2) NOT NULL,
    option_values JSON DEFAULT NULL,
    FOREIGN KEY (product_id) REFERENCES product (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE product_inventory
(
    id                 BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_variant_id BIGINT NOT NULL,
    available_quantity BIGINT DEFAULT 0,
    tracked            BIT(1) DEFAULT 0,
    FOREIGN KEY (product_variant_id) REFERENCES product_variant (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;