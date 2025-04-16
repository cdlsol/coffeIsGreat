package com.coffeeIsGreat;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class LoadData {

    // Load data into the dim_city table using custom SQL
    public static void loadDimCity(Dataset<Row> cityData) {
        String url = "jdbc:postgresql://localhost:5434/coffeeData";
        String user = "admin";
        String password = "admin";

        // Collect rows from the Spark Dataset
        List<Row> rows = cityData.collectAsList();

        // Batch insert with conflict handling (do nothing on conflict)
        String insertSQL = "INSERT INTO dim_city (city) VALUES (?) ON CONFLICT (city) DO NOTHING;";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            // Batch processing for better performance
            int batchSize = 100; // You can adjust this based on your needs
            int count = 0;

            for (Row row : rows) {
                pstmt.setString(1, row.getAs("city"));
                pstmt.addBatch(); // Add to batch

                if (++count % batchSize == 0) {
                    pstmt.executeBatch(); // Execute batch every `batchSize` rows
                }
            }

            // Execute remaining batch if there are any left
            pstmt.executeBatch();
            System.out.println("Data successfully loaded into dim_city.");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Alternative: Load data into the dim_city table using Spark's .write() method
//        cityData.write()
//                .format("jdbc")
//                .option("url", url)
//                .option("dbtable", "dim_city")
//                .option("user", user)
//                .option("password", password)
//                .option("driver", "org.postgresql.Driver")
//                .mode("append")  // Use append to avoid inserting duplicate records
//                .save();
//
//        System.out.println("Data successfully loaded into dim_city using Spark .write().");
    }

    // Load data into the dim_product table using custom SQL
    public static void loadDimProduct(Dataset<Row> productData) {
        String url = "jdbc:postgresql://localhost:5434/coffeeData";
        String user = "admin";
        String password = "admin";

        // Collect rows from the Spark Dataset
        List<Row> rows = productData.collectAsList();

        // Batch insert with conflict handling (do nothing on conflict)
        String insertSQL = "INSERT INTO dim_product (category, product, unit_price) VALUES (?, ?, ?) ON CONFLICT (category, product, unit_price) DO NOTHING;";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            // Batch processing for better performance
            int batchSize = 100; // You can adjust this based on your needs
            int count = 0;

            for (Row row : rows) {
                pstmt.setString(1, row.getAs("category"));
                pstmt.setString(2, row.getAs("product"));
                pstmt.setInt(3, row.getAs("unit_price"));
                pstmt.addBatch(); // Add to batch

                if (++count % batchSize == 0) {
                    pstmt.executeBatch(); // Execute batch every `batchSize` rows
                }
            }

            // Execute remaining batch if there are any left
            pstmt.executeBatch();
            System.out.println("Data successfully loaded into dim_product.");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Alternative: Load data into the dim_product table using Spark's .write() method
//        productData.write()
//                .format("jdbc")
//                .option("url", url)
//                .option("dbtable", "dim_product")
//                .option("user", user)
//                .option("password", password)
//                .option("driver", "org.postgresql.Driver")
//                .mode("append")  // Use append to avoid inserting duplicate records
//                .save();
//
//        System.out.println("Data successfully loaded into dim_product using Spark .write().");
    }

    // Load data into the dim_product table using custom SQL
    public static void loadDimSales(Dataset<Row> salesData) {
        String url = "jdbc:postgresql://localhost:5434/coffeeData";
        String user = "admin";
        String password = "admin";

        // Collect rows from the Spark Dataset
        List<Row> rows = salesData.collectAsList();

        // Batch insert with conflict handling (do nothing on conflict)
        String insertSQL = "INSERT INTO dim_sales (date, customer_id, quantity, sales_amount, used_discount, final_sales, city, product, category) VALUES (?,?, ?, ?, ?, ?, ?, ?, ?);";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            // Batch processing for better performance
            int batchSize = 100; // You can adjust this based on your needs
            int count = 0;

            for (Row row : rows) {
                pstmt.setDate(1, row.getAs("date"));
                pstmt.setString(2, row.getAs("customer_id"));
                pstmt.setInt(3, row.getAs("quantity"));
                pstmt.setInt(4, row.getAs("sales_amount"));
                pstmt.setString(5, row.getAs("used_discount"));
                pstmt.setInt(6, row.getAs("final_sales"));
                pstmt.setString(7, row.getAs("city"));
                pstmt.setString(8, row.getAs("product"));
                pstmt.setString(9, row.getAs("category"));

                pstmt.addBatch(); // Add to batch

                if (++count % batchSize == 0) {
                    pstmt.executeBatch(); // Execute batch every `batchSize` rows
                }
            }

            // Execute remaining batch if there are any left
            pstmt.executeBatch();
            System.out.println("Data successfully loaded into dim_sales.");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
