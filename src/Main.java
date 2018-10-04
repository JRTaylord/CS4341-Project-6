public class Main {
    public static void main (String[] args) {
    	if (args.length != 3) {
    		System.out.println("Incorrect number of arguments");
    		return;
    	}
    	BayesianNetwork bn = BayesianNetwork.createBayesianFromFile(args[0]);
    	bn.assignNodesFileWrapper(args[1]);
    	
    	double[] rS = bn.rejectionSampling(Integer.parseInt(args[2]));
    	System.out.println(rS[0] + ", "+rS[1]);
    	//double lW = bn.likelihoodWeightingSampling(Integer.parseInt(args[2]));
    	
    }
}
