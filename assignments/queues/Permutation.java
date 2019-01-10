/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {

        int i = Integer.parseInt(args[0]);
        RandomizedQueue<String> r = new RandomizedQueue();
        while (!StdIn.isEmpty())
        {
            String n = StdIn.readString();
            if (r.size()<i)
            r.enqueue(n);
            else {
                r.enqueue(n);
                r.dequeue();
            }
        }

        for (int j =0;j<i;j++)
        {
            System.out.println(r.dequeue());
        }


    }
}
