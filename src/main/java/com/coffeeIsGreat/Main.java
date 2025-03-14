package com.coffeeIsGreat;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class Main {
    public static void main(String[] args) {
        SparkSession spark = null;
        try {
            //Create Spark session
            spark = SparkSession.builder()
                    .appName("CoffeeETL")
                    .master("local") //To be adjusted depending on cluster set-up
                    .getOrCreate();

            System.out.println("Coffee ETL in progress");

            //Fetch data
            FetchData fetchData = new FetchData(spark); //Pass Spark session to fetch data
            String filePath = "/home/carlos/java-oop/coffee-etl/coffeeIsGreat/data/DatasetForCoffeeSales2.csv";
            Dataset<Row> coffeeSalesData = fetchData.readCoffeeSalesData(filePath);

            //Show original data
            System.out.println("Original Data:");
            coffeeSalesData.show();

            //Transform the data (fix headers)
            Dataset<Row> transformedData = TransformData.fixData(coffeeSalesData);

            //Show transformed data
            System.out.println("Transformed Data:");
            transformedData.show();

        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (spark != null) {
                // Stop Spark session
                spark.stop();
            }
        }
    }
}
