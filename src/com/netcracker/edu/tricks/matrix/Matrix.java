package com.netcracker.edu.tricks.matrix;

import com.netcracker.edu.tricks.matrix.exceptions.MatrixException;

public class Matrix {
    private final double[][] matrixArray;

    public Matrix(int n, int m) throws MatrixException {
        if ((n < 1) || (m < 1)) {
            throw new MatrixException("Dimension must be positive. Provided: " + n + "x" + m + "\n");
        }
        matrixArray = new double[n][m];
    }

    public int getNumberOfRows() {
        return matrixArray.length;
    }

    public int getNumberOfColumns() {
        return matrixArray[0].length;
    }

    public double getElement(int i, int j) throws MatrixException {
        if (checkRange(i, j)) {
            return matrixArray[i][j];
        }
        throw new MatrixException("Index out of bounds while getting element: " + i + "x" + j + ". Required: " + matrixArray.length + "x" + matrixArray[0].length + "\n");
    }

    public void setElement(int i, int j, double value) throws MatrixException {
        if (checkRange(i, j)) {
            matrixArray[i][j] = value;
        } else {
            throw new MatrixException("Index out of bounds while setting element: " + i + "x" + j + ". Required: " + matrixArray.length + "x" + matrixArray[0].length + "\n");
        }
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("\nMatrix : " + matrixArray.length + "x" + matrixArray[0].length + "\n");
        for (double[] row : matrixArray) {
            for (double value : row) {
                s.append(value).append(" ");
            }
            s.append("\n");
        }
        return s.toString();
    }

    private boolean checkRange(int i, int j) {
        return i >= 0 && i < matrixArray.length && j >= 0 && j < matrixArray[0].length;
    }

    public Matrix getMinor(int row, int column) throws MatrixException {
        Matrix result = new Matrix(this.getNumberOfRows() - 1, this.getNumberOfColumns() - 1);

        int minorRow = 0;
        int minorColumn = 0;
        for (int i = 0; i < this.getNumberOfRows(); i++) {
            if (i == row) {
                continue;
            }
            for (int j = 0; j < this.getNumberOfColumns(); j++) {
                if (j == column) {
                    continue;
                }
                result.setElement(minorRow, minorColumn++, this.getElement(i, j));
            }
            minorRow++;
            minorColumn = 0;
        }
        return result;
    }
}