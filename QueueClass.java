/*
Mohamed Mohamed
821344570
CS 310-2
Software Decoder 2
10/21/19
 */
import java.util.*;

public class QueueClass {



    ArrayList<Integer> queue = new ArrayList<>();

    public boolean isEmpty() {

        return queue.size() == 0;
    }

    public void loadValue(int val) {

        queue.add(val);
    }

    public int nextValue() {
        if (isEmpty() == true) {
            return -1;
        }
        return queue.remove(0);
    }

    public void normalize() {
        if (queue.size() == 0) {
            return;
        }
        int first = queue.get(0);

        for (int i = 0; i < queue.size(); i++) {
            queue.set(i,queue.get(i) - first);
        }
    }

    public int[] peek(int num) {
        int[] array = new int[num];
        for (int i = 0; i < array.length; i++) {
            if (i >= queue.size()) {
                return array;
            } else {
                array[i] = queue.get(i);
            }
        }
        return array;
    }


    public int size() {

        return queue.size();
    }
}
