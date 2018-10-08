import java.util.ArrayList;

public class Node {
    ArrayList<Double> probabilities;
    String name;
    String type;
    ArrayList<Edge> parentEdges; //edges that this node is the child on
    Boolean value = null;
    Double probability;
    public Node(String name, ArrayList<Double> probabilities) {
        this.name = name;
        this.probabilities = probabilities;
        type = "";
        parentEdges = new ArrayList<Edge>();
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Node) {
            Node node = ((Node) obj);
            return this.name.equals(node.name) && this.probabilities.equals(node.probabilities) &&
                    this.type.equals(node.type);
        }
        return false;
    }

	public boolean getNodeValue() {
		if (this.value == null) {
			if(parentEdges.size() > 0) {
				this.value = this.getValueFromParents();
			}else {
				double r = Math.random();
				if (this.probabilities.size() != 1) {
					System.out.println("This node has no parents but multiple probabilities, defaulting to first");
				}
				if (r < this.probabilities.get(0)) {
					this.value = true;
				}else {
					this.value = false;
				}
				
			}
		}
		return this.value.booleanValue();
	}

	/**
	 * This function purposefully does NOT set value
	 * @return
	 */
	private Boolean getValueFromParents() {
		int binaryCode = 0;
		for (Edge edge: this.parentEdges) {
			if (edge.parent.getNodeValue()) {
				binaryCode += Math.pow(2, edge.parentN);
			}
		}
		probability = probabilities.get(binaryCode);
		double r = Math.random();
		if (r < probability) {
			return true;
		}else {
			return false;
		}
	}

	public double getProbability() {
		if (this.probability == null) {
			
			this.getValueFromParents(); //note this function sets probabilty but NOT value

		}
		if (!value) {//if false we want to return probability of false, not of true
			return 1.0 - this.probability;
		}
		return this.probability;
	}

}
