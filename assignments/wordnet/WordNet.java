/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */


import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;


public class WordNet {
    // constructor takes the name of the two input files
    private Digraph dg;
    private HashMap<Integer, String[]> words;
    private SAP s;
    private HashMap<String, LinkedList<Integer>> nounToId;



    public WordNet(String synsets, String hypernyms)
    {
        if (synsets == null || hypernyms == null)
            throw new IllegalArgumentException();

        words = new HashMap<>();
        nounToId = new HashMap<>();

        In in = new In(synsets);
        while (in.hasNextLine())
        {
            String line = in.readLine();
            String[] ss = line.split(",");
            words.put(Integer.parseInt(ss[0]), ss[1].split(" "));

            for (String s : ss[1].split(" ")){
                if (nounToId.get(s) == null)
                    nounToId.put(s, new LinkedList<>());

                nounToId.get(s).add(Integer.parseInt(ss[0]));
            }



        }

        dg = new Digraph(words.size());



        In in2 = new In(hypernyms);

        while (in2.hasNextLine())
        {
            String line = in2.readLine();
            String[] ss = line.split(",");
            int origen = Integer.parseInt(ss[0]);

            for (int i = 1; i < ss.length; i++)
            {
                int son = Integer.parseInt(ss[i]);
                dg.addEdge(origen, son);

            }

        }

        s = new SAP(dg);

        if (!isDAG(dg))
            throw new IllegalArgumentException();
    }

    private boolean isDAG(Digraph dg){
        int[] inDegree = new int[dg.V()];
        int count = 0;
        int root = 0;

        for (int i = 0; i < inDegree.length; i++){
            inDegree[i] = dg.indegree(i);
            int adj = 0;
            for (int j : dg.adj(i))
                adj++;
            if (adj == 0)
                root++;
        }


        if (root != 1)
            return false;

                    Stack<Integer> stack = new Stack<>();

                    for (int i = 0; i < inDegree.length; i++)
                        if (inDegree[i] == 0)
                            stack.push(i);



                        while (!stack.isEmpty()){
                            count ++;
                            int tem = stack.pop();

                            for (int i : dg.adj(tem)){
                                inDegree[i]--;
                                if (inDegree[i] == 0)
                                    stack.push(i);
                            }
                        }

                        if (count != inDegree.length)
                            return false;

                        return true;
    }


    // returns all WordNet nouns
    public Iterable<String> nouns()
    {
      return nounToId.keySet();
    }


    // is the word a WordNet noun?
    public boolean isNoun(String word)
    {
        if (word == null)
            throw new IllegalArgumentException();

        return nounToId.containsKey(word);
    }


    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB)
    {
        if (nounA == null || nounB == null || !isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException();

        return s.length(nounToId.get(nounA), nounToId.get(nounB));
    }


    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB)
    {
        if (nounA == null || nounB == null || !isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException();


        int ancestor = s.ancestor(nounToId.get(nounA), nounToId.get(nounB));
        if (ancestor == -1)
            return null;

        String result = words.get(ancestor)[0];

        for (int i = 1; i < words.get(ancestor).length; i++)
            result += " " + words.get(ancestor)[i];

        return result;
    }


    public static void main(String[] args) {
        WordNet wordNet = new WordNet("synsets1000-subgraph.txt", "hypernyms1000-subgraph.txt");
        int u = wordNet.distance("medulla", "sliver");
        String a = wordNet.sap("medulla", "sliver");
        System.out.println(wordNet.distance("medulla", "sliver"));
    }
}
