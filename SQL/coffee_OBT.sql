CREATE TABLE landing_stage (
    landing_id SERIAL PRIMARY KEY,
    sale_date DATE NOT NULL,
    customer_id INT NOT NULL,
    city INT NOT NULL,
    product INT NOT NULL,
    unit_price NUMERIC(10,2) NOT NULL,
    quantity INT NOT NULL,
    sales_amount NUMERIC(10,2) NOT NULL,
    used_discount BOOLEAN NOT NULL,
    discount_amount NUMERIC(10,2) NOT NULL,
    final_sales NUMERIC(10,2) NOT NULL
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
    UNIQUE(category, product, unit_price) -- Ensures no duplicate products
);


CREATE TABLE fact_sales (
    sale_date DATE NOT NULL,
    customer_id INT NOT NULL,
    city_id INT NOT NULL REFERENCES dim_city(city_id),
    product_id INT NOT NULL REFERENCES dim_product(product_id),
    unit_price NUMERIC(10,2) NOT NULL,
    quantity INT NOT NULL,
    sales_amount NUMERIC(10,2) NOT NULL,
    used_discount BOOLEAN NOT NULL,
    discount_amount NUMERIC(10,2) NOT NULL,
    final_sales NUMERIC(10,2) NOT NULL,
    PRIMARY KEY (sale_date, customer_id, city_id, product_id)
);
