package main

import (
	"fmt"
	"io"
	"bufio"
	"os"
	"strings"
	"net/url"
	"encoding/xml"
	"net/http"
	"log"
	"io/ioutil"
	"encoding/json"
)

type reportType struct{
	Version xml.CharData		`xml:"version"`
	TermsOfService xml.CharData	`xml:"termsofService"`
	Problem myErrorType		`xml:"error"`
}
type myErrorType struct{
	TypeOfError xml.CharData `xml:"type"`
	Desciption xml.CharData `xml:"description"`
}
type reportTypeJson struct{
	Version        string  `json:"version"`;
	TermsOfService string;
}
func main() {
	fmt.Println("data is from WeatherUnderground.")
	fmt.Println("https://www.wunderground.com/")
	var state, city string
	state="MO"
	city="Saint_Joseph"
	baseURL := "http://api.wunderground.com/api/";
	apiKey := "REDACTED"
	var query string

	//set up the query
	query = baseURL+apiKey +
	"/conditions/q/"+
	url.QueryEscape(state)+ "/"+
	url.QueryEscape(city)+ ".xml"
	fmt.Println("The escaped query: "+query)

	response, err := http.Get(query)
	doErr(err, "After the GET")
	var body []byte
	body, err = ioutil.ReadAll(response.Body)
	doErr(err, "After Readall")
	fmt.Println(body);
	fmt.Printf("The body: %s\n",body)

	//Unmarshalling
	var report reportType
	xml.Unmarshal(body, &report)
	fmt.Printf("The Report: %s\n", report)
	fmt.Printf("The description is [%s]\n",report.Problem.Desciption)

	//Now marshal the data out in JSON
	var data []byte
	var output reportTypeJson
	output.Version = string(report.Version);
	//report.Version -> output.Version
	output.TermsOfService = string(report.TermsOfService)
	data,err = json.MarshalIndent(output,"","      ")
	doErr(err, "From marshalIndent")
	fmt.Printf("JSON output nicely formatted: \n%s\n",data)


}
func doErr( err error, message string){
	if err != nil{
		log.Panicf("ERROR: %s %s \n", message, err.Error())
	}


}