package lab_5_1;


import DataStructures.Ring;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    private static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) {
        showPreview();
        int ringSize = setRingSize();
        Ring myRing = new Ring(ringSize);
        int multiplicity = setMultiplicity(ringSize);
        printAnswer(myRing, multiplicity);
    }

    private static void showPreview() {
        System.out.println("This program calculates multiple positions to remove from the ring and shows remaining");
    }

    private static int setRingSize() {
        int ringSize = 0;
        boolean isReaded = false;
        do {
            try {
                printMessage("Please, enter integer ring size ( 1 < size < 100)");
                String input = bufferedReader.readLine().trim();
                ringSize = Integer.parseInt(input);
                isReaded = 1 < ringSize && ringSize < 100;
            } catch (Exception e) {
                printMessage("Not valid input ring size. Please enter digits");
            }
        } while (!isReaded);
        return ringSize;
    }

    private static int setMultiplicity(int ringSize) {
        int multiplicity = 0;
        boolean isReaded = false;
        do {
            try {
                printMessage("Please, enter integer multiplicity(1 < multiplicity <= " + ringSize + ")");
                String input = bufferedReader.readLine().trim();
                multiplicity = Integer.parseInt(input);
                isReaded = 1 < multiplicity && multiplicity <= ringSize;
            } catch (Exception e) {
                printMessage("Not valid multiplicity. Please enter digits");
            }
        } while (!isReaded);
        return multiplicity;
    }

    private static void printMessage(String errorMessage) {
        System.out.println(errorMessage);
    }

    private static void printAnswer(Ring myRing, int multiplicity) {
        System.out.println("Removed elements : " + myRing.deleteEveryMultiplePos(multiplicity).toString());
        System.out.println("Remaining elements : " + myRing.getRingElements().toString());
    }
}
