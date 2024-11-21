package main

import (
	"fmt"
	"sort"
)

func main() {
	nameList := make(map[string]int)

	nameList["Joe"] = 48;
	nameList["Karen"] = 17;
	nameList["John"] = 3;
	nameList["Abe"] = 17;
	nameList["Chuck"] = 5;

	for index, value := range nameList {
		fmt.Printf("%v %v \n", index, value)
	}


	//Sort the name list by the name.  The name is the index.
	var justTheNames []string;
	for name := range nameList {
		justTheNames = append(justTheNames, name)
	}
	sort.Strings(justTheNames);

	fmt.Println("----Sorted by Key----")
	for _, name := range justTheNames {
		fmt.Printf("%v %v\n", name, nameList[name])
	}

	var justNums []int
	for _, value := range nameList {
		found := false;
		for i := range justNums {
			if value == justNums[i] {
				found = true;
			}

		}
		if (!found) {
			justNums = append(justNums, value)
		}
	}

	sort.Ints(justNums)

	fmt.Println("----Sorted by Num----")

	for _, num := range justNums {
		for name := range nameList {
			if num == nameList[name] {
				fmt.Printf("%v %v \n", name, num)
			}
		}
	}

	//Sort the name list by frequency from low to high.


	fmt.Println("Done");
}
