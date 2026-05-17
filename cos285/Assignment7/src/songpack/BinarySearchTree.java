package songpack;

import java.util.ArrayList;

/**
 * Author: Colby Wirth 
 * Version: 25 October 2024 
 * Course: COS 285 
 * Class: BinarySearchTree.java
 */
public class BinarySearchTree {

    public Node root;

    /**
     * Constructor for a BinarySearchTree
     */
    public BinarySearchTree(){
        this.root = null;
    }

    /**
     * Node class encapsulates A Node object 
     * 
     */
    public static class Node{
        Song data;
        Node left;
        Node right;
        int height;

        public Node(Song data){
            this.data= data;
            left = right = null;
            height = 0;
        }
    }
    /**
     * Method returns the height of a node
     * @param N the input node
     * @return the height of a Node, an int
     */
    public int height(Node N){
        if (N== null)
            return 0;
        
        return N.height;
    }
    
        /**
         * This method validates whether or not a Tree is a valid BST
         * @param root the root value for a BST
         * @return true if valid, false if invalid
         */
        public boolean isValidBST(Node root){

           return isValid(root, Integer.MIN_VALUE, Integer.MAX_VALUE);

        }
        /**
         * helper function handles the logic for isValidBST
         * @param root the of tree or root of a subtree
         * @param min min val for comparison
         * @param max max val for comparison
         * @return
         */
        public boolean isValid(Node root, int min, int max){

            if(root == null)
                return true;

            if(!(root.data.getViews() > min && root.data.getViews() > max))
                return false;
            
            return isValid(root.left, min, root.data.getViews()) && isValid(root.right, max, root.data.getViews());

        }
       /**
        *This method returns a sorted ArrayList of songs by using inorder traversal of the BST of Song nodes
        *
        *@return list - a sorted ArrayList<Song>
        */
        public ArrayList<Song> toSortedArrayList(){

            ArrayList<Song> list = new ArrayList<>();

            toSortedArrayList(list, root);

            return list;
        }

            /**
             * Helper method for toSortedArrayList.  Handles the logic
             * Handles the logic of an in-order traversal of a BST
             * @param list The sorted  Arraylist of Songs
             * @param root the root of the tree or root of a subtree
             */
            private void toSortedArrayList(ArrayList<Song> list, Node root){

                if(root == null)
                    return;

                toSortedArrayList(list, root.left);
                list.add(root.data);
                toSortedArrayList(list, root.right);
            }

            /**
             * This method adds a Song Object to a BST while maintaining order
             * @param item the new Song Objeect to be added
             */
            public void insert(Song item){
                
                root = insert(item, root);
            }

            /**
             * Helper method for insert method.  Handles the logic
             * @param item the new Song being added
             * @param root the root of the subtree
             * @return root, the root of the newly updated BST
             */
            private Node insert(Song item, Node root){

                if(root == null){
                   return new Node(item);
                }

                if(item.compareTo(root.data) < 0) //if item < root
                    root.left = insert(item, root.left);
                
                else//if item  >= root
                    root.right = insert(item, root.right);

                root.height = 1 + Math.max(height(root.left), height(root.right));

            return root;
            }

            /**
             * Search method finds all songs with a view count that is greater than or equal to a specified viewcount
             * 
             * @param views the specified viewcount
             * @return an ArrayList of songs with a viewcount greater than or equal to the specified viewcount
             */
            public ArrayList<Song> search(int views){
                return search(views, new ArrayList<>(), root);
            }

            /**
             * helper method for search method.  Handles the logic
             * uses In-order traversal to find songs with larger view counts
             * @param views
             * @param songs
             * @param root
             * @return
             */
            private ArrayList<Song> search(int views, ArrayList<Song> songs, Node root){

                if (root == null)
                    return songs;

                if(root.data.getViews() >= views){
                    search(views, songs, root.left);
                    songs.add(root.data);
                }

                search(views, songs, root.right);

                return songs;
            }

            /**
             * clone a method that clones a BinarySerachTree
             * @return bst the cloned BinarySearchTree
             */
            public BinarySearchTree clone(){

                BinarySearchTree bst = new BinarySearchTree();

                bst.root = (clone(root));
                
                return bst;
            }

            /**
             * The helper method for clone that handles the logic for cloning a BST.   
             * Uses preorder traversal
             * @param root the current root of a tree /subtree
             * @return the root node of the new subtree
             */
            private Node clone(Node root){

                if(root == null)
                    return root;
                
                Node newNode = new Node(root.data);

                newNode.left = clone(root.left);
                newNode.right = clone(root.right);

                return newNode;
            }

            /**
             * This method finds all of the Artists with the max views found in a BST
             * @return popularArtist the list of artists with max views
             */
            public ArrayList<String> popularArtist(){

                ArrayList<String> artists = new ArrayList<>();

                if (root != null) {
                    artists.add(root.data.getArtist());
                    popularArtist(root, artists);
                }
                
                return artists;
            }

            /**
             * Helper method for popularArtist, handles the logic
             * @param root the root node of a tree or subtree
             * @param artists the list of popular artists
             * @return artists the list of popular artists
             */
            private ArrayList<String> popularArtist(Node root, ArrayList<String> artists){

                if(root == null)
                    return artists;
                
                if (root.right != null) {

                    if (root.right.data.getViews() > root.data.getViews()) {
                        artists.clear();
                        artists.add(root.right.data.getArtist());
                    } 
                    else if (root.right.data.getViews() == root.data.getViews()) {

                        if (!artists.contains(root.right.data.getArtist())) 
                            artists.add(root.right.data.getArtist()); 
                    }
                }
            
                return popularArtist(root.right, artists);
                }

            /**
             * This method filters a BST within specified upper and lower bounds
             * @param minView the min boundary, inclusive
             * @param maxView the max boundary, inclusive
             * @return the rootNode of the modified tree
             */
            public Node filterByView(int minView, int maxView){

                return filterByView(root, minView, maxView);
            }

            /**
             * Helper method for filterByView.  Handles the logic for finding the tree within the specified bounds
             * @param root the root node of a tree or current root node of a subtree
             * @param minView the min boundary, inclusive
             * @param maxView the max boundary, inclusive
             * @return the rootNode of the modified tree
             */
            private Node filterByView(Node root, int minView, int maxView){

            if (root == null)
                return root;
            
            root.left = filterByView(root.left, minView, maxView);
            root.right = filterByView(root.right, minView, maxView);


            if(root.data.getViews() < minView)  
                return root.right;
            
            else if(root.data.getViews() > maxView)  
                return root.left;

            return root;
            }
        }
