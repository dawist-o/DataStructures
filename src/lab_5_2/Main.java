package lab_5_2;

import DataStructures.Tree.Tree;

import java.io.*;

public class Main {
    private static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    private static final byte CONSOLE_WAY = 0;
    private static final byte FILE_WAY = 1;

    public static void main(String[] args) throws IOException {
        showPreview();
        Tree<Integer> tree = createTree();
        int distance = tree.findMaxDist();
        printAnswer(tree, distance);

    }

    private static void showPreview() {
        System.out.println("This program finds max range between two vertices with different numbers of children" +
                "in binary tree");
    }

    private static Tree<Integer> createTree() throws IOException {
        Tree<Integer> tree;
        String data;
        boolean isNotCreated;
        do {
            if (chooseInputWay() == CONSOLE_WAY)
                data = getDataFromConsole();
            else  //chooseWay() == FILE_WAY
                data = getDataFromFile();
            tree = fillTree(data);
            isNotCreated = tree.getSize() < 2;
            if (isNotCreated) {
                tree.deleteTree();
                printMessage("Invalid data. Data should contains two or more different integer values");
            }
        } while (isNotCreated);
        return tree;
    }

    private static byte chooseInputWay() {
        printMessage("Choose input way. 0 - console, 1 - file");
        return chooseWay();
    }

    private static byte chooseWay() {
        boolean isNotChosen = true;
        byte way = -1;
        do {
            try {
                String input = bufferedReader.readLine().trim();
                way = Byte.parseByte(input);
                isNotChosen = way != CONSOLE_WAY && way != FILE_WAY;
                if (isNotChosen)
                    printMessage("Please enter again. 0 - console, 1 - file");
            } catch (Exception e) {
                printMessage("Not valid input, please input 0 for console or 1 for file");
            }
        } while (isNotChosen);
        return way;
    }

    private static String getDataFromConsole() throws IOException {
        String data;
        do {
            printMessage("Please enter two or more integer values");
            data = bufferedReader.readLine().trim();
        } while (data.equals(""));
        return data;
    }

    private static String getDataFromFile() throws IOException {
        File file = findFile();
        String data = readFile(file);
        return data;
    }

    public static File findFile() throws IOException {
        printMessage("Please, enter file path");
        File file;
        boolean fileNotFound = true;
        do {
            String path = bufferedReader.readLine().trim();
            file = new File(path);
            if (file.exists())
                fileNotFound = false;
            else
                printMessage("File doesn't exist. Please, enter again.");
        } while (fileNotFound);
        return file;
    }

    public static String readFile(File inputFile) throws IOException {
        BufferedReader fileReader = new BufferedReader(new FileReader(inputFile));
        String line;
        String data = "";
        while ((line = fileReader.readLine()) != null) {
            data += line + " ";
        }
        fileReader.close();
        return data;
    }

    private static Tree<Integer> fillTree(String data) {
        Tree<Integer> tree = new Tree<>();
        try {
            for (String dataElement : data.split(" ")) {
                if (!dataElement.equals("")) {
                    tree.insert(Integer.parseInt(dataElement));
                }
            }
        } catch (NumberFormatException e) {
            printMessage("Invalid data.");
        }
        return tree;
    }

    static void printMessage(String message) {
        System.out.println(message);
    }

    private static void printAnswer(Tree<Integer> tree, int distance) throws IOException {
        printMessage("Choose output way. 0 - console, 1 - file");
        if (chooseWay() == CONSOLE_WAY)
            printAnswerInConsole(tree, distance);
        else
            writeAnswerInFile(tree, distance);
    }

    private static void printAnswerInConsole(Tree<Integer> tree, int distance) {
        System.out.println("Tree:\n" + tree.printTree());
        System.out.println("Distance : " + distance);
    }

    private static void writeAnswerInFile(Tree<Integer> tree, int distance) throws IOException {
        File outputFile = findFile();
        FileWriter fileWriter = new FileWriter(outputFile);
        fileWriter.write("Tree:\n");
        fileWriter.write(tree.printTree());
        fileWriter.write("\nDistance : " + distance);
        fileWriter.flush();
        fileWriter.close();
    }
}
