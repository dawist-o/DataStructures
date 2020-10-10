package test;

import DataStructures.Tree.RBTree;

// RED = false;
//  BLACK = true;

public class Test {
    public static void main(String[] args) {
        RBTree<Integer, Integer> tree = new RBTree();
        tree.put(10, 2);
        tree.put(20, 7);
        tree.put(30, 7);
        tree.put(40, 7);
        tree.put(60, 7);
        tree.put(5, 7);
        tree.put(3, 7);
        System.out.println(tree);
    }

}
