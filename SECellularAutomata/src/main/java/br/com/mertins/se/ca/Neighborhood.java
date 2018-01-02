package br.com.mertins.se.ca;

/**
 *
 * @author mertins
 */
public class Neighborhood {

    public enum Type {
        VONNEUMANN, MOORE
    }

    public static Integer[] vonNeumann(int row, int col, int[][] image) {
        Integer[] values = new Integer[5];
        values[0] = col > 0 ? image[row][col - 1] : null;
        values[1] = col < image[0].length - 1 ? image[row][col + 1] : null;
        values[2] = image[row][col];
        values[3] = row > 0 ? image[row - 1][col] : null;
        values[4] = row < image.length - 1 ? image[row + 1][col] : null;
        return values;
    }

    public static Integer[] moore(int row, int col, int[][] image) {
        Integer[] values = new Integer[9];
        int pos = 0;
        int rowi = row - 1;
        int coli = col - 1;
        int rowf = row + 1;
        int colf = col + 1;

        for (int r = rowi; r < rowf; r++) {
            for (int c = coli; c < colf; c++) {
                values[pos++] = (r > -1 && c > -1 && r < image.length - 1 && c < image[0].length - 1) ? image[r][c] : null;
            }
        }
        return values;
    }

    public static Integer[] process(Type type, int row, int col, int[][] image) {
        switch (type) {
            case MOORE:
                return moore(row, col, image);
            case VONNEUMANN:
                return vonNeumann(row, col, image);
            default:
                return null;
        }
    }

}
