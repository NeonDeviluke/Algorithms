import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        String champion = "";
        double count = 0;
        while (!StdIn.isEmpty()) {
            String value = StdIn.readString();
            count++;
            if (StdRandom.bernoulli(1.0 / count)) {
                champion = value;
            }
        }
        StdOut.println(champion);
    }
}
