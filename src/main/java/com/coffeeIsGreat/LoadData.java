package com.coffeeIsGreat;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public class LoadData {

    // Load data into the dim_city table
    public static void loadDimCity(Dataset<Row> cityData) {
        String url = "jdbc:postgresql://localhost:5434/coffeeData";
        String user = "admin";
        String password = "admin";

        cityData.write()
                .format("jdbc")
                .option("url", url)
                .option("dbtable", "dim_city")
                .option("user", user)
                .option("password", password)
                .option("driver", "org.postgresql.Driver")
                .mode("append")  // Use append to avoid inserting duplicate records
                .save();

        System.out.println("Data successfully loaded into dim_city.");
    }

    // Load data into the dim_product table
    public static void loadDimProduct(Dataset<Row> productData) {
        String url = "jdbc:postgresql://localhost:5434/coffeeData";
        String user = "admin";
        String password = "admin";

        productData.write()
                .format("jdbc")
                .option("url", url)
                .option("dbtable", "dim_product")
                .option("user", user)
                .option("password", password)
                .option("driver", "org.postgresql.Driver")
                .mode("append")  // Use append for non-duplicate inserts
                .save();

        System.out.println("Data successfully loaded into dim_product.");
    }

    // Load data into the fact_sales table
    public static void loadFactSales(Dataset<Row> salesData) {
        String url = "jdbc:postgresql://localhost:5434/coffeeData";
        String user = "admin";
        String password = "admin";

        salesData.write()
                .format("jdbc")
                .option("url", url)
                .option("dbtable", "fact_sales")
                .option("user", user)
                .option("password", password)
                .option("driver", "org.postgresql.Driver")
                .mode("append")  // Use append mode for fact table data
                .save();

        System.out.println("Data successfully loaded into fact_sales.");
    }
}
