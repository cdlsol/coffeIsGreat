package handlers

import (
    "database/sql"
    "encoding/json"
    "net/http"
	"log"

    "coffee-api/models"
)

func HealthCheck(w http.ResponseWriter, r *http.Request) {
    w.Header().Set("Content-Type", "text/plain")
    w.WriteHeader(http.StatusOK)
    w.Write([]byte("OK"))
}

func GetProducts(db *sql.DB) http.HandlerFunc {
    return func(w http.ResponseWriter, r *http.Request) {
        w.Header().Set("Content-Type", "application/json")

        // Query the products dimension
        rows, err := db.Query("SELECT DISTINCT category, product, unit_price FROM dim_product")
		log.Println("Querying products from database...")
        if err != nil {
            http.Error(w, "Failed to retrieve products", http.StatusInternalServerError)
			log.Println("Error querying products:", err)
            return
        }
        defer rows.Close()

        // Parse the rows into a slice of Product
        var products []models.Product
        for rows.Next() {
            var product models.Product
            if err := rows.Scan(&product.Category, &product.Product, &product.UnitPrice); err != nil {
                http.Error(w, "Failed to parse product data", http.StatusInternalServerError)
				log.Println("Error scanning product:", err)
                return
            }
            products = append(products, product)
        }

        // Encode the products into JSON and send the response
        if err := json.NewEncoder(w).Encode(products); err != nil {
            http.Error(w, "Failed to encode products", http.StatusInternalServerError)
            return
        }
    }
}

func GetMonthlySales(db *sql.DB) http.HandlerFunc {
    return func(w http.ResponseWriter, r *http.Request) {
        w.Header().Set("Content-Type", "application/json")

        // Query the monthly sales data
        rows, err := db.Query("SELECT date_part('MONTH',date) as month, SUM(final_sales) as monthly_sales FROM dim_sales GROUP BY month order by month asc")
        log.Println("Querying monthly sales from database...")
        if err != nil {
            http.Error(w, "Failed to retrieve monthly sales", http.StatusInternalServerError)
            log.Println("Error querying monthly sales:", err)
            return
        }
        defer rows.Close()

        // Parse the rows into a slice of MonthlySales
        var monthlySales []models.MonthlySales
        for rows.Next() {
            var sale models.MonthlySales
            if err := rows.Scan(&sale.Month, &sale.MonthlySales); err != nil {
                http.Error(w, "Failed to parse monthly sales data", http.StatusInternalServerError)
                log.Println("Error scanning monthly sales:", err)
                return
            }
            monthlySales = append(monthlySales, sale)
        }

        // Encode the monthly sales into JSON and send the response
        if err := json.NewEncoder(w).Encode(monthlySales); err != nil {
            http.Error(w, "Failed to encode monthly sales", http.StatusInternalServerError)
            return
        }
    }
}

func GetCitySales(db *sql.DB) http.HandlerFunc {
    return func(w http.ResponseWriter, r *http.Request) {
    w.Header().Set("Content-Type", "application/json")

    // Query the monthly sales data
    rows, err := db.Query("SELECT SUM(final_sales) as net_sales, city FROM dim_sales GROUP BY city order by net_sales asc")
    log.Println("Querying net sales by city from database...")
    if err != nil {
        http.Error(w, "Failed to retrieve net sales by city", http.StatusInternalServerError)
        log.Println("Error querying net sales by city:", err)
        return
    }
    defer rows.Close()

    // Parse the rows into a slice of MonthlySales
    var citySales []models.CitySales
    for rows.Next() {
        var citySale models.CitySales
        if err := rows.Scan(&citySale.NetSales ,&citySale.City); err != nil {
            http.Error(w, "Failed to parse net sales by city data", http.StatusInternalServerError)
            log.Println("Error scanning net sales by city data:", err)
            return
        }
        citySales = append(citySales, citySale)
    }

    // Encode the monthly sales into JSON and send the response
    if err := json.NewEncoder(w).Encode(citySales); err != nil {
        http.Error(w, "Failed to encode net sales by city data", http.StatusInternalServerError)
        return
        }
    }
}

func GetCities(db *sql.DB) http.HandlerFunc {
    return func(w http.ResponseWriter, r *http.Request) {
    w.Header().Set("Content-Type", "application/json")

    // Query the monthly sales data
    rows, err := db.Query("SELECT DISTINCT city FROM dim_city order by city asc")
    log.Println("Querying cities from database...")
    if err != nil {
        http.Error(w, "Failed to retrieve cities", http.StatusInternalServerError)
        log.Println("Error querying cities:", err)
        return
    }
    defer rows.Close()

    // Parse the rows into a slice of MonthlySales
    var cities []models.Cities
    for rows.Next() {
        var city models.Cities
        if err := rows.Scan(&city.City); err != nil {
            http.Error(w, "Failed to parse cities", http.StatusInternalServerError)
            log.Println("Error scanning cities:", err)
            return
        }
        cities = append(cities, city)
    }

    // Encode the monthly sales into JSON and send the response
    if err := json.NewEncoder(w).Encode(cities); err != nil {
        http.Error(w, "Failed to encode cities", http.StatusInternalServerError)
        return
        }
    }
}