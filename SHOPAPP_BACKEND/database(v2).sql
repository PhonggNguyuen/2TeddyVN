CREATE DATABASE teddy;
USE teddy;

-- Khách hàng khi muốn mua hàng => phải đăng ký tài khoản => bảng users
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    fullname VARCHAR(100) DEFAULT '',
    phone_number VARCHAR(10) NOT NULL,
    address VARCHAR(200) DEFAULT '',
    password VARCHAR(100) NOT NULL DEFAULT '', -- mật khẩu đã mã hóa
    created_at DATETIME,
    updated_at DATETIME,
    is_active TINYINT(1) DEFAULT 1,
    date_of_birth DATE,
    facebook_account_id INT DEFAULT 0,
    google_account_id INT DEFAULT 0
);
ALTER TABLE users 
ADD COLUMN role_id INT;

CREATE TABLE roles (
    id INT PRIMARY KEY,
    name VARCHAR(20) NOT NULL
);

ALTER TABLE users 
ADD FOREIGN KEY (role_id) 
REFERENCES roles (id);

CREATE TABLE tokens(
    id int PRIMARY KEY AUTO_INCREMENT,
    token varchar(255) UNIQUE NOT NULL,
    token_type varchar(50) NOT NULL,
    expiration_date DATETIME,
    revoked tinyint(1) NOT NULL,
    expired tinyint(1) NOT NULL,
    user_id int,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE social_accounts(
    id INT PRIMARY KEY AUTO_INCREMENT,
    provider VARCHAR(20) NOT NULL COMMENT 'Tên nhà social network',
    provider_id VARCHAR(50) NOT NULL,
    email VARCHAR(150) NOT NULL COMMENT 'Email tài khoản',
    name VARCHAR(100) NOT NULL COMMENT 'Tên người dùng',
    user_id int,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE categories(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name varchar(100) NOT NULL DEFAULT '' COMMENT 'Tên danh mục'
);

CREATE TABLE products (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(350) COMMENT 'Tên sản phẩm',
    price FLOAT NOT NULL CHECK (price >= 0),
    thumbnail VARCHAR(300) DEFAULT '',
    description LONGTEXT,
    created_at DATETIME,
    updated_at DATETIME,
    category_id INT,
    FOREIGN KEY (category_id) REFERENCES categories (id)
);
CREATE TABLE product_images (
id INT PRIMARY KEY AUTO_INCREMENT,
product_id INT,
image_url Varchar(300) , 
FOREIGN KEY (product_id) REFERENCES products(id),
CONSTRAINT fk_product_images_product_id FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
    
);
CREATE TABLE orders (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES users(id),
    fullname VARCHAR(100) DEFAULT '',
    email VARCHAR(100) DEFAULT '',
    phone_number VARCHAR(20) NOT NULL,
    address VARCHAR(200) NOT NULL,
    note VARCHAR(100) DEFAULT '',
    order_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20),
    total_money FLOAT CHECK (total_money >= 0),
    shipping_method VARCHAR(100), 
    shipping_address VARCHAR(200), 
    shipping_date DATE, 
    tracking_number VARCHAR(100), 
    payment_method VARCHAR(100),
    active TINYINT(1)
);

ALTER TABLE orders
MODIFY COLUMN status ENUM('pending', 'processing', 'shipped', 'delivered', 'cancelled') 
COMMENT 'Trạng thái đơn hàng';

CREATE TABLE order_details (
    id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT,
    FOREIGN KEY (order_id) REFERENCES orders(id),
    product_id INT,
    FOREIGN KEY (product_id) REFERENCES products(id),
    price FLOAT CHECK(price >= 0),
    number_of_products INT CHECK(number_of_products > 0),
    total_money FLOAT CHECK(total_money >= 0),
    color VARCHAR(20) DEFAULT ''
);

CREATE TABLE product_reviews (
    id INT PRIMARY KEY AUTO_INCREMENT,
    product_id INT NOT NULL, -- Mã sản phẩm (Foreign Key)
    user_id INT NOT NULL, -- Mã người dùng (Foreign Key)
    review TEXT NOT NULL, -- Nội dung đánh giá
    rating INT NOT NULL CHECK (rating >= 1 AND rating <= 5), -- Đánh giá sao
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP, -- Thời gian đánh giá
    FOREIGN KEY (product_id) REFERENCES products(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);
CREATE TABLE variantproduct (
    id INT PRIMARY KEY AUTO_INCREMENT,
    color TEXT NOT NULL, -- Màu sắc
    size TEXT NOT NULL, -- Kích cỡ
    quantity BIGINT NOT NULL, -- Số lượng
    productId INT NOT NULL, -- Mã id sản phẩm (Foreign Key)
    FOREIGN KEY (productId) REFERENCES products(id)
);
-- 1.1.14. Bảng statusorder (Trạng thái đơn hàng)
CREATE TABLE statusorder (
    id INT PRIMARY KEY AUTO_INCREMENT,
    display CHAR(255) NOT NULL
    );

CREATE TABLE promotions (
    id INT PRIMARY KEY AUTO_INCREMENT,
    code VARCHAR(50) NOT NULL, -- Mã khuyến mãi
    description TEXT, -- Mô tả chi tiết
    discount_percentage FLOAT NOT NULL CHECK (discount_percentage > 0 AND discount_percentage <= 100), -- Tỷ lệ giảm giá
    start_date DATETIME, -- Ngày bắt đầu khuyến mãi
    end_date DATETIME, -- Ngày kết thúc khuyến mãi
    is_active TINYINT(1) DEFAULT 1 -- Trạng thái khuyến mãi (1 là hoạt động)
);
ALTER TABLE orders ADD COLUMN promotion_id INT;
ALTER TABLE orders ADD FOREIGN KEY (promotion_id) REFERENCES promotions(id);
CREATE TABLE cart_items (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    product_id INT,
    quantity INT CHECK (quantity > 0),
    added_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);
ALTER TABLE product_reviews
ADD FOREIGN KEY (user_id) 
REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE product_reviews
ADD FOREIGN KEY (product_id) 
REFERENCES products(id) ON DELETE CASCADE;

ALTER TABLE cart_items
ADD FOREIGN KEY (user_id) 
REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE cart_items
ADD FOREIGN KEY (product_id) 
REFERENCES products(id) ON DELETE CASCADE;
ALTER TABLE orders
ADD COLUMN status_order_id INT;
ALTER TABLE orders
ADD FOREIGN KEY (status_order_id) REFERENCES statusorder(id);
ALTER TABLE variantproduct
ADD CONSTRAINT fk_variantproduct_productId
FOREIGN KEY (productId) REFERENCES products(id) ON DELETE CASCADE;




