/**
 * Mohib Kohi
 * Chris Kubec
 * Artem Davtyan
 *
 * Assignment 4 TCSS343 Summer 2016
 */



public class FieldAndStonesBF {

    public static void main(String[] args) {

        int fieldSize = Integer.parseInt(args[0]) + 1;

        boolean[][] inField = new boolean[fieldSize][fieldSize];    // input field


        makeInputField(args, inField, fieldSize);

        System.out.println(CheckSolution(inField, 6, 6, 3, fieldSize));

    }

    private static boolean CheckSolution(boolean field[][], int x, int y, int s, int fsize) {

        boolean solution = true;

        for (int i = x; i < (x + s); i++) {
            for (int j = y; j < (y + s); j++) {
                if (j < fsize && i < fsize) {
                    if (field[i][j]) solution = false;
                } else {
                    solution = false;
                }
            }
        }

        return solution;
    }

    private static void makeInputField(String[] args, boolean[][] inField, int fieldSize) {

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
}
