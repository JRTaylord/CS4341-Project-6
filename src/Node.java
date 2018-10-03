import java.util.ArrayList;

public class Node {
    ArrayList<Long> probabilities;
    long probabilityTrue;
    long probabilityFalse;
    boolean evidence;
    boolean observed;
    boolean query;
}
