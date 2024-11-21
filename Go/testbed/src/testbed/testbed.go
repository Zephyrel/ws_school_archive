package main

import (
	"fmt"
	"io/ioutil"
	"net/http"
	"encoding/xml"
	"encoding/json"
	"log"
)
/*type report struct{
	coord[] float 'json:"coord"' 'xml:"coord"'{
		"lon":-94.85,
		"lat":39.77
	"weather":[{
		"id":800,
		"main":"Clear",
		"description":"clear sky",
		"icon":"01d"}],
	"base":"stations","main":{
		"temp":74.1,
		"pressure":1018,
		"humidity":23,
		"temp_min":71.6,
		"temp_max":75.99
	},
	"visibility":16093,
	"wind":{"speed":5.82},
	"clouds":{"all":1},
	"dt":1462479308,
	"sys":{
		"type":1,
		"id":1664,
		"message":0.0297,
		"country":"US",
		"sunrise":1462446812,
		"sunset":1462497542
	},
	"id":4407010,
	"name":"Saint Joseph",
	"cod":200
}*/
type reportXML struct{
<<<<<<< HEAD
	Name string `xml:"name"`
=======
	
>>>>>>> butt
}
type reportJSON struct{
	
}
func main(){
	var XMLquery, JSONquery, city string
	//var XMLresponse, JSONresponse string
	fmt.Println("Data and API is from OpenWeatherMap.")
	fmt.Println("http://openweathermap.org/\n")
	baseURL := "http://api.openweathermap.org/data/2.5/weather?q=";
	XMLmode := "&mode=xml"
	imperialMode := "&units=imperial"	//use this, for Fahrenheit
	//metricMode := "&units=metric"		//use this, for Celsius
	apiKey := "&appid=REDACTED"
	//Examples @ http://openweathermap.org/current
	//api.openweathermap.org/data/2.5/weather?q={city name}
	//api.openweathermap.org/data/2.5/weather?q={city name},{country code}
	//http://api.openweathermap.org/data/2.5/weather?q=London&appid=REDACTED
	
	//input
	fmt.Println("Enter a city, replacing any spaces between words with _")
	fmt.Print("City: ")
	fmt.Scanln(&city)
	fmt.Println("You entered: "+city)
	
	//Write queries
	JSONquery = baseURL+city+imperialMode+apiKey
	XMLquery = baseURL+city+imperialMode+XMLmode+apiKey
	fmt.Println("JSON query:\t"+JSONquery)
	fmt.Println("XML query:\t"+XMLquery)

	//Get Query - JSON
	var JSONbody, XMLbody []byte
	JSONresponse, err := http.Get(JSONquery)
<<<<<<< HEAD
	doErr(err, "Error")
=======
	doErr(err, "Afer")
	//fmt.Println(JSONbody);
>>>>>>> butt
	JSONbody, err = ioutil.ReadAll(JSONresponse.Body)
	fmt.Printf("\nJSONbody:\t%s",JSONbody)
	
	//Get Query - XML
	XMLresponse, err := http.Get(XMLquery)
	XMLbody, err = ioutil.ReadAll(XMLresponse.Body)
	fmt.Printf("\nXMLbody:\t%s\n",XMLbody)

	//Unmarshal XMLReport
	var XMLreport reportXML
	xml.Unmarshal(XMLbody, &XMLreport)
	fmt.Printf("\nThe XML Report: %s\n", XMLreport)
	//Marshal XML to JSON
	var XMLtoJSONdata []byte
	var XMLtoJSONoutput reportJSON
	//report.Version -> output.Version
	XMLtoJSONdata,err = json.MarshalIndent(XMLtoJSONoutput,"","      ")

	fmt.Printf("Formatted XML to JSON output: \n%s\n",XMLtoJSONdata)
	
	//Unmarshal JSONReport
	var JSONreport reportJSON
	json.Unmarshal(JSONbody, &JSONreport)
	fmt.Printf("\nThe JSON Report: %s\n", JSONreport)
	//Marshal JSON to XML
	var JSONtoXMLdata []byte
	var JSONtoXMLoutput reportXML
	//report.Version -> output.Version
	JSONtoXMLdata,err = json.MarshalIndent(JSONtoXMLoutput,"","      ")

<<<<<<< HEAD
	fmt.Printf("Formatted XML to JSON output: \n%s\n",JSONtoXMLdata)
}
=======
	fmt.Printf("XML to JSON output nicely formatted: \n%s\n",JSONtoXMLdata)
}

>>>>>>> butt
func doErr( err error, message string){
	if err != nil{
		log.Panicf("ERROR: %s %s \n", message, err.Error())
	}
} 