import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class BayesianTest extends TestCase {
    public void testEdgeNodeConsistency(){
        ArrayList<Double> parentProbability = new ArrayList<>();
        parentProbability.add(0.3);
        Node parentNode = new Node("parentNode", parentProbability);
        ArrayList<Double> childProbability = new ArrayList<>();
        childProbability.add(0.2);
        childProbability.add(0.7);
        Node childNode = new Node("childNode", childProbability);
        Edge edge = new Edge(parentNode, childNode, 0);
        edge.child.name = "newChildNode";
        Node newChildNode = new Node("newChildNode", childProbability);
        assertEquals(newChildNode, childNode);
    }

    public void testBayesianNetworkConstructor(){
        ArrayList<Double> probabilities1 = new ArrayList<>();
        probabilities1.add(0.4);
        Node node1 = new Node("node1", probabilities1);
        ArrayList<String> input = new ArrayList<>();
        input.add("node1: [] [0.4]");
        input.add("node2: [node1] [0.5 0.6]");
        input.add("node3: [node1 node2] [0.25 0.75 0.8 0.1]");
        BayesianNetwork bN = new BayesianNetwork(input);
        assertEquals(node1, bN.nodes.get(0));
        assertEquals(node1, bN.edges.get(0).parent);
        assertEquals(0, bN.edges.get(0).parentN);
        assertEquals(0, bN.edges.get(1).parentN);
        assertEquals(1, bN.edges.get(2).parentN);
    }
}