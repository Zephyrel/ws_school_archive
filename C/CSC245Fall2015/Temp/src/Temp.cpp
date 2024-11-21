//
//  stoi_test.cpp
//
//  Created by ecg
//

#include <iostream>
#include <string> // stoi should be in here

int main() {

    std::string test = "12345";
    int myint = std::stoi(test); // using stoi, specifying in standard library
    std::cout << myint << '\n'; // printing the integer

    return(0);

}
