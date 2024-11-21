
package testbed;

public class TestBed {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*double[] Numbers = {1.9, 2.9, 3.4, 3.5};
        double testSum = sumArray(Numbers);
        System.out.println(testSum);*/
        int amount = 8;
        while (amount > 0) {
            amount--;
            if (amount % 2 == 0) {   //if amount is even
                continue;
            }
            amount -= 5;
        }
        System.out.println(amount);
    }
    
    public static double sumArray(double[] arrayNumbers){
        double sum = 0;
            for (int n = 0; n < arrayNumbers.length; n++){
                sum += arrayNumbers[n];
            }
        return sum;
    }
    
    /*public static int linearSearch(double[] list, int n, double target) {
        int foundAt  = -1;
        int result;
        boolean found;

        found = false;
        for (int i = 0; i < n; i++) {
            if (list[i] == target) {
                foundAt = i;
                found = true;
            }
        }
        if (found) {
            result = foundAt;
        } else {
            result = -1;
        }
        return result;
    }*/
    public static int linearSearch(double[] list, int n, double target) {
        int foundAt  = -1;
        
        for (int i = 0; i < n; i++) {
            if (list[i] == target) {
                foundAt = i;
            }
        }
        
        return foundAt;
    }    
}
