import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class BayesianNetwork {
	ArrayList<Node> nodes;
	ArrayList<Edge> edges;
	int queryNodeIndex;
	double weight = 0;

	/**
	 * @param input: an ArrayList of input Strings in the format of Option B
	 */
	public BayesianNetwork(ArrayList<String> input) {
		this.nodes = new ArrayList<>();
		this.edges = new ArrayList<>();
		// Node creation
		int stage;
		ArrayList<ArrayList> soonToBeEdges = new ArrayList<>();
		for (String nodeDesc : input) {
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
					} else
						stage++;
					break;
				case 1:
				case 3:
					if (nodeDesc.charAt(i) == '[')
						stage++;
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
		// Since the list of prospective edges and established nodes are aligned, we can
		// count on each edge being accurate
		for (int i = 0; i < soonToBeEdges.size(); i++) {
			for (int j = 0; j < soonToBeEdges.get(i).size(); j++) {
				for (Node node : nodes) {
					if (node.name.equals(soonToBeEdges.get(i).get(j))) {
						Edge tempEdge = new Edge(node, nodes.get(i), j);
						this.edges.add(tempEdge);
						nodes.get(i).parentEdges.add(tempEdge);
						break;
					}
				}
			}
		}
	}

	/**
	 *
	 * @param assignments:
	 *            a list of preprocessed chars
	 */
	public void assignNodes(ArrayList<Character> assignments) {
		for (int i = 0; i < assignments.size(); i++) {
			Node node = this.nodes.get(i);
			switch (assignments.get(i)) {
			case '?':
			case 'q':
				node.type = "query";
				queryNodeIndex = i;
				break;
			case 't':
				node.type = "true";
				break;
			case 'f':
				node.type = "false";
				break;
			case '-':
				node.type = "-";
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Acts as a wrapper for the testable method of assigning states to nodes in the
	 * Bayesian Network
	 *
	 * @param fileName
	 */
	public void assignNodesFileWrapper(String fileName) {
		File file = new File(fileName);
		BufferedReader reader = null;

		ArrayList<Character> assigments = new ArrayList<>();
		try {
			reader = new BufferedReader(new FileReader(file));
			String text = null;

			while ((text = reader.readLine()) != null) {
				for (int i = 0; i < text.length(); i++) {
					switch (text.charAt(i)) {
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

	public double[] rejectionSampling(int sampleSize) {
		HashMap<Boolean, Double> queryValueCounts = new HashMap<Boolean, Double>();
		queryValueCounts.put(true, 0.0);
		queryValueCounts.put(false, 0.0);
		for (int i = 0; i < sampleSize; i++) {
			boolean[] b = this.priorSample();
			if (this.isConsistent(b)) {
				queryValueCounts.put(b[queryNodeIndex], 1 + queryValueCounts.remove(b[queryNodeIndex]));
			}
			for (Node n : nodes) {
				n.value = null;
				n.probability = null;
			}
		}
		return getNormalizedArray(queryValueCounts);
	}

	private boolean[] priorSample() {
		boolean[] boolArray = new boolean[nodes.size()];
		for (int i = 0; i < nodes.size(); i++) {
			boolArray[i] = nodes.get(i).getNodeValue();
		}

		return boolArray;

	}

	private boolean isConsistent(boolean[] b) {
		for (int i = 0; i < nodes.size(); i++) {
			if (nodes.get(i).type.equals("true") && !b[i]) {
				return false;
			}
			if (nodes.get(i).type.equals("false") && b[i]) {
				return false;
			}
		}
		return true;
	}

	private double[] getNormalizedArray(HashMap<Boolean, Double> queryValueCounts) {
		double[] array = new double[2];
		double sum = queryValueCounts.get(true) + queryValueCounts.get(false);
		array[0] = queryValueCounts.get(true) / sum;
		array[1] = queryValueCounts.get(false) / sum;
		return array;
	}

	public double[] likelihoodWeightingSampling(int sampleSize) {
		HashMap<Boolean, Double> queryValueCounts = new HashMap<Boolean, Double>();
		queryValueCounts.put(true, 0.0);
		queryValueCounts.put(false, 0.0);
		for (int i = 0; i < sampleSize; i++) {
			Boolean[] b = this.weightedSample();
			queryValueCounts.put(b[queryNodeIndex], weight + queryValueCounts.remove(b[queryNodeIndex]));
			for (Node n : nodes) {
				n.value = null;
				n.probability = null;
			}
		}
		return getNormalizedArray(queryValueCounts);
	}

	private Boolean[] weightedSample() {
		weight = 1;
		Boolean[] array = makeArrayFromEvidence();
		for (int i = 0; i < nodes.size(); i++) {
			if (array[i] != null) {
				weight = weight * nodes.get(i).getProbability();
			} else {
				array[i] = nodes.get(i).getNodeValue();
			}
		}
		return array;
	}

	private Boolean[] makeArrayFromEvidence() {
		Boolean array[] = new Boolean[nodes.size()];
		for (int i = 0; i < nodes.size(); i++) {
			Node node = nodes.get(i);
			if (node.type.equals("true")) {
				node.value = true;
				array[i]= true;
			} else {
				if (node.type.equals("false")) {
					node.value = false;
					array[i]= false;
				} else {
					array[i] = null;
				}
			}
		}
		return array;
	}

	public static BayesianNetwork createBayesianFromFile(String fileName) {
		File file = new File(fileName);
		BufferedReader reader = null;

		ArrayList<String> nodes = new ArrayList<>();
		try {
			reader = new BufferedReader(new FileReader(file));
			String text = null;

			while ((text = reader.readLine()) != null) {

				nodes.add(text);// The ] appears once in middle and once at end so doing this ensures it will
								// function correctly

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
		return new BayesianNetwork(nodes);
	}
}
