package DataStructures;

import java.util.ArrayList;
import java.util.Collections;

public class Ring {
    private int ringSize;
    private Node firstElement;
    private Node lastElement;


    public Ring(int ringSize) {
        this.ringSize = ringSize;
        createRing();
    }

    public int getRingSize() {
        return ringSize;
    }

    private void createRing() {
        for (int position = 1; position <= ringSize; position++) {
            addPos(position);
        }
        lastElement.nextNode = firstElement;
        firstElement.prevNode = lastElement;
    }

    private void addPos(int pos) {
        Node newElement = new Node(pos);
        if (firstElement == null)
            firstElement = newElement;
        else {
            lastElement.nextNode = newElement;
            newElement.prevNode = lastElement;
        }
        lastElement = newElement;
    }

    public ArrayList<Integer> getRingElements() {
        ArrayList<Integer> ringElements = new ArrayList<>();
        if (ringSize == 1) {
            ringElements.add(firstElement.position);
        } else {
            Node currentElement = firstElement;
            do {
                ringElements.add(currentElement.position);
                currentElement = currentElement.nextNode;
            } while (currentElement.nextNode != lastElement.nextNode);
            ringElements.add(currentElement.position);
        }
        return ringElements;
    }

    public ArrayList<Integer> deleteEveryMultiplePos(int multiplePos) {
        ArrayList<Integer> allDeletedPositions = new ArrayList<>();
        ArrayList<Integer> loopDeletedPositions = new ArrayList<>();
        while (multiplePos <= ringSize) {
            int currentRingSize = ringSize;
            for (int i = currentRingSize; i > 1; i--) {
                if (i % multiplePos == 0)
                    loopDeletedPositions.add(deletePos(i));
            }
            Collections.reverse(loopDeletedPositions);
            allDeletedPositions.addAll(loopDeletedPositions);
            loopDeletedPositions.clear();
        }
        return allDeletedPositions;
    }

    private int deletePos(int pos) {
        if (pos % ringSize != 0)
            pos = pos % ringSize;
        Node current = firstElement;
        int temp = 1;
        while (temp++ < pos) {
            current = current.nextNode;
        }
        current.prevNode.nextNode = current.nextNode;
        current.nextNode.prevNode = current.prevNode;
        ringSize--;
        return current.position;
    }

    private static class Node {
        private int position;
        private Node nextNode;
        private Node prevNode;
        private Node(int position) {
            this.position = position;
        }
    }
}
