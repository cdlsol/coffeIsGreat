
--Create dimensions
CREATE TABLE dim_sales (
    sale_id SERIAL PRIMARY KEY,
    date DATE NOT NULL,
    customer_id TEXT NOT NULL,
    quantity INT NOT NULL,
    sales_amount NUMERIC(10,2),
    used_discount TEXT NOT NULL,
    final_sales NUMERIC(10,2),
    city TEXT NOT NULL,
    product TEXT NOT NULL,
    category TEXT NOT NULL
);

CREATE TABLE dim_city (
    city_id SERIAL PRIMARY KEY,
    city TEXT UNIQUE NOT NULL
);


CREATE TABLE dim_product (
    product_id SERIAL PRIMARY KEY,
    category TEXT NOT NULL,
    product TEXT NOT NULL,
    unit_price NUMERIC(10,2) NOT NULL,
    UNIQUE(category, product, unit_price)
);


CREATE TABLE fact_sales (
    fact_id SERIAL PRIMARY KEY,
    sale_id INT NOT NULL REFERENCES dim_sales(sale_id),
    customer_id TEXT NOT NULL, 
    city_id INT NOT NULL REFERENCES dim_city(city_id),
    product_id INT NOT NULL REFERENCES dim_product(product_id),
    unit_price NUMERIC(10,2) NOT NULL,
    quantity INT NOT NULL,
    sales_amount NUMERIC(10,2) NOT NULL,
    used_discount BOOLEAN NOT NULL,
    discount_amount NUMERIC(10,2) NOT NULL,
    final_sales NUMERIC(10,2) NOT NULL
);

--Insert data into fact table from dimensions
INSERT INTO fact_sales (
    sale_id,
    customer_id,
    city_id,
    product_id,
    unit_price,
    quantity,
    sales_amount,
    used_discount,
    discount_amount,
    final_sales
)
SELECT
    s.sale_id,
    s.customer_id,
    c.city_id,
    p.product_id,
    p.unit_price,
    s.quantity,
    s.sales_amount,
    CASE
        WHEN LOWER(s.used_discount) IN ('yes', 'true', '1') THEN TRUE
        ELSE FALSE
    END AS used_discount,
    s.sales_amount - s.final_sales AS discount_amount,
    s.final_sales
FROM dim_sales s
JOIN dim_city c ON c.city = s.city
JOIN dim_product p ON p.product = s.product AND p.category = s.category