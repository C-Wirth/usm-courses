package songpack;

/**
 * Author: Colby Wirth 
 * Version: 25 October 2024 
 * Course: COS 285 
 * Class: AVLTree.java
 */
public class AVLTree extends BinarySearchTree {

    public int leftRotation, rightRotation, leftRightRotation, rightLeftRotation;
    public Node root;

    /**
     * Constructor for a AVLTree
     */
    public AVLTree(){
        this.root = null;
        leftRotation = 0;
        rightRotation = 0;
        leftRightRotation = 0;
        rightLeftRotation = 0; 

    }


    /**
     * Helper method to update the height of a node when a node is added
     * @param n
     */
    private void updateHeight(Node n){
        if(n != null)
            n.height=1+Math.max(height(n.left),height(n.right));
    }

    /**
     * completes a left rotation on a AVLTree
     * @param current the node to root node
     * @return replace - the new root node
     */
    private Node leftRotate(Node root){

        Node replace = root.right;
        Node replaceLeft = replace.left;

        replace.left = root;
        root.right = replaceLeft;

        updateHeight(root);
        updateHeight(replace);

        leftRotation++;

        return replace; //the new root rode
    }

    private Node rightRotate(Node root){

        Node replace = root.left;
        Node replaceRight = replace.right;

        replace.right = root;
        root.left = replaceRight;

        updateHeight(root);
        updateHeight(replace);

        return replace;
    }

    /**
     * insert method, overrids BinarySearchTree's insert method
     * @param data - the new song to insert
     */
    @Override
    public void insert(Song data) {
        root = insert(data, root);
    }
    
    /**
     * Helper method to insert a node into the AVL tree recursively
     * @param data
     * @param node
     * @return
     */
    private Node insert(Song data, Node node) {

        if (node == null) {
            return new Node(data);
        }
    
        if (data.compareTo(node.data) < 0) {
            node.left = insert(data, node.left);
        }

        else {
            node.right = insert(data, node.right);
        }    
        updateHeight(node); //redundant - delete me and check if nothing breaks
        return rebalance(node);
    }
    

    /**
     * helper method that checks the balance factor of a Tree
     * @param N the node being checked
     * @return int: -1 if left heavy | 2 if left heavy by 2 nodes | 0 if tree is balanced | 1 if right heavy | 2 if right heavy by 2 nodes
     */
    private int balanceFactor(Node N){

        if(N==null)
            return 0;

        return height(N.right) - height(N.left);
    }

    /**
     * This handles the logic for rebalancing an AVLTree
     * @param root the root node
     * @return root the root node - after rebalancing the tree
     */
    private Node rebalance(Node root){

        if(root == null)
            return root;
        
        updateHeight(root);
        int bf = balanceFactor(root);

        //2L-L Case
        if(bf <-1 && balanceFactor(root.left)<0){
            rightRotation+=1;

            return rightRotate(root);
        }

        //2R-R Case
        if(bf >1 && balanceFactor(root.right) > 0){
            leftRotation+=1;

            return leftRotate(root);
        }
        
        //Left-Right Case
        if(bf <-1 & balanceFactor(root.left)>0){
            leftRightRotation+=1;
            root.left = leftRotate(root.left);

            return rightRotate(root);
        }

        //Right-Left Case
        if (bf > 1 && balanceFactor(root.right) < 0){
            rightLeftRotation+=1;
            root.right = rightRotate(root.right);

            return leftRotate(root);
        }
    
        return root;
    }
}
