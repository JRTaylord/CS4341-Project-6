public class Main {
	public static void main(String[] args) {
		if (args.length != 3) {
			System.out.println("Incorrect number of arguments");
			return;
		}
		BayesianNetwork bn = BayesianNetwork.createBayesianFromFile(args[0]);
		bn.assignNodesFileWrapper(args[1]);

		double[] rS = bn.rejectionSampling(Integer.parseInt(args[2]));
		System.out.println("Rejection Sampling: "+rS[0] + "," + rS[1]);
		double[] lW = bn.likelihoodWeightingSampling(Integer.parseInt(args[2]));
		System.out.println("Weighted Liklihood Sampling: "+ lW[0] + "," + lW[1]);
		
		/**
		 * Generates data for tests 
		for (int size = 200; size <= 1000; size+=200) {
			System.out.println("Rejection Size = "+size+ ",True,False");
			for (int i = 0; i < 10; i++) {
				rS = bn.rejectionSampling(size);
				System.out.println("Trial " + (i + 1) + "," + rS[0] + "," + rS[1]);
			}
			System.out.println("Likelihood Weighting Size = "+size + ",True,False");
			for (int i = 0; i < 10; i++) {
				lW = bn.likelihoodWeightingSampling(size);
				System.out.println("Trial " + (i + 1) + "," + lW[0] + "," + lW[1]);
			}
		}
		*/

	}
}
