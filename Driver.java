/*
Mohamed Mohamed
821344570
CS 310-2
Software Decoder 2
10/21/19
 */


import java.io.FileInputStream;
import java.util.*;
import java.io.*;
public class Driver {

    public static void main(String[] args) {
        FileInputStream patterns = null;
        FileInputStream unsignedValues = null;
        QueueClass queueClass1 = new QueueClass(); // unsigned values
        QueueClass queueClass2 = new QueueClass(); // patterns queue

        System.out.println("You provided " + args.length + " arguments");

        if (args.length < 2) {
            System.err.println("This program needs two arguments, try again");
            return;
        }

        try {
            // as of right now, I'll just have arguments, but I'll use test files later when my code
            // is complete
            patterns = new FileInputStream(args[0]); //args[0] is the patterns file, check for test
            unsignedValues = new FileInputStream(args[1]);// cases, same with args[1] ,
            FileWriter outFile = new FileWriter("return.txt");

            PrintWriter pw = new PrintWriter(outFile);

            Scanner scan = new Scanner(patterns); // scanner for the patterns & weapons codes
            Scanner scnr = new Scanner(unsignedValues); // scanner for the unsigned values


            while (scnr.hasNextLine()) { // checks the scanner for the unsigned values

                String line = scnr.nextLine();
                String[] unsignedArray = line.split(",");
                // patterns are for the decoder

                for (int i = 0; i < unsignedArray.length; i++) {
                    // unsigned values in the queue
                    queueClass1.loadValue(Integer.parseInt(unsignedArray[i]));
                    //

                }

            }

            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                String[] pattern = line.split(",");


                for (int i = 0; i < pattern.length; i++) {
                    // patterns & weapon codes in the queue
                    queueClass2.loadValue(Integer.parseInt(pattern[i]));
                }

            }

            QueueClass returnQueue = decoderMethod(queueClass1,queueClass2);

            int returnQueueSize = returnQueue.size();
            // size often changes when you do the nextValue within the array, so
            // returnQueueSize stays the same

            try {
                for (int i = 0; i < returnQueueSize; i++) {
                    // write the values into the return.txt file
                    Integer patternCodeValue = returnQueue.nextValue();
                    pw.println((patternCodeValue));
                    pw.flush();

                }
            }
            finally {

                    pw.close();

            }


            scan.close();
            scnr.close();


        } catch (FileNotFoundException e) {
            System.err.println("Make sure to use the entire file path for each of the arguments");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static QueueClass decoderMethod(QueueClass unsignedVals, QueueClass patterns) {
        // I'm assuming that the unsigned values are coming in like
        // the simple input stream that Healey gave in his earlier example
        //Also, unsignedVals is unsigned values, patterns is weapon codes and patterns
        ArrayList<Integer> returnList = new ArrayList<>();

        // copies the weapons patterns values to another queue
        QueueClass patternsCopy = patterns;

        // returnQueue stores the confirmed values
        QueueClass returnQueue = new QueueClass();


        // this 2D array stores the pattern array
        int [][] patternArray = new int[patterns.size()/7][7];

        // copies the values stored in the patternsCopy queue into the 2D array
        for (int i = 0; i < patternArray.length; i++) {
            for (int j = 0; j < patternArray[i].length; j++) {
                patternArray[i][j] = patternsCopy.nextValue();
            }
        }

        int unsignedValuesContainedInTheQueue = unsignedVals.size() / 6;
        // this value checks to see how many times the loop can go


        for (int i = 0; i < unsignedValuesContainedInTheQueue; i++) {

            int originalUnsignedValsQueueSize = unsignedVals.size();
            // I keep the original queue size due to the size value being changed

            for (int j = 0; j < originalUnsignedValsQueueSize; j++) {

                // what this loop does is that it removes unsigned values that
                // doesn't match the pattern. Since every potential pattern
                // starts as " 0, 20 " this will check to see if the difference
                // of the two values meet those requirements

                int[] peekValues = unsignedVals.peek(7);
                // peekValues are declared here, taking advantage of scope

                if (peekValues[1] - peekValues[0] != 20) {
                    // if it doesn't meet the requirements above, then the value
                    // is removed from the queue and the values are normalized
                    unsignedVals.nextValue();
                    unsignedVals.normalize();
                } else {
                    // if a word is at least found, the loop is stopped
                    break;
                }
            }

            int noise = 0;
            unsignedVals.normalize();
            int[] peekValues = unsignedVals.peek(7);


            for (int j = 0; j < patternArray.length; j++) {
                // iterate through the peekValues to see if conditions are met
                if (j >= patternArray.length) {
                    noise = 0;
                    // if j is bigger than the pattern array length, then stop the loop
                    // this stops the ArrayIndexOutOfBoundsException from occurring
                    break;
                }
                if (peekValues[1] - peekValues[0] == 0) {
                    // fail-safe because peek will automatically get zeros after the values
                    // are done, so this is used to break if the size is empty
                    break;
                }
                noise = 0;
                //noise is reset to scan for the next value
                for (int k = 0; k < 6; k++) {

                    if (noise >= 2) {
                        // if there are two noise values, it isn't a valid MILES pattern
                        break;
                    } else if (noise == 1) {
                        // if there's one noise value, it can still be a noise value, so skip
                        // the value that's a noise value and scan the rest of the peeked values
                        if (peekValues[k + 1] != patternArray[j][k]) {
                            noise++;
                            unsignedVals.normalize();
                            continue;
                        }
                    }
                    else {
                        // if there are no noise values and the unsigned values match, then we
                        // have a MILES pattern!
                        if (peekValues[k] != patternArray[j][k]) {
                            if (peekValues[k+1] != patternArray[j][k]) {
                                // checks to see if there is a another noise value next to a noise value
                                noise++;
                            }
                            noise++;
                            continue;
                        }
                    }

                }




                if (noise == 1 || noise == 0) {
                    // if there is a valid MILES pattern within the unsigned values, add the respective
                    // MILES pattern number to the returnList that contains the weapon code
                    returnList.add(patternArray[j][6]);
                    System.out.println("weapon code pattern: " + patternArray[j][6]);
                    break;
                }

            }
             if (noise == 2) {
                 // if noise is equal to two, then pretty much erase those values from the queue
                 // and try again
                unsignedVals.nextValue();
                unsignedVals.normalize();

            }
            else if (noise == 1) {
                // if noise is equal to one, pretty much do the same thing and erase those values
                // from the queue
                for (int j =0; j < 7; j++) {
                    if (unsignedVals.size() == 0) {
                        // if the queue is empty, just stop this process
                        break;
                    }
                    unsignedVals.nextValue();
                }
                if (unsignedVals.size() > 0) {
                    // if the array isn't empty, normalize the values and try again
                    unsignedVals.normalize();

                }
            }
            else if (noise == 0){
                 if (peekValues[1] - peekValues[0] == 0) {
                     // if noise is equal to zero, just make sure that the array isn't empty
                     break;
                 }
                for (int j =0; j < 6; j++) {
                    if (unsignedVals.size() == 0) {
                        // if the array is empty, don't erase the values from the queue and stop the process
                        break;
                    }
                    unsignedVals.nextValue();
                }
                if (unsignedVals.size() > 0) {
                    // if the array isn't empty, normalize the values and try again
                    unsignedVals.normalize();
                }
            }
        }

        for (int i = 0; i < returnList.size(); i++) {
            // load the values from returnList to the queue we want to return
            returnQueue.loadValue(returnList.get(i));
        }

        return returnQueue;

    }

}







