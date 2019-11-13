/* Mohamed Mohamed
   CS 310-2 Shawn Healey
   9/10/19
   Software Decode Problem 4
 */


public class Problem6 {
    public static void main(String[] args) {
        // example array for testing method
        int[] array = {65505,65525,9,20,31,40,55};
        //arrayPattern scans for the pattern in an array
        //This method returns the index position of where the array begins
        System.out.println(arrayPattern(array));
    }

    public static int arrayPattern(int[] array) {
        // I use a for loop to scan the array for the potential first number of the pattern
        for (int i = 0; i < array.length; i++) {
            // In order to find numbers that meet the pattern, I must call a linear search method to
            // see if the number exists for both conditions
            if (linearSearch(array,getOverflow(array[i] + 20)) == true) {
                if (linearSearch(array, getOverflow(array[i] + 40)) == true) {
                    return i;
                }

            }
        }
        return -1;
    }

    private static boolean linearSearch (int[] array, int num) {
        // Returns true if the index exists

        for (int i = 0; i < array.length; i++) {
            if (array[i] == num) {
                return true;
            }
        }

        return false;
    }

    public static int getOverflow (int num) {
        int maxValue =  65535;
        int z = (num) % (maxValue + 1);
        return z;
    }
}
