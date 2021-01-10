
public class SearchTree {
    //Declaring root variable of node as global
    public static Node root;

    //Declaring valueToBePrinted string for printing the node values
    public String valueToBePrinted = "";

    //Initialising the depth of the nodes as 0 and declaring it public to the class
    public int dep = 0;

    //Intiailasing the root node in the constructor
    public SearchTree(){
        this.root = null;
    }

    //Method to add the tree elements
    public boolean add(String key){
        // Checking the input validations of the add function
        if(key==null){
            //System.out.println("String passed cannot be null");
            return false;
        }else if(key.isEmpty()){
            //System.out.println("String cannot be empty");
            return false;
        }
        else {
            //Creating an object for the new node passed by the user
            Node newNode = new Node(key);
            //If tree is empty then assign the newNode to root
            if (root == null) {
                root = newNode;
            } else {
                //If the tree is not empty then iterate the node by performing a string comparision and the
                // node is added to the end of a child node based  on the sorted order
                Node current = root;
                Node previous = null;
                while (current != null) {
                    previous = current;
                    int wordValueCompare = key.compareToIgnoreCase(current.data);                    //Referred https://algorithms.tutorialhorizon.com/binary-search-tree-complete-implementation/ for node creation
                    if (wordValueCompare == 0) {
                        //If node is already present in the tree then false is returned in the method call
                        return false;
                    } else if (wordValueCompare < 0) {
                        current = current.left;
                        //current.parent=current
                        if (current == null) {
                            //adding the newNode to left of the parent node
                            previous.left = newNode;
                            //assigning the parent to the newly added node
                            previous.left.parent = previous;
                        }
                    } else {
                        current = current.right;
                        if (current == null) {
                            //adding the newNode to right of the parent node
                            previous.right = newNode;
                            //assigning the parent to the newly added node
                            previous.right.parent = previous;
                        }
                    }
                }
            }
        }
        // Returning true when node is added successfully
        return true;
    }

    //Method to find a node passed in the form of string by user
    public int find(String key) {
        // Checking the input validations of the find method
        if(key==null){
            //System.out.println("String passed in find method cannot be null");
            return 0;
        }
        else if(key.isEmpty()){
            //System.out.println("String passed in find method cannot be empty");
            return 0;
        }
        else {
            Node current = root;
            if (root == null) {
                //System.out.println("Empty tree");
                return 0;
            } else {
                //If key is valid then iterate over the tree and check for each nodes
                Node previous = null;
                //Initialise the depth count to 1 as we are navigating through root
                int dep = 1;
                // checking from rood node for the node in the existing tree
                while (current != null) {
                    int wordValueCompare = key.compareToIgnoreCase(current.data);
                    if (wordValueCompare == 0) {
                        break;
                    } else if (wordValueCompare < 0) {
                        //assigning the previous node to my current node and the checking the left node of current
                        previous = current;
                        current = current.left;
                        //Incrementing thr depth by one count
                        dep += 1;
                    } else {
                        //assigning the previous node to my current node and the checking the right node of current
                        previous = current;
                        current = current.right;
                        //Incrementing the depth by one count
                        dep += 1;
                    }
                }
                if (current == null) {
                    // If node is node found even at the end of the tree then returning node not found
                    return 0;
                } else {
                    //If node is found then increment the count
                    current.count++;
                    //Ignore the root node where depth is one and check for the left/right positioning of the node by string compare and call the swap methods
                    if(dep!=1) {
                        if (current.count > previous.count) {
                            if (current.data.compareToIgnoreCase(previous.data) > 0) {
                                //Passing the parent node of the node to be swapped
                                swapLeft(previous);
                                //dep--;
                            }
                            else {
                                //Passing the parent node of the node to be swapped
                                swapRight(previous);
                                //dep--;
                            }
//                        current = rotation(key);
                        }
                    }
                    // returning the current depth of the node found before the rotation
                    return dep;
                }
            }
        }
        //return dep;
    }

    //Method used to achieve right rotation of the tree node
    public void swapRight(Node par){
        //Referred https://cs.lmu.edu/~ray/notes/binarysearchtrees/ for swap method
        Node child = par.left;
        //when parent node passed is a root node
        if (par.parent == null) {
            root = child;
            par.parent = child;
            child.parent = null;
        } else {
            //when parent node passed is not a root node and parent nodes parent is at left
            if (par.parent.data.compareToIgnoreCase(par.data) < 0) {

                //par.parent = child;
                child.parent = par.parent;
                par.parent.right = child;
                par.parent = child;
            } else {
                //when parent node passed is not a root node and parent nodes parent is at right
                //par.parent = child;
                child.parent = par.parent;
                par.parent.left = child;
                par.parent = child;
            }
        }
        //If child node has right value
        if (child.right != null) {
            par.left = child.right;
            child.right.parent = par;
            child.right = par;
        } else {
            //If child nodes right has no value
            child.right = par;
            par.left = null;
        }
    }

    public void swapLeft(Node par){
        // Referred https://cs.lmu.edu/~ray/notes/binarysearchtrees/ for swap method
        Node child = par.right;
        //when parent node passed is a root node
        if (par.parent == null) {
            root = child;
            par.parent = child;
            child.parent = null;
        } else {
            if (par.parent.data.compareToIgnoreCase(par.data) < 0) {
                //when parent node passed is not a root node and parent nodes parent is at left
                child.parent = par.parent;
                par.parent.right = child;
                par.parent = child;
            } else {
                //when parent node passed is not a root node and parent nodes parent is at right
                child.parent = par.parent;
                par.parent.left = child;
                par.parent = child;
            }
        }
        //If child node has left value
        if (child.left != null) {
            par.right = child.left;
            child.left.parent = par;
            child.left = par;
        } else {
            //If child nodes left has no value
            child.left = par;
            par.right = null;
        }
    }

    // method to reset the count of each nodes in the Binary Search Tree
    public void reset(){
        Node current = root;
        resetCount(current);
    }
    //Method called by reset method by passing the tree nodes recursively
    private void resetCount(Node reCount){
        //System.out.println("next nodes "+ reCount.data + reCount.left.data + reCount.right.data);
        //Iterating each nodes over the tree using Inorder traversal and setting the counter of each nodes to zero
        if (reCount != null) {
            resetCount(reCount.left);
            reCount.count = 0;
            resetCount(reCount.right);
        }
    }

    //Method to print the nodes off the tree with each nodes in a newline
    public String printTree(){
        String temp ="";
        dep=0;
        Node current = root;
        if(root==null){
            //System.out.println("Empty tree");
            temp= "null";
        }
        else {
            //      printing the tree using inorder traversal
            // Calling the printRecTree method to sort the tree and display
            printRecTree(current);
            temp = valueToBePrinted;
            //resetting the global parameter valueToBePrinted after each node iteration
            valueToBePrinted = "";
        }
        // returning the node values as requested by the user
        return temp;
    }

    //Method to print the tree using Inorder traversal
    public void printRecTree(Node currRoot) {
        //assigning the value to the global parameter valueToBePrinted
        if (currRoot.left!= null)
            printRecTree(currRoot.left);
        //findDepth method is called to retrieve the depth of the current node
        valueToBePrinted += currRoot.data + " "+findDepth(currRoot.data)+"\n";
        if (currRoot.right!= null)
            printRecTree(currRoot.right);

    }

    //method called by printRecTree method to find and return the depth of the node passed.
    public int findDepth(String key) {
        Node current = root;
        int dep = 1;
        while ((current != null) && (current.data != key)) {
            int wordValueCompare = key.compareToIgnoreCase(current.data);
            if (wordValueCompare < 0) {
                current = current.left;
                dep += 1;
            } else {
                current = current.right;
                dep += 1;
            }
        }
        if (current == null) {
            return 0;
        } else {
            return dep;
        }
    }
}