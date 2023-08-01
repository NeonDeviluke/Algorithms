/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public class Outcast {
    private WordNet wordnet;

    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }

    public String outcast(String[] nouns) {
        int d = 0;
        String outcast = nouns[0];

        for (int i = 0; i < nouns.length; i++) {
            int tmp = distance(nouns[i], nouns);
            if (tmp > d) {
                d = tmp;
                outcast = nouns[i];
            }
        }
        return outcast;
    }

    private int distance(String noun, String[] nouns) {
        int result = 0;

        for (int i = 0; i < nouns.length; i++) {
            result += wordnet.distance(noun, nouns[i]);
        }
        return result;
    }

    public static void main(String[] args) {

    }
}
