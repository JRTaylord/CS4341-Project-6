import java.util.ArrayList;

public class BayesianNetwork {
    ArrayList<Node> nodes;
    ArrayList<Edge> edges;

    /**
     * @param input: an ArrayList of unformatted input Strings
     */
    public BayesianNetwork(ArrayList<String> input) {
        // Node creation
        int stage;
        ArrayList<ArrayList> soonToBeEdges = new ArrayList<>();
        for (String nodeDesc :
                input) {
            stage = 0;
            String nodeName = "";
            ArrayList<Long> nodeProbabilities = new ArrayList<>();
            ArrayList<String> nodeParents = new ArrayList<>();
            String parentName = "";
            String probability = "";
            for (int i = 0; i < nodeDesc.length(); i++) {
                switch (stage) {
                    case 0:
                        if (nodeDesc.charAt(i) != ':') {
                            nodeName += nodeDesc.charAt(i);
                        } else stage++;
                        break;
                    case 1:
                    case 3:
                        if (nodeDesc.charAt(i) == '[') stage++;
                        break;
                    case 2:
                        switch (nodeDesc.charAt(i)) {
                            case ']':
                                stage++;
                            case ' ':
                                nodeParents.add(parentName);
                                parentName = "";
                                break;
                            default:
                                parentName += nodeDesc.charAt(i);
                                break;
                        }
                        break;
                    case 4:
                        switch (nodeDesc.charAt(i)) {
                            case ']':
                                stage++;
                            case ' ':
                                nodeProbabilities.add(Long.parseLong(probability));
                                probability = "";
                                break;
                            default:
                                probability += nodeDesc.charAt(i);
                                break;
                        }
                        break;
                }
            }
            this.nodes.add(new Node(nodeName, nodeProbabilities));
            soonToBeEdges.add(nodeParents);
        }
        // Edge Creation
        // Since the list of prospective edges and established nodes are aligned, we can count on each edge being accurate
        for (int i = 0; i < soonToBeEdges.size(); i++) {
            for (int j = 0; j < soonToBeEdges.get(i).size(); j++) {
                for (Node node :
                        nodes) {
                    if (node.name.equals(soonToBeEdges.get(i).get(j))) {
                        this.edges.add(new Edge(node, nodes.get(i), j));
                        break;
                    }
                }
            }
        }
    }
}
