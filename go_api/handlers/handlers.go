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