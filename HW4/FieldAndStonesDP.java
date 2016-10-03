/**
 * Mohib Kohi
 * Chris Kubec
 * Artem Davtyan
 *
 * Assignment 4 TCSS343 Summer 2016
 */


public class FieldAndStonesDP {

    public static final int PREV = 1;

    public static void main(String[] args) {

        int fieldSize = Integer.parseInt(args[0]) + 1;

        boolean[][] inField = new boolean[fieldSize][fieldSize];    // input field
        int[][] outField = new int[fieldSize][fieldSize];           // output field


        makeInputField(args, inField, fieldSize);
        // printField(inField);

        getSolutionBruteForce(inField, outField, fieldSize);
        printField(outField);
    }



    public static void makeInputField(String[] args, boolean[][] inField, int fieldSize) {

        // input field: 0-row and 0-column are initialized with stones to
        // be able to check bounds
        for (int i = 0; i < fieldSize; i++) {
            inField[i][0] = true;
            inField[0][i] = true;
        }

        // setting positions containing stones on the field
        for (int i = 2; i < args.length - 1; i+=2) {
            int n = Integer.parseInt(args[i]);
            int m = Integer.parseInt(args[i + 1]);

            inField[n][m] = true;
        }
    }


    public static int getSquareSize(int[][] field, int i, int j) {

        // current min is left cell
        int min = field[i][j - PREV];

        if (field[i - PREV][j] < min)
            min = field[i - PREV][j];

        else if (field[i - PREV][j - PREV] < min)
            min = field[i - PREV][j - PREV];

        return min + 1;
    }


    public static void getSolutionBruteForce(boolean[][] inField, int[][] outField, int fieldSize) {

        int[] solution = new int[3];
        solution[2] = 0; // set current square size to 0

        int squareSize = 0, max = 0, x = 0, y = 0;

        for (int i = 1; i < fieldSize; i++) {
            for (int j = 1; j < fieldSize; j++) {

                if (!inField[i][j]) {
                    if (inField[i][j - PREV] || inField[i - PREV][j] || inField[i - PREV][j - PREV]) {
                        outField[i][j] = 1;
                    } else {
                        squareSize = getSquareSize(outField, i, j);
                        outField[i][j] = squareSize;

                        if (squareSize > max) {
                            max = squareSize;
                            x = i;
                            y = j;
                        }

                    }
                }

            } // end for j
        } // end for i

        x = x - (max - 1);
        y = y - (max - 1);

        System.out.println("size: " + max);
        System.out.println("position: " + x + ", " + y);

    }




    public static void printField(boolean[][] array) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (array[i][j])
                    System.out.print("x, ");
                else
                    System.out.print("0, ");
            }
            System.out.println();
        }
    }


    public static void printField(int[][] array) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (array[i][j] == 0)
                    System.out.print("x ");
                else
                    System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
    }

}
