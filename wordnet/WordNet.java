/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

import java.util.HashMap;

public class WordNet {
    private HashMap<Integer, String> idmap;
    private HashMap<String, Bag<Integer>> nounmap;
    private SAP sap;


    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        idmap = new HashMap<>();
        nounmap = new HashMap<>();
        synSetting(synsets);
        sap = new SAP(hypersettings(hypernyms));
    }


    private Digraph hypersettings(String hypernyms) {
        In bello = new In(hypernyms);
        Digraph graph = new Digraph(idmap.size());
        while (!bello.isEmpty()) {
            String[] parts = bello.readLine().split(",");
            int v = Integer.parseInt(parts[0]);
            for (int i = 1; i < parts.length; i++) {
                int w = Integer.parseInt(parts[i]);
                graph.addEdge(v, w);
            }
        }
        checkcycle(graph);
        return graph;
    }

    private void checkcycle(Digraph graph) {
        DirectedCycle cycle = new DirectedCycle(graph);
        if (cycle.hasCycle()) throw new IllegalArgumentException("Digraph should not have a cycle");
    }

    private void synSetting(String synsets) {
        In hello = new In(synsets);
        while (!hello.isEmpty()) {
            String[] parts = hello.readLine().split(",");
            Integer id = Integer.parseInt(parts[0]);
            String[] words = parts[1].split(" ");
            idmap.put(id, parts[1]);
            Bag<Integer> idbag;

            for (String noun : words) {
                idbag = nounmap.get(noun);
                if (idbag == null) {
                    idbag = new Bag<>();
                    idbag.add(id);
                    nounmap.put(noun, idbag);
                }
                else {
                    idbag.add(id);
                }
            }
        }

    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nounmap.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) throw new IllegalArgumentException();
        return nounmap.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) throw new java.lang.IllegalArgumentException();
        Iterable<Integer> A = nounmap.get(nounA);
        Iterable<Integer> B = nounmap.get(nounB);
        return sap.length(A, B);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) throw new java.lang.IllegalArgumentException();
        Iterable<Integer> A = nounmap.get(nounA);
        Iterable<Integer> B = nounmap.get(nounB);
        int ancestor = sap.ancestor(A, B);
        return idmap.get(ancestor);
    }

    // do unit testing of this class
    public static void main(String[] args) {
        // WordNet wordNet = new WordNet("D:\\Algorithms\\wordnet\\synsets3.txt",
        //                            "D:\\Algorithms\\wordnet\\hypernyms3InvalidCycle.txt");
    }
}