import java.io.*;
import java.util.ArrayList;

public class BayesianNetwork {
    ArrayList<Node> nodes;
    ArrayList<Edge> edges;

    /**
     * @param input: an ArrayList of input Strings in the format of Option B
     */
    public BayesianNetwork(ArrayList<String> input) {
        this.nodes = new ArrayList<>();
        this.edges = new ArrayList<>();
        // Node creation
        int stage;
        ArrayList<ArrayList> soonToBeEdges = new ArrayList<>();
        for (String nodeDesc :
                input) {
            stage = 0;
            String nodeName = "";
            ArrayList<Double> nodeProbabilities = new ArrayList<>();
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
                                nodeProbabilities.add(Double.parseDouble(probability));
                                probability = "";
                                break;
                            default:
                                probability += nodeDesc.charAt(i);
                                break;
                        }
                        break;
                }
            }
            Node node = new Node(nodeName, nodeProbabilities);
            this.nodes.add(node);
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

    /**
     *
     * @param assignments: a list of preprocessed chars
     */
    public void assignNodes(ArrayList<Character> assignments){
        for (int i = 0; i < assignments.size(); i++) {
            Node node = this.nodes.get(i);
            switch (assignments.get(i)){
                case '?':
                case 'q':
                    node.query = true;
                    node.neither = false;
                    break;
                case 't':
                    node.evidence = true;
                    node.query = false;
                    node.neither = false;
                    break;
                case 'f':
                    node.evidence = false;
                    node.query = false;
                    node.neither = false;
                    break;
                case '-':
                    node.evidence = false;
                    node.query = false;
                    node.neither = true;
                    break;
            }
        }
    }

    /**
     * Acts as a wrapper for the testable method of assigning states to nodes in the Bayesian Network
     * @param fileName
     */
    public void assignNodesFileWrapper(String fileName){
        File file = new File(fileName);
        BufferedReader reader = null;

        ArrayList<Character> assigments = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(file));
            String text = null;

            while ((text = reader.readLine()) != null) {
                for (int i = 0; i < text.length(); i++) {
                    switch (text.charAt(i)){
                        case (','):
                            break;
                        default:
                            assigments.add(text.charAt(i));
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
            }
        }
        this.assignNodes(assigments);
    }

    public void rejectionSampling(){

    }

    public void likelihoodWeightingSampling(){

    }
}
