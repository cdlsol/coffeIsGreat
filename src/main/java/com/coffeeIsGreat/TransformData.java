package com.coffeeIsGreat;

import org.apache.spark.sql.*;
import org.apache.spark.sql.functions;

public class TransformData {

    // Fix headers by replacing spaces with underscores
    public static Dataset<Row> fixData(Dataset<Row> df) {
        //Transform column names to remove spaces and replace with underscores
        for (String colName : df.columns()) {
            String newColName = colName.replace(" ", "_");
            df = df.withColumnRenamed(colName, newColName); // Rename columns
        }

        //Date transformation: convert the "Date" column to a proper DateType
        String dateFormatPattern = "MM/dd/yyyy"; // Adjust to match your date format
        df = df.withColumn("Date", functions.to_date(df.col("Date"), dateFormatPattern));

        return df;
    }
}
