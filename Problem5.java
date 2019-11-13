/* Mohamed Mohamed
   CS 310-2 Shawn Healey
   9/10/19
   Software Decode Problem 4
 */


public class Problem5 {


        public static void main(String[] args) {
            // example array for testing method
            int[] array = {10,18,19,20,31,38,40,55,56,57,58,59,60,6525};
            //arrayPattern scans for the pattern in an array
            //This method returns the index position of where the array begins
            int pattern = 19;
            System.out.println(arrayPattern(array,pattern));
        }

        public static int arrayPattern(int[] array, int pattern) {
            // I use a for loop to scan the array for the potential first number of the pattern
            for (int i = 0; i < array.length; i++) {
                // In order to find numbers that meet the pattern, I must call a binary search method to
                // see if the number exists for both conditions
                if (binarySearch(array, array[i] + pattern, 0, array.length-1) == true) {
                    if (binarySearch(array, array[i] + (pattern * 2), 0, array.length-1) == true) {
                        return i;
                    }
                    else {
                        return -1;
                    }
                }
                else {
                    continue;
                }
            }
            return -1;
        }

        private static boolean binarySearch (int[] array, int key, int low, int high) {
            // This method, instead of returning the index of the array, it returns the contents
            // of the index

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


