-- 0. DB 생성
CREATE DATABASE sgrw_erp
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

-- 1. DB 선택
USE sgrw_erp;

-- 2. accounts (계정과목)
CREATE TABLE accounts (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  code VARCHAR(20) NOT NULL UNIQUE,
  name VARCHAR(100) NOT NULL,
  type ENUM('ASSET','LIABILITY','EQUITY','REVENUE','EXPENSE') NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO accounts (code, name, type) VALUES
-- 자산
('1000', '현금', 'ASSET'),
('1100', '외상매출금', 'ASSET'),
('1200', '재고자산', 'ASSET'),
('2200', '부가세대급금', 'ASSET'),
-- 부채
('2000', '외상매입금', 'LIABILITY'),
('2100', '부가세예수금', 'LIABILITY'),
-- 자본
('3000', '자본금', 'EQUITY'),
-- 수익
('4000', '매출', 'REVENUE'),
-- 비용
('5000', '매출원가', 'EXPENSE'),
('5100', '급여', 'EXPENSE');

-- 3. journal_entries (전표)
CREATE TABLE journal_entries (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  date DATE NOT NULL,
  description VARCHAR(255),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO journal_entries (date, description)
VALUES (CURDATE(), '홍차 티백 판매');

-- 생성된 id 확인
SELECT LAST_INSERT_ID();

-- 4. journal_details (분개)
CREATE TABLE journal_details (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  journal_entry_id BIGINT NOT NULL,
  account_id BIGINT NOT NULL,
  debit DECIMAL(15,2) DEFAULT 0,
  credit DECIMAL(15,2) DEFAULT 0,
  CONSTRAINT fk_journal_entry FOREIGN KEY (journal_entry_id) REFERENCES journal_entries(id),
  CONSTRAINT fk_account FOREIGN KEY (account_id) REFERENCES accounts(id)
);

-- 차변 (현금)
INSERT INTO journal_details (journal_entry_id, account_id, debit, credit)
VALUES (1, 1, 11000, 0);

-- 대변 (매출)
INSERT INTO journal_details (journal_entry_id, account_id, debit, credit)
VALUES (1, 5, 0, 10000);

-- 대변 (부가세)
INSERT INTO journal_details (journal_entry_id, account_id, debit, credit)
VALUES (1, 3, 0, 1000);

-- 5. products (상품)
CREATE TABLE products (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  price DECIMAL(15,2) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 상품
INSERT INTO products (name, price) VALUES
('홍차 티백', 5000),
('허브티', 7000);

-- 6. inventories (재고)
CREATE TABLE inventories (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  product_id BIGINT NOT NULL UNIQUE,
  quantity INT DEFAULT 0,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT fk_product_inventory FOREIGN KEY (product_id) REFERENCES products(id)
);

-- 재고
INSERT INTO inventories (product_id, quantity) VALUES
(1, 100),
(2, 50);

-- 7. transactions (거래)
CREATE TABLE transactions (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  type ENUM('SALE','PURCHASE') NOT NULL,
  product_id BIGINT NOT NULL,
  quantity INT NOT NULL,
  price DECIMAL(15,2) NOT NULL,
  total_amount DECIMAL(15,2) NOT NULL,
  vat_amount DECIMAL(15,2) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_product_transaction FOREIGN KEY (product_id) REFERENCES products(id)
);

INSERT INTO transactions (type, product_id, quantity, price, total_amount, vat_amount)
VALUES ('SALE', 1, 2, 5000, 10000, 1000);

--  거래 구분
ALTER TABLE transactions
ADD payment_type ENUM('CASH','CREDIT') NOT NULL;

SHOW TABLES;