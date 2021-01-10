public class Node {

    String data;
    int count;
//    int depth;
    Node left;
    Node right;
    Node parent;

    public Node(String data){
        this.data = data;
        this.count = 0;
//        this.depth = 0;
        left = null;
        right = null;
        parent = null;
    }
}
