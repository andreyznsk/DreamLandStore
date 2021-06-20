CREATE TABLE chart (
                            id INT          IDENTITY NOT NULL,
                            customer_id INT NOT NULL,
                            prod_id INT NOT NULL,
                            FOREIGN KEY (customer_id) REFERENCES USERS(id),
                            FOREIGN KEY (prod_id) REFERENCES PRODUCTS(prod_id)

);


