import java.util.ArrayList;

public class BayesianNetwork {
    ArrayList<Node> nodes;
    ArrayList<Edge> edges;

    /**
     * 
     * @param input: an ArrayList of unformatted input Strings
     */
    public BayesianNetwork(ArrayList<String> input){
        // Node creation
        int stage;
        ArrayList<ArrayList> parents = new ArrayList<>();
        for (String nodeDesc :
                input) {
            stage = 0;
            String nodeName = "";
            for (int i = 0; i < nodeDesc.length(); i++) {
                switch (stage){
                    case 0:
                        if ()
                        nodeName += nodeDesc.charAt(i);
                }
            }
        }
        // Edge Creation
    }
}
