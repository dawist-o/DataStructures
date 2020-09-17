package DataStructures.Tree;

import DataStructures.MyDeque.MyDeque;
import DataStructures.MyLinkedList.MyLinkedList;
import DataStructures.Pair;

import java.util.*;

public class Tree<V>{

    private Node treeRoot;
    private int size;

    private Deque<Node> treeElements = new ArrayDeque<>();

    private final Comparator<V> comparator = Comparator.comparing(o -> (Integer) o);

    public int getSize() {
        return size;
    }

    public void insert(V value) {
        treeRoot = insert(treeRoot, value);
    }

    private Node insert(Node node, V value) {
        if (node == null) {
            Node newChild = new Node(value);
            treeElements.addLast(newChild);
            size++;
            return newChild;
        }
        if (comparator.compare(node.value, value) > 0) {
            if (node.childrenCount < 2)
                node.childrenCount++;
            node.leftChild = insert(node.leftChild, value);
        } else if (comparator.compare(node.value, value) < 0) {
            if (node.childrenCount < 2)
                node.childrenCount++;
            node.rightChild = insert(node.rightChild, value);
        }
        return node;
    }

    public int findMaxDepth() {
        return findMaxDepth(treeRoot);
    }

    private int findMaxDepth(Node node) {
        int max_depth = 0;
        if (node == null)
            return max_depth;
        int left_depth = findMaxDepth(node.leftChild);
        int right_depth = findMaxDepth(node.rightChild);
        max_depth = Math.max(left_depth, right_depth);
        if (node != treeRoot)
            max_depth++;
        return max_depth;
    }

    public boolean contains(V value) {
        return contains(treeRoot, value);
    }

    private boolean contains(Node node, V value) {
        if (node == null)
            return false;
        if (value == node.value)
            return true;
        return comparator.compare(value, node.value) < 0 ? contains(node.leftChild, value) :
                contains(node.rightChild, value);
    }

    public int findMaxDist() {
        Node lowestChild = findLowestChild();
        int maxDist = 0;
        for (Node tempChild : treeElements) {
            if (tempChild.childrenCount != 0)
                maxDist = Math.max(maxDist, findDist_ByValue(lowestChild.value, tempChild.value));
        }
        return maxDist;
    }

    private Node findLowestChild() {
        int level = 1;
        MyDeque<Pair<Node, Integer>> pairs = new MyLinkedList();
        Node lowestChild = treeRoot;
        pairs.add(new Pair<>(treeRoot, level));
        while (!pairs.isEmpty()) {
            Pair<Node, Integer> currentPair = pairs.getLast();
            lowestChild = currentPair.getNode();
            if (currentPair.getLvl() > level)
                level++;
            if (currentPair.getNode().leftChild != null) {
                pairs.add(new Pair<>(currentPair.getNode().leftChild, currentPair.getLvl() + 1));
            }
            if (currentPair.getNode().rightChild != null)
                pairs.add(new Pair<>(currentPair.getNode().rightChild, currentPair.getLvl() + 1));
        }
        return lowestChild;
    }

    private int findDist_ByValue(V firstValue, V secondValue) {
        if (!(contains(treeRoot, firstValue) && contains(treeRoot, secondValue)))
            return 0;
        Deque<Node> firstWay = getWayToElement(treeRoot, firstValue);
        if (secondValue == treeRoot.value) {
            assert firstWay != null;
            return firstWay.size() - 1;
        }
        Deque<Node> secondWay = getWayToElement(treeRoot, secondValue);
        return findDistBetweenElements(firstWay, secondWay);
    }

    private Deque<Node> getWayToElement(Node node, V value) {
        if (treeRoot == null)
            return null;
        Deque<Node> way = new ArrayDeque<>();
        way.addLast(node);
        Node current = way.peekLast();
        while (value != current.value) {
            current = way.peekLast();
            if (comparator.compare(value, current.value) < 0) {
                way.addLast(current.leftChild);
            }
            if (comparator.compare(value, current.value) > 0) {
                way.addLast(current.rightChild);
            }
        }
        return way;
    }

    private int findDistBetweenElements(Deque<Node> firstWay, Deque<Node> secondWay) {
        int distance = 0;
        if (firstWay.size() > secondWay.size()) {
            for (int i = 0; i < firstWay.size() && !secondWay.isEmpty(); i++) {
                if (firstWay.pop().value == secondWay.pop().value) {
                    distance = firstWay.size() + secondWay.size();
                }
            }
        } else {
            for (int i = 0; i < secondWay.size() && !firstWay.isEmpty(); i++) {
                if (firstWay.pop().value == secondWay.pop().value) {
                    distance = firstWay.size() + secondWay.size();
                }
            }
        }
        return distance;
    }

    public void deleteNode(V value) {
        deleteNode(treeRoot, value);
    }

    public Node findMinimumValue(Node node) {
        if (node.leftChild == null)
            return node;
        return findMinimumValue(node.leftChild);
    }

    public Node findMaximumValue(Node node) {
        if (node.rightChild == null)
            return node;
        return findMaximumValue(node.rightChild);
    }

    public Node deleteNode(Node node, V value) {
        if (node == null) return node;

        //going left
        if (comparator.compare(node.value, value) > 0)
            node.leftChild = deleteNode(node.leftChild, value);

            //going right
        else if (comparator.compare(node.value, value) < 0)
            node.rightChild = deleteNode(node.rightChild, value);

            //checking children, if both exist then find minimal node in right part
        else if (node.rightChild != null && node.leftChild != null) {
            node.value = findMinimumValue(node.rightChild).value;
            node.rightChild = deleteNode(node.rightChild, value);
        } else {
            //if exists only left part
            if (node.leftChild != null)
                node = node.leftChild;
                //if exists only right part
            else if (node.rightChild != null)
                node = node.rightChild;
                //if it is last child
            else
                node = null;
        }
        return node;
    }

    public String printTree() {
        if (treeRoot == null) {
            return "";
        }
        int level = 1;
        MyDeque<Pair<Node, Integer>> pairs = new MyLinkedList();
        String printedTree = "";
        pairs.add(new Pair<>(treeRoot, level));
        while (!pairs.isEmpty()) {
            Pair<Node, Integer> currentPair = pairs.getLast();
            if (currentPair.getLvl() > level) {
                printedTree += "\n";
                level++;
            }
            printedTree += currentPair.getNode().value + " ";
            if (currentPair.getNode().leftChild != null) {
                pairs.add(new Pair<>(currentPair.getNode().leftChild, currentPair.getLvl() + 1));
            }
            if (currentPair.getNode().rightChild != null)
                pairs.add(new Pair<>(currentPair.getNode().rightChild, currentPair.getLvl() + 1));
        }
        return printedTree;
    }

    public void deleteTree() {
        size = 0;
        treeElements.clear();
        treeRoot = null;
    }



    private class Node {
        private V value;
        private int childrenCount;
        private Node leftChild;
        private Node rightChild;

        private Node(V value) {
            this.value = value;
            this.childrenCount = 0;
        }
    }
}

