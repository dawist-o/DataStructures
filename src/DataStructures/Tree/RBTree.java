package DataStructures.Tree;

import java.util.Deque;
import java.util.LinkedList;

public class RBTree<K extends Comparable<K>, V> {

    private static final boolean RED = false;
    private static final boolean BLACK = true;

    private Node root;
    private int size;

    public int size() {
        return this.size;
    }

    public void put(K key, V value) {
        this.root = put(this.root, key, value);
        size++;
        this.root.color=BLACK;
    }

    public void clear() {
        this.size = 0;
        this.root = null;
    }

    private Node put(Node node, K key, V value) {
        if (node == null)
            return new Node(key, value, RED);

        if (key.compareTo(node.key) < 0)
            node.left = put(node.left, key, value);
        else if (key.compareTo(node.key) > 0)
            node.right = put(node.right, key, value);
        else
            node.value = value;

        if (!isRed(node.left) && isRed(node.right)) // left is black && right is red
            node = rotateLeft(node);

       // if (isRed(node.left.left) && isRed(node.left))
        if (!isRed(node.right) && isRed(node.left)) //incorrect colors
            node = rotateRight(node);

        if (isRed(node.right) && isRed(node.left))
            recolor(node);

        return node;
    }

    private Node rotateRight(Node head) {
        //rotating
        Node rotR = head.left;
        head.left = rotR.right;
        rotR.right = head;
        //colors changing
        rotR.color = head.color;
        head.color = RED;
        return rotR;
    }

    private Node rotateLeft(Node head) {
        //rotating
        Node rotL = head.right;
        head.right = rotL.left;
        rotL.left = head;
        //colors changing
        rotL.color = head.color;
        head.color = RED;
        return rotL;
    }

    private void recolor(Node node) {
        node.color = RED;
        node.right.color = BLACK;
        node.left.color = BLACK;
    }

    private boolean isRed(Node node) {
        return node != null && node.color == RED;
    }


    @Override
    public String toString() {
        if (root == null)
            return "{}";
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Deque<Node> deque = new LinkedList<>();
        deque.add(root);
        Node current = null;

        while (!deque.isEmpty()) {
            current = deque.removeFirst();
            sb.append("{").append(current.key)
                    .append(", ").append(current.value).append("},");
            if (current.left != null)
                deque.add(current.left);
            if (current.right != null)
                deque.add(current.right);
        }
        sb.replace(sb.length() - 2, sb.length() - 1, "]");
        return sb.toString();
    }

    private class Node {
        private Node right;
        private Node left;
        private boolean color;
        private K key;
        private V value;

        public Node(K key, V value, boolean color) {
            this.color = color;
            this.key = key;
            this.value = value;
        }
    }
}
