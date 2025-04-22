# Coffee is Great ☕

A data pipeline project that uses **Apache Spark (Java)** to read data from a CSV file and load it into a **containerized PostgreSQL** database. After the data is loaded, a **Flask REST API** is built to interact with the database.

---

## 🚀 Project Overview

This project automates the process of:
1. Reading structured data from a CSV using Apache Spark.
2. Uploading and transforming it into a PostgreSQL database container.
3. Serving the data through a lightweight Flask REST API.

---

## 🧱 Tech Stack

- **Apache Spark (Java)** – data processing
- **PostgreSQL** – containerized relational database
- **Flask (Python)** – RESTful API service
- **Docker** – containerization of the PostgreSQL service

---

## 📁 Project Structure

coffee-is-great/ 
├── sql/ # SQL script and data model
├── src/main/java/com/coffeeIsGreat # Apache Spark job (Java code) 
├── api/ # Flask REST API 
├── data/ # Input CSV files 
├── dockerfile # PostgreSQL container configuration 
├── pom.xml # Maven dependencies
├── README.md #This file
├── run.py #Flask API main file

---

🧪 API Usage
Once the API is running locally, you can access the following endpoints:
`GET /healthcheck`
Check if the API is running properly.

`GET /city`
Returns a list of cities available in the database.

`GET /products`
Returns a list of coffee products stored in the database.

---

🗄 SQL Data Model
Check the /sql directory for the SQL schema and table definitions used by the PostgreSQL database.

---

📫 Author
Carlos Lopez
📧 carloslopez.cl265@gmail.com

