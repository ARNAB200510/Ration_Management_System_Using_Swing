CREATE Table Users(
id Integer AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(50) NOT NULL,
password VARCHAR(30) NOT NULL,
phone Varchar(10) NOT NULL,
user_type ENUM('Benificiary','Dealer') NOT NULL,
CHECK(length(password) >= 10),
CHECK(length(phone) = 10)
);

CREATE table  Emails(
email VARCHAR(50) Primary Key NOT NULL,
id Integer NOT NULL,
Foreign key(id) references users(id)
ON delete cascade
);

CREATE table Beneficiary(
id Integer UNIQUE NOT NULL Primary Key,
family_size int NOT NULL,
income_group ENUM('APL','BPL') NOT NULL,
address TEXT,
location varchar(20),
details_filled boolean default false,
Foreign key(id) references users(id)
ON delete cascade
);

Create table CardNumbers(
card_number varchar(20) Primary Key,
id Integer Unique NOT NULL,
foreign key(id) references users(id)
on delete cascade
);

CREATE table Dealers(
id Integer UNIQUE NOT NULL,
center_name varchar(100) NOT NULL,
center_code varchar(20) NOT NULL unique,
location  varchar(20),
stock_limit_tons INT default 1000,
is_validated boolean default false,
Foreign key(id) references users(id)
ON delete cascade,
CHECK (stock_limit_tons >= 1000)
);

CREATE TABLE items (
    item_id INT AUTO_INCREMENT PRIMARY KEY,
    item_name VARCHAR(50) NOT NULL,
    unit VARCHAR(20) NOT NULL  
);

CREATE TABLE orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    card_number VARCHAR(20),
    dealer_id INT,
    issue_date DATE,
    FOREIGN KEY (card_number) REFERENCES CardNumbers(card_number) ON DELETE CASCADE,
    FOREIGN KEY (dealer_id) REFERENCES dealers(id) ON DELETE CASCADE
);

CREATE TABLE order_items (
    order_id INT,
    item_id INT,
    quantity INT NOT NULL,
    PRIMARY KEY (order_id, item_id),
    FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE,
    FOREIGN KEY (item_id) REFERENCES items(item_id) ON DELETE CASCADE
);

Create table customers(
beneficiary_id Integer NOT NULL  Primary Key,
dealer_id Integer NOT NULL,
foreign key(beneficiary_id) references Beneficiary(id)
on delete cascade,
foreign key(dealer_id) references Dealers(id)
on delete cascade
);

CREATE TABLE stock (
    dealer_id INT,
    item_id INT,
    quantity INT NOT NULL DEFAULT 0,
    PRIMARY KEY (dealer_id, item_id),
    FOREIGN KEY (dealer_id) REFERENCES Dealers(id) ON DELETE CASCADE,
    FOREIGN KEY (item_id) REFERENCES items(item_id) ON DELETE CASCADE
);

Create view capacity AS 
select 
	dealer_id,
    center_name
    location,
	total_payable,
    stock_limit_tons,
    (stock_limit_tons * 1000 - total_payable) as capacity_left
from 
(
	select 
		c.dealer_id,
        sum(b.family_size) * 10 as total_payable 
	from 
		customers c 
        JOIN Beneficiary b ON b.id = c.Beneficiary_id 
	group by dealer_id
) temp 
JOIN Dealers d ON d.id = temp.dealer_id;