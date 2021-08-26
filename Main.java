import java.util.*;

public class Main {

    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();
        bst.insert(12);
        bst.insert(11);
        bst.insert(90);
        bst.insert(82);
        bst.insert(7);
        bst.insert(9);
        List<Node> deepestNodes = bst.getDeepestNodes();

        if (deepestNodes != null) {
            System.out.println("deepest node(s):");
            deepestNodes.forEach(n -> System.out.print(n.getValue()));
        }
    }
}

class BinarySearchTree {
    private Node root;

    public void insert(int value) {
        Node n = this.insertInNode(this.root, value);
        if (this.root == null) {
            this.root = n;
        }
    }

    private Node insertInNode(Node parent, int value) {
        if (parent == null) {
            return new Node(value);
        }

        // if the value is less than the parent node,
        // expand left subtree
        if (value < parent.getValue()) {
            parent.setLeft(insertInNode(parent.getLeft(), value));
        } else {
            // otherwise, expand the right subtree
            parent.setRight(insertInNode(parent.getRight(), value));
        }

        return parent;
    }


    public List<Node> getDeepestNodes() {
        if (root == null) {
            return null;
        }

        Queue<Node> queue = new ArrayDeque<>();
        queue.add(root);

        List<Node> lastLevel = null;
        while (!queue.isEmpty()) {
            final int sz = queue.size();

            lastLevel = new ArrayList<>(queue);

            for (int i = 0; i < sz; i++) {
                Node n = queue.poll();
                if (n.getLeft() != null) {
                    queue.add(n.getLeft());
                }
                if (n.getRight() != null) {
                    queue.add(n.getRight());
                }
            }
        }

        return lastLevel;
    }
}

class Node {
    private int value;
    private Node left;
    private Node right;

    public Node(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }
}

