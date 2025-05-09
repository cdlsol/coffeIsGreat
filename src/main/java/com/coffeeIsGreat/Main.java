package com.coffeeIsGreat;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class Main {
    public static void main(String[] args) {
        SparkSession spark = null;
        try {
            // Create Spark session
            spark = SparkSession.builder()
                    .appName("CoffeeETL")
                    .master("local") // Adjust for cluster
                    .getOrCreate();

            System.out.println("Coffee ETL in progress");

            // Fetch data
            FetchData fetchData = new FetchData(spark); // Pass Spark session
            String filePath = "/home/carlos/java-oop/coffee-etl/coffeeIsGreat/data/DatasetForCoffeeSales2.csv";
            Dataset<Row> coffeeSalesData = fetchData.readCoffeeSalesData(filePath);

            // Show original data
            System.out.println("Original Data:");
            coffeeSalesData.show();

            // Transform the data (fix headers)
            Dataset<Row> transformedData = TransformData.fixData(coffeeSalesData);

            // Show transformed data
            System.out.println("Transformed Data:");
            transformedData.show();

            // Split the data into dimensions and fact tables
            Dataset<Row> cityData = transformedData.select("city").distinct(); // Get unique cities
            Dataset<Row> productData = transformedData.select("category", "product", "unit_price").distinct(); // Get unique products
            Dataset<Row> salesData = transformedData.select("date","customer_id", "quantity", "sales_amount", "used_discount", "final_sales", "city", "product", "category");

            // Load into dimension tables
            LoadData.loadDimCity(cityData);
            LoadData.loadDimProduct(productData);
            LoadData.loadDimSales(salesData);

        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (spark != null) {
                spark.stop();
            }
        }
    }
}
