import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private WordNet wordNet;

    public Outcast(WordNet wordnet)    {
        this.wordNet = wordnet;
    }     // constructor takes a WordNet object
    public String outcast(String[] nouns){
        int min = -1;
        String result = null;
        for (String s1 : nouns){
            int distance = 0;
            for (String s2 : nouns){
                distance += wordNet.distance(s1, s2);
            }
            if (distance > min) {
                min = distance;
                result = s1;
            }
        }

        return result;
    }   // given an array of WordNet nouns, return an outcast
    public static void main(String[] args){
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }  // see test client below
}
