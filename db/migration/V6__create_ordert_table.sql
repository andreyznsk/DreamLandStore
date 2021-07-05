CREATE TABLE orders (
                       order_id         IDENTITY NOT NULL,
                       customer_id   INT NOT NULL DEFAULT '-1',
                       delivery_address VARCHAR(30),
                       is_ordered BOOLEAN DEFAULT false,
                       is_paid BOOLEAN DEFAULT false,
                       is_delivered  BOOLEAN DEFAULT false,
                       order_date DATE NOT NULL ,
                       total_price NUMERIC(10,2),
                       FOREIGN KEY (customer_id) REFERENCES USERS(id)
);

CREATE TABLE order_details (
                        id IDENTITY NOT NULL,
                        order_id INT,
                        prod_id INT,
                        FOREIGN KEY (order_id) references ORDERS(order_id),
                        FOREIGN KEY (prod_id) REFERENCES PRODUCTS(prod_id)
);