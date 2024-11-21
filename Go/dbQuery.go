package main

import (
	"database/sql"
	f "fmt"
	_ "github.com/go-sql-driver/mysql"
	"log"
)

func main() {
	f.Println("Starting")
	db, err := sql.Open("mysql", "REDACTED_NAME:REDACTED_PASS@tcp(REDACTED_URL:PORT)/junk")
	if err != nil {
		panic(err.Error())
	}
	defer db.Close()

	err = db.Ping()
	if err != nil {
		panic(err.Error())
	} else {
		f.Println("Don't Panic.  Ping must have worked!")
	}
	var number int
	var postalCode string
	f.Print("How many records?  ")
	f.Scanln(&number)
	f.Print("What postal Code?  ")
	f.Scanln(&postalCode)
	f.Printf("Attempting to print %v records from zip code %v\n\n",
		number, postalCode)

	var(
		givenName, surName string
	)
	rows, rowErr := db.Query("SELECT givenName, surName FROM people WHERE zip LIKE ? LIMIT ?" ,
	postalCode, number)
	if rowErr != nil {
		log.Fatal(rowErr.Error())
	}
	defer rows.Close()

	for rows.Next() {
		nextErr := rows.Scan(&givenName, &surName)
		if nextErr != nil{
			log.Fatal(nextErr.Error())
		}
		f.Println(givenName, surName)
	}

	f.Println("Done")
}
