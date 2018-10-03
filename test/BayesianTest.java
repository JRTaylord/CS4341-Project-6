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
}