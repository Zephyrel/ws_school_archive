package main

import (
	"fmt"
	"io/ioutil"
	"net/http"
	//"encoding/json"
	//"encoding/xml"
	"log"
)

func main(){
	var query, CRN string
	baseURL := "REDACTED";

	//input
	fmt.Println("Enter in a 5-digit CRN. Ex. 13398, or 13794")
	fmt.Print("CRN: ")
	fmt.Scanln(&CRN)
	fmt.Println("You entered: "+CRN)
	
	//Write query
	query = baseURL+CRN
	fmt.Println("XML query:\t"+query)
	var body []byte
	response, err := http.Get(query)
	body, err = ioutil.ReadAll(response.Body)
	doErr( err, "Error")
	fmt.Printf("\nBody:\t%s\n",body)
}

func doErr( err error, message string){
	if err != nil{
		log.Panicf("ERROR: %s %s \n", message, err.Error())
	}
}

