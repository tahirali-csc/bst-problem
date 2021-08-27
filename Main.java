import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        testDeepestWithSingleValue();
        testDeepestWithMultipleValues();
    }

    //Return multiple deepest nodes at height = 3
    private static void testDeepestWithMultipleValues() {
        BinarySearchTree bst = new BinarySearchTree();
        bst.insert(new int[]{12, 11, 90, 82, 7, 6, 9});
        Result res = bst.getDeepestNodes();

        if ((res != null && res.getNodes().size() == 2) &&
                (res.getNodes().get(0).getValue() == 6 && res.getNodes().get(1).getValue() == 9)) {
            System.out.println("PASS::" + res.toString());
            return;
        }
        System.out.println("FAILED: Unable to get the expected deepest nodes");
    }

    //Return single deepest node at height = 3
    private static void testDeepestWithSingleValue() {
        BinarySearchTree bst = new BinarySearchTree();
        bst.insert(new int[]{12, 11, 90, 82, 7, 9});
        Result res = bst.getDeepestNodes();

        if (res != null) {
            if (res.getHeight() == 3 && (res.getNodes() != null && res.getNodes().size() == 1)) {
                System.out.println("PASS::" + res.toString());
                return;
            }
        }
        System.out.println("Unable to find the expected height and deepest nodes");
    }

}

class BinarySearchTree {
    private Node root;

    public void insert(int[] values) {
        for (int i : values) {
            this.insert(i);
        }
    }

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


    public Result getDeepestNodes() {
        if (root == null) {
            return null;
        }

        Queue<Node> queue = new ArrayDeque<>();
        queue.add(root);

        List<Node> lastLevel = null;
        int height = -1;
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

            ++height;
        }
        return new Result(lastLevel, height);
    }
}

class Result {
    private List<Node> nodes;
    private int height;

    public Result(List<Node> nodes, int height) {
        this.nodes = nodes;
        this.height = height;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Height=%d", this.height));

        if (this.getNodes() != null) {
            String nodes = this.getNodes().
                    stream().
                    map(n -> String.valueOf(n.getValue())).
                    collect(Collectors.joining(","));
            sb.append(" Deepest Nodes=[" + nodes + "]");
        }
        return sb.toString();

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

