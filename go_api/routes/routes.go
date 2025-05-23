package routes

import (
	// "net/http"
	"database/sql"
	"github.com/gorilla/mux"

	"coffee-api/handlers"
)

func SetupRouter(db *sql.DB) *mux.Router {
	router := mux.NewRouter()

	// Routes for Coffee API
	router.HandleFunc("/health", handlers.HealthCheck).Methods("GET")
	router.HandleFunc("/products", handlers.GetProducts(db)).Methods("GET")
	router.HandleFunc("/monthly-sales", handlers.GetMonthlySales(db)).Methods("GET")
	router.HandleFunc("/city-sales", handlers.GetCitySales(db)).Methods("GET")
	router.HandleFunc("/cities", handlers.GetCities(db)).Methods("GET")
	

	return router
}