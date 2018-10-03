public class Edge {
    Node parent;
    Node child;
    int parentN;

    public Edge(Node parent, Node child, int parentN){
        this.parent = parent;
        this.child = child;
        this.parentN = parentN;
    }
}
