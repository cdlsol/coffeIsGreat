package com.coffeeIsGreat;

import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;

public class FetchData {

    private final SparkSession spark;

    //Constructor accepts Spark session as a parameter
    public FetchData(SparkSession spark) {
        spark.conf().set("spark.sql.legacy.timeParserPolicy", "LEGACY");
        this.spark = spark;
    }

    //Define csv schema
    private static final StructType schema = new StructType()
            .add("Date", DataTypes.StringType) //Treat as string initially
            .add("Customer_ID", DataTypes.StringType)
            .add("City", DataTypes.StringType)
            .add("Category", DataTypes.StringType)
            .add("Product", DataTypes.StringType)
            .add("Unit_Price", DataTypes.IntegerType)
            .add("Quantity", DataTypes.IntegerType)
            .add("Sales_Amount", DataTypes.IntegerType)
            .add("Used_Discount", DataTypes.StringType)
            .add("Discount_Amount", DataTypes.IntegerType)
            .add("Final_Sales", DataTypes.IntegerType);

    //Read CSV data with a configurable file path and apply schema
    public Dataset<Row> readCoffeeSalesData(String filePath) {
        try {
            // Read CSV data using the schema
            Dataset<Row> data = spark.read()
                    .option("header", "true") // Keep column names
                    .option("inferSchema", "true") // Let Spark infer the schema for unknown columns
                    .schema(schema) // Apply schema
                    .csv(filePath); // Read CSV file from the provided path

            //Show a preview of the data (for debugging)
            data.show(5);
            return data;

        } catch (Exception e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    // Stop Spark session
    public void stopSpark() {
        if (spark != null) {
            spark.stop();
        }
    }
}
