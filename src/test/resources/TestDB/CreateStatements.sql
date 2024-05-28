DROP TABLE IF EXISTS FIXED_DEPOSIT_ACCOUNT CASCADE;

CREATE TABLE IF NOT EXISTS public.fixed_deposit_account
(
 account_id INT AUTO_INCREMENT PRIMARY KEY,
    principal DOUBLE NOT NULL,
    term_in_months INT NOT NULL,
    interest_rate DOUBLE NOT NULL,
    maturity_amount DOUBLE NOT NULL,
    account_number VARCHAR(255) NOT NULL,
    branch VARCHAR(255) NOT NULL,
    added_by VARCHAR(255) NOT NULL,
    updated_by VARCHAR(255) NOT NULL,
    added_on DATETIME NOT NULL,
    updated_on DATETIME NOT NULL,
    customer_customer_id integer
    
);

DROP TABLE IF EXISTS _user CASCADE;
CREATE TABLE IF NOT EXISTS public._user(
user_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('MANAGER', 'HELPDESK') NOT NULL
);

DROP TABLE IF EXISTS customer CASCADE;
CREATE TABLE IF NOT EXISTS public.customer
(
customer_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_first_name VARCHAR(255) NOT NULL,
    customer_last_name VARCHAR(255) NOT NULL,
    customer_email VARCHAR(255) NOT NULL UNIQUE,
    customer_password VARCHAR(255) NOT NULL,
    contact BIGINT NOT NULL,
    added_by VARCHAR(255) NOT NULL,
    added_on TIMESTAMP NOT NULL,
    updated_by VARCHAR(255),
    updated_on TIMESTAMP
    
);
    