/* Mohamed Mohamed
   CS 310-2 Shawn Healey
   9/10/19
   Software Decode Problem 4
 */


public class Problem4 {
    public static void main(String[] args) {
        // example array for testing method
        short[] array = {0, 20, 30, 40, 50, 60, 101};
        //arrayPattern scans for the pattern in an array
        //This method returns the index position of where the array begins
        System.out.println(arrayPattern(array));
    }

    public static int arrayPattern(short[] array) {
        // I use a for loop to scan the array for the potential first number of the pattern
        for (int i = 0; i < array.length; i++) {
            // In order to find numbers that meet the pattern, I must call a binary search method to
            // see if the number exists for both conditions
            if (binarySearch(array, array[i] + 20, 0, array.length -1) ==  true) {
                if (binarySearch(array, array[i] + 40, 0, array.length -1) == true) {
                    continue;
                }
                else {
                    return i;
                }
            }
            else {
                continue;
            }
        }
    return -1;
    }

    private static boolean binarySearch (short[] array, int key, int low, int high) {
        // This method returns a boolean
        while (high >= low) {
            int mid = (low + high) / 2;
            if (key < array[mid]) {
                high = mid - 1;
            }
            else if (key > array[mid]) {
                low = mid + 1;
            }
            else {
                // returns the number in that position IF IT EXISTS
                return true;
            }

        }
        return false;
    }
}
