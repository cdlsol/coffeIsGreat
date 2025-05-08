package main

import (
    "database/sql"
    "log"
    "net/http"

    _ "github.com/lib/pq" //PostgreSQL driver
	"coffee-api/routes"
)

func main() {
    db, err := sql.Open("postgres", "host=localhost port=5434 user=admin password=admin dbname=coffeeData sslmode=disable")
    if err != nil {
        log.Fatal("Failed to connect to the database:", err)
    }
    defer db.Close()

	router := routes.SetupRouter(db)

    log.Println("Server is running on port 8080")
    log.Fatal(http.ListenAndServe(":8080", router))
}