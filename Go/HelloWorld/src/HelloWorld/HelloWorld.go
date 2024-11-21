package main 

import (
	"fmt"
	//"io"
	//"bufio"
	//"os"
	//"strings"
	//"net/url"
	//"encoding/xml"
	//"net/http"
	//"log"
	//"io/ioutil"
	//"encoding/json"
)

func main(){
	fmt.Println("hello world")
	//var query string
	var city string
	fmt.Println("Data and API is from OpenWeatherMap.")
	fmt.Println("http://openweathermap.org/")
	//baseURL := "http://api.openweathermap.org/data/2.5/weather?q=";
	//apiKey := "&appid=840ba5edc615f34f7cabc808099cffec"
	//Examples
	//http://openweathermap.org/current
	//api.openweathermap.org/data/2.5/weather?q={city name}
	//api.openweathermap.org/data/2.5/weather?q={city name},{country code}
	//api.openweathermap.org/data/2.5/weather?zip={zip code},{country code}
	//http://api.openweathermap.org/data/2.5/weather?q=London&appid=840ba5edc615f34f7cabc808099cffec
	
	//input
	fmt.Println("Enter a city, replacing any spaces between words with _")
	fmt.Print("City: ")
	//reader := bufio.NewReader(os.Stdin)
	fmt.Scanln(&city)
	fmt.Println("You entered"+city)
}