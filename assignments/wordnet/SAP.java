/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Map;
import java.util.List;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;


public class SAP {
    private Digraph g;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G)
    {
        this.g = G;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w)
    {
        int[] value =  valueAndDistance(v, w);
        if (value[0] == -1)
            return -1;

        return value[1];
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w)
    {
        if (v >= g.V() || v < 0 || w >= g.V() || w < 0)
            throw new IllegalArgumentException();

       return valueAndDistance(v, w)[0];
    }

    private int[] valueAndDistance (int v, int w){

        int[] result = new int[2];
        result[0] = -1;
        result[1] = -1;
        Map<Integer, Integer> vDegree = degree(v);
        Map<Integer, Integer> wDegree = degree(w);

        for (int i : vDegree.keySet()){
            if (wDegree.keySet().contains(i) && (result[1] > vDegree.get(i) + wDegree.get(i) || result[1] == -1)){
                result[0] = i;
                result[1] = vDegree.get(i) + wDegree.get(i);
            }
        }

        return result;
    }


    private Map<Integer, Integer> degree(int start){
        Queue<Integer> frontier = new Queue<>();
        Map<Integer, Integer> result = new HashMap<>();
        Set<Integer> visited = new HashSet<>();

        frontier.enqueue(start);
        result.put(start, 0);
        visited.add(start);

        while (!frontier.isEmpty()){
            int tem = frontier.dequeue();

            for (int i : g.adj(tem)){
                if (!visited.contains(i)){
                    frontier.enqueue(i);
                    result.put(i, result.get(tem) + 1);
                    visited.add(i);
                }
            }
        }
        return result;
    }


    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w)
    {
        if (v == null || w == null)
            throw new IllegalArgumentException();

        int length = -1;

        for (Integer i : v){
            for (Integer j : w){
                if (i == null || j == null)
                    throw new IllegalArgumentException();

                int[] tem = valueAndDistance(i, j);
                if (tem[0] != -1 && (tem[1] < length || length == -1))
                    length = tem[1];
            }
        }
        return length;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w)
    {
        if (v == null || w == null)
            throw new IllegalArgumentException();

        int length = -1;
        int ancestor = -1;

        for (Integer i : v){
            for (Integer j : w){
                if (i == null || j == null)
                    throw new IllegalArgumentException();

                int[] tem = valueAndDistance(i, j);
                if (tem[0] != -1 && (tem[1] < length || length == -1)) {
                    length = tem[1];
                    ancestor = tem[0];
                }
            }
        }
        return ancestor;
    }

    public static void main(String[] args) {
        In in = new In("digraph-wordnet.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
//        while (!StdIn.isEmpty()) {
//            System.out.println("start");
//            int v = StdIn.readInt();
//            System.out.println(v);
//            int w = StdIn.readInt();
//            System.out.println(w);
//            int length   = sap.length(v, w);
//            System.out.println(length);
//            int ancestor = sap.ancestor(v, w);
//            System.out.println(ancestor);
//
//            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
//        }

        List<Integer> i1 = new LinkedList<>();
        List<Integer> i2 = new LinkedList<>();
        i1.add(2819);
        i1.add(79215);
        i2.add(21269);
        i2.add(34846);
        System.out.println(sap.ancestor(i1, i2));

//        System.out.println(sap.length(3, 11));
//        System.out.println(sap.length(9, 12));
//        System.out.println(sap.length(7, 2));
//        System.out.println(sap.length(1, 6));
    }
}
