import java.util.ArrayList;

public class Node {
    ArrayList<Long> probabilities;
    String name;
    boolean evidence;
    boolean observed;
    boolean query;

    public Node(String name, ArrayList<Long> probabilities){
        this.name = name;
        this.probabilities = probabilities;
        boolean evidence = false;
        boolean observed = false;
        boolean query = false;
    }

    public String getName(){
        return name;
    }
}
