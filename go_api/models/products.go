package models

type Product struct {
	Category	string		`json:"category"`
	Product		string		`json:"product"`
	UnitPrice	float64 	`json:"unit_price"`
}	